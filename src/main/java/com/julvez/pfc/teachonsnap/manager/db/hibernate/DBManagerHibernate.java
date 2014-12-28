package com.julvez.pfc.teachonsnap.manager.db.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

public class DBManagerHibernate implements DBManager{

	private SessionFactory sessionFactory;
	
	private static final String LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    
    public DBManagerHibernate(){
    	sessionFactory = buildSessionFactory();
    }
		
	private SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");
             
            //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");
             
            SessionFactory sessionFactory = configuration
                                .buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
	private SQLQuery getQuery(Session sess, String queryName, Class<?> entityClass,
			Object... queryParams){
		
		SQLQuery query;
		
		if(entityClass != null)
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString())
				.addEntity(entityClass);
		else
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString());
	
		int i=0;
		
		String queryLog = query.getQueryString();
		
		for (Object queryParam : queryParams) {
			query.setParameter(i++, queryParam);
			queryLog = queryLog.replaceFirst("\\?", queryParam.toString());
		}
		
		System.out.println("QueryLog: "+ queryLog);
				
		return query;
	}
	
	
	@Override
	public List<?> getQueryResultList(String queryName, Class<?> entityClass,
			Object... queryParams) {
		Session sess = sessionFactory.getCurrentSession();
		
		List<?> resultados = new ArrayList<>();
		
		try{
			sess.beginTransaction();
		
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
			
			resultados = query.list();
			
			sess.getTransaction().commit();
			
		} catch (HibernateException e) {
			System.out.println(e);
			resultados = null;
		}		
		System.out.println("QueryLog: -> "+ resultados + " -> " + (resultados.size()>0?resultados.get(0).getClass().getCanonicalName():"empty"));
		return resultados;	
	}

	@Override
	public Object getQueryResultUnique(String queryName,
			Class<?> entityClass, Object... queryParams) {
		Session sess = sessionFactory.getCurrentSession();
		
		Object resultado;
		
		try{
			sess.beginTransaction();

			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
					
			resultado = query.uniqueResult();
			
			sess.getTransaction().commit();
					
		} catch (HibernateException e) {
			System.out.println(e);
			resultado = null;
		}		
		System.out.println("QueryLog: -> "+ resultado);
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
			System.out.println("DBManager: Cerrando conexiones");
		}
		catch(Throwable t){
			System.out.println(t);
		}		
	}

	@Override
	public long updateQuery(String queryName, Object... queryParams) {
		Session sess = sessionFactory.getCurrentSession();
		
		long lastInsertID=-1;
		
		try{
			sess.beginTransaction();

			SQLQuery query = getQuery(sess, queryName, null, queryParams);
					
			query.executeUpdate();
			
			query = sess.createSQLQuery(LAST_INSERT_ID);
			
			lastInsertID = ((BigInteger)query.uniqueResult()).longValue();
			
			sess.getTransaction().commit();
					
		} catch (HibernateException e) {
			System.out.println(e);			
		}		
		System.out.println("QueryLog: -> "+ lastInsertID);
		return lastInsertID;
	}
}
