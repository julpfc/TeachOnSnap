package com.julvez.pfc.teachonsnap.manager.db.hibernate;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class DBManagerHibernate implements DBManager{

	private SessionFactory sessionFactory;
	
	private static final String LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
	
	private LogManager logger = LogManagerFactory.getManager();
    
    public DBManagerHibernate(){
    	sessionFactory = buildSessionFactory();
    }
		
	private SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            logger.info("Hibernate Configuration loaded");
             
            //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            logger.info("Hibernate serviceRegistry created");
             
            SessionFactory sessionFactory = configuration
                                .buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable t) {
            // Make sure you log the exception, as it might be swallowed        	
            logger.error(t, "Initial SessionFactory creation failed.");
            throw new ExceptionInInitializerError(t);
        }
    }
    
	private SQLQuery getQuery(Session sess, String queryName, Class<?> entityClass,
			Object... queryParams){
		
		SQLQuery query;
		
		if(entityClass!= null && isEntity(entityClass))
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString())
				.addEntity(entityClass);
		else
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString());
	
		int i=0;
		
		String queryLog = query.getQueryString();
		
		for (Object queryParam : queryParams) {
			query.setParameter(i++, queryParam);
		}
		if(queryLog.contains("%")){
			queryLog = queryLog.replaceAll("%", "%%");
		}
		if(queryLog.contains("?")){
			queryLog = queryLog.replaceAll("\\?", "%s");
		}

		queryLog = String.format(queryLog, queryParams);			
		
		logger.info(queryName +": " + queryLog);
		
				
		return query;
	}
	
	
	@Override
	public <T> List<T> getQueryResultList(String queryName, Class<T> entityClass,
			Object... queryParams) {
		List<T> list = null;
		
		try{
			Session sess = beginTransaction();
			
			list = getQueryResultList_NoCommit(sess,queryName,entityClass,queryParams);
			
			endTransaction(true, sess);
		}
		catch (Throwable t) {
			logger.error(t,"Error en consulta: " + queryName);
			list = null;
		}			
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getQueryResultList_NoCommit(Object session, String queryName, Class<T> entityClass,
			Object... queryParams) {
		
		List<T> resultados = new ArrayList<>();
		Session sess = (Session)session;
		
		try{
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);			
			logger.startTimer();
			resultados = query.list();
			logger.infoTime(resultados + " -> " + ((resultados!= null && resultados.size()>0)?resultados.get(0).getClass().getCanonicalName():"empty"));
		} catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error en consulta: " + queryName);
			resultados = null;
		}		
		return resultados;	
	}

	@Override
	public <T> T getQueryResultUnique(String queryName,
			Class<T> entityClass, Object... queryParams) {
		T result = null;
		
		try{
			Session sess = beginTransaction();
			
			result = getQueryResultUnique_NoCommit(sess,queryName,entityClass,queryParams);
			
			endTransaction(true, sess);
		}
		catch (Throwable t) {
			logger.error(t, "Error en consulta: " + queryName);
			result = null;
		}			
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getQueryResultUnique_NoCommit(Object session, String queryName,
			Class<T> entityClass, Object... queryParams) {
		T resultado;
		Session sess = (Session)session;
		
		try{
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
			logger.startTimer();
			resultado = (T)query.uniqueResult();
			logger.infoTime(resultado!=null?resultado.toString():null);
		
		} catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error en consulta: " + queryName);
			resultado = null;
		}		
		return resultado;	
	}

	@Override
	public void close() {
		try{
			if(sessionFactory!=null){
				sessionFactory.close();
				sessionFactory = null;
				AbandonedConnectionCleanupThread.shutdown();
				Enumeration<java.sql.Driver> drivers = java.sql.DriverManager.getDrivers();
				   while (drivers.hasMoreElements()) {
				      java.sql.Driver driver = drivers.nextElement();
				      java.sql.DriverManager.deregisterDriver(driver);				     
				   }
			} 
			logger.info("Conexiones cerradas");
		}
		catch(Throwable t){
			logger.error(t, "Error cerrando conexiones.");
		}		
	}

	@Override
	public int updateQuery(String queryName, Object... queryParams) {
		int affectedRows = -1;
		
		try{
			Session sess = beginTransaction();
			
			affectedRows = updateQuery_NoCommit(sess,queryName,queryParams);
			
			endTransaction(affectedRows>-1, sess);
		}
		catch (Throwable t) {
			logger.error(t, "Error en consulta: " + queryName);
			affectedRows = -1;
		}			
		return affectedRows;
	}
	
	@Override
	public int updateQuery_NoCommit(Object session, String queryName, Object... queryParams) {
		
		int affectedRows = -1;
		Session sess = (Session)session;
		
		try{
			SQLQuery query = getQuery(sess, queryName, null, queryParams);
			logger.startTimer();
			affectedRows = query.executeUpdate();
			
		} catch (Throwable t) {
			logger.error(t, "Error en consulta: " + queryName);		
			affectedRows = -1;
		}		
		logger.infoTime("Rows-> "+ affectedRows);
		return affectedRows;
	}

	@Override
	public Session beginTransaction() {
		Session sess = sessionFactory.getCurrentSession();
		sess.beginTransaction();
		return sess;
	}

	@Override
	public void endTransaction(boolean commit, Object session) {
		if(session!=null){
			Session sess = (Session) session;
			if(commit){
				sess.getTransaction().commit();
			}
			else{
				sess.getTransaction().rollback();
			}
		}		
	}
	
	private boolean isEntity(Class<?> clazz){
		boolean isEntity= false;
		Annotation[] annotations = clazz.getAnnotations();
		
		for(Annotation annotation:annotations){
			if(javax.persistence.Entity.class.getCanonicalName().equals(annotation.annotationType().getCanonicalName())){
				isEntity = true;
				break;
			}			
		}
		
		return isEntity;
	}


	@Override
	public long insertQueryAndGetLastInserID(String queryName, Object... queryParams) {
		
		long lastInsertID = -1;
				
		try{
			Session sess = beginTransaction();
			
			lastInsertID = insertQueryAndGetLastInserID_NoCommit(sess, queryName, queryParams);			
			
			endTransaction(lastInsertID>-1, sess);
		}
		catch (Throwable t) {
			logger.error(t, "Error en consulta: " + queryName);
			lastInsertID = -1;
		}			
		return lastInsertID;
	}

	
	private long getLastInsertID(Object session) {
		long lastInsertID=-1;
		
		Session sess = (Session)session;
		
		try{
			SQLQuery query = sess.createSQLQuery(LAST_INSERT_ID);
			logger.startTimer();
			lastInsertID = ((BigInteger)query.uniqueResult()).longValue();
			logger.infoTime("ID: " + lastInsertID);
		} catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error recuperando LAST_INSERT_ID() ");		
			lastInsertID = -1;
		}		
		return lastInsertID;
	}

	@Override
	public long insertQueryAndGetLastInserID_NoCommit(Object session,
			String queryName, Object... queryParams) {
		long lastInsertID = -1;
		
		Session sess = (Session)session;
		
		try{
			int affectedRows = updateQuery_NoCommit(sess,queryName,queryParams);
			
			if(affectedRows>0){
				lastInsertID = getLastInsertID(sess);
			}
			else if(affectedRows==0){
				lastInsertID = 0;
			}
		}
		catch (Throwable t) {
			logger.error(t, "Error en consulta: " + queryName);
			lastInsertID = -1;			
		}			
		return lastInsertID;
	}
	
	
}
