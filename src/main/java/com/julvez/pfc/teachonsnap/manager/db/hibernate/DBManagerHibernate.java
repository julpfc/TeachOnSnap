package com.julvez.pfc.teachonsnap.manager.db.hibernate;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.util.Collections;
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
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * Implementation of the DBManager, uses internal {@link LogManager} 
 * to log the errors and {@link PropertyManager} to access application's properties.
 */
public class DBManagerHibernate implements DBManager{

	/** Last insert ID query */
	private static final String LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

	/** Hibernate session factory */
	private SessionFactory sessionFactory;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
    
	/**
	 * Constructor requires all parameters not to be null
	 * @param logger Log manager providing logging capabilities
	 * @param properties Property manager providing access to properties files
	 */
    public DBManagerHibernate(LogManager logger, PropertyManager properties){
    	if(logger == null || properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
    	this.logger = logger;
    	this.properties = properties;
    	sessionFactory = buildSessionFactory();
    }
		
	/**
	 * Initializes the Hibernate SessionFactory from configuration
	 * @return Hibernate's session factory
	 */
	private SessionFactory buildSessionFactory() {
		SessionFactory sessionFactory = null;
        
		try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            logger.info("Hibernate Configuration loaded");
            
            //set password from password properties file instead of default properties
            configuration.getProperties().setProperty(DBPropertyName.HIBERNATE_DB_PASSWORD.toString(), 
            				properties.getPasswordProperty(DBPropertyName.HIBERNATE_DB_PASSWORD));            
             
            //apply configuration property settings to StandardServiceRegistryBuilder
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            logger.info("Hibernate serviceRegistry created");
            
            //build SessionFactory
            sessionFactory = configuration
                                .buildSessionFactory(serviceRegistry);
        }
        catch (Throwable t) {
            // Make sure you log the exception, as it might be swallowed        	
            logger.error(t, "Initial SessionFactory creation failed.");
            throw new ExceptionInInitializerError(t);
        }
        return sessionFactory;
    }
    
	
	
	@Override
	public <T> List<T> getQueryResultList(String queryName, Class<T> entityClass,
			Object... queryParams) {
		List<T> list = null;
		
		try{
			//Begin database transaction
			Session sess = beginTransaction();
			
			//get result
			list = getQueryResultList_NoCommit(sess,queryName,entityClass,queryParams);
			
			//Commit
			endTransaction(true, sess);
		}
		catch (Throwable t) {
			logger.error(t,"Error executing query: " + queryName);
			list = null;
		}			
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getQueryResultList_NoCommit(Object session, String queryName, Class<T> entityClass,
			Object... queryParams) {
		
		List<T> results = Collections.emptyList();
		Session sess = (Session)session;
		
		try{
			//create query
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);			
			logger.startTimer();
			//get result
			results = query.list();
			//log execution time & result
			logger.infoTime(results + " -> " + ((results!= null && results.size()>0)?results.get(0).getClass().getCanonicalName():"empty"));
		} catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error executing query: " + queryName);
			results = null;
		}		
		return results;	
	}

	@Override
	public <T> T getQueryResultUnique(String queryName,
			Class<T> entityClass, Object... queryParams) {
		T result = null;
		
		try{
			//Begin database transaction
			Session sess = beginTransaction();
			
			//get result
			result = getQueryResultUnique_NoCommit(sess,queryName,entityClass,queryParams);
			
			//Commit
			endTransaction(true, sess);
		}
		catch (Throwable t) {
			logger.error(t, "Error executing query: " + queryName);
			result = null;
		}			
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getQueryResultUnique_NoCommit(Object session, String queryName,
			Class<T> entityClass, Object... queryParams) {
		T result;
		Session sess = (Session)session;
		
		try{
			//create query
			SQLQuery query = getQuery(sess, queryName, entityClass, queryParams);
			logger.startTimer();
			//get result
			result = (T)query.uniqueResult();
			//log execution time & result
			logger.infoTime(result!=null?result.toString():null);		
		} 
		catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error executing query: " + queryName);
			result = null;
		}		
		return result;	
	}

	@Override
	public void close() {
		try{
			//cleanup
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
			logger.info("Connections closed");
		}
		catch(Throwable t){
			logger.error(t, "Error closing connections.");
		}		
	}

	@Override
	public int updateQuery(String queryName, Object... queryParams) {
		return (int)updateQuery(queryName, false, queryParams);
	}
	
	@Override
	public int updateQuery_NoCommit(Object session, String queryName, Object... queryParams) {		
		return (int)updateQuery_NoCommit(session, queryName, false, queryParams);
	}
		
	@Override
	public long insertQueryAndGetLastInserID(String queryName, Object... queryParams) {
		return updateQuery(queryName, true, queryParams);
	}

	@Override
	public long insertQueryAndGetLastInserID_NoCommit(Object session,
			String queryName, Object... queryParams) {
		return updateQuery_NoCommit(session, queryName, true, queryParams);		
	}

	@Override
	public Session beginTransaction() {
		//Begin a database transaction
		Session sess = sessionFactory.getCurrentSession();
		sess.beginTransaction();
		return sess;
	}

	@Override
	public void endTransaction(boolean commit, Object session) {
		if(session!=null){
			Session sess = (Session) session;
			//Commit on success
			if(commit){
				sess.getTransaction().commit();
			}
			else{
				//Rollback on error
				sess.getTransaction().rollback();
			}
		}		
	}
		
	
	/**
	 * Creates a SQL query with the specified params and result's entity
	 * @param sess Open Hibernate session
	 * @param queryName Query's name
	 * @param entityClass Entity describing the result of the query
	 * @param queryParams Query params
	 * @return created SQL query, null otherwise
	 */
	private SQLQuery getQuery(Session sess, String queryName, Class<?> entityClass,
			Object... queryParams){
		
		SQLQuery query;
		
		//Create query
		if(entityClass!= null && isEntity(entityClass)){
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString())
				.addEntity(entityClass);
		}
		else{
			query = sess.createSQLQuery(sess.getNamedQuery(queryName).getQueryString());
		}
	
		if(query != null){
			//Set query params
			int i=0;		
			for (Object queryParam : queryParams) {
				query.setParameter(i++, queryParam);
			}
	
			//Log query with parameters
			String queryLog = getQueryStringWithParams(query.getQueryString(), queryParams);			
			logger.info(queryName +": " + queryLog);
		}
		else{
			logger.error("Error creating query for: " + queryName);
		}
				
		return query;
	}

	/**
	 * Replaces the query params on the query string
	 * @param queryString Query string
	 * @param queryParams Query params
	 * @return Query string with query params.
	 */
	private String getQueryStringWithParams(String queryString, Object... queryParams) {
		//Escape % character
		if(queryString.contains("%")){
			queryString = queryString.replaceAll("%", "%%");
		}
		//Escape ? character
		if(queryString.contains("?")){
			queryString = queryString.replaceAll("\\?", "%s");
		}

		//Replace params
		queryString = String.format(queryString, queryParams);
		
		return queryString;
	}

	
	/**
	 * Checks if the specified class is a JPA entity
	 * @param clazz Class to check
	 * @return true if the specified class is a JPA entity
	 */
	private boolean isEntity(Class<?> clazz){
		boolean isEntity= false;
		
		//get class annotations
		Annotation[] annotations = clazz.getAnnotations();
		
		//check if it's annotated with @Entity
		for(Annotation annotation:annotations){
			if(javax.persistence.Entity.class.getCanonicalName().equals(annotation.annotationType().getCanonicalName())){
				isEntity = true;
				break;
			}			
		}		
		return isEntity;
	}
	
	/**
	 * Returns the last insert id in the open database transaction
	 * @param session Open database transaction
	 * @return last insert id, -1 otherwise
	 */
	private long getLastInsertID(Object session) {
		long lastInsertID=-1;
		Session sess = (Session)session;
		
		try{
			//create query
			SQLQuery query = sess.createSQLQuery(LAST_INSERT_ID);
			logger.startTimer();
			//get last insert id
			lastInsertID = ((BigInteger)query.uniqueResult()).longValue();
			logger.infoTime("ID: " + lastInsertID);
		} catch (Throwable t) {
			logger.clearTimer();
			logger.error(t, "Error retreiving LAST_INSERT_ID() ");		
			lastInsertID = -1;
		}		
		return lastInsertID;
	}
	
	
	/**
	 * Executes an update query within an open transaction in the database. 
	 * Returns last insert id or affected rows upon request.
	 * @param session Open database transaction
	 * @param queryName Query name
	 * @param getLastInsertId Indicates if has to return the last insert ID (true) 
	 * or the number of affected rows (false).
	 * @param queryParams Query params
	 * @return last insert id or affected rows, -1 if error
	 */
	private long updateQuery_NoCommit(Object session, String queryName, boolean getLastInsertId, Object... queryParams) {
		
		long affectedRows = -1;
		long lastInsertID = -1;
		long retValue = -1;
		Session sess = (Session)session;
		
		try{
			//Create query
			SQLQuery query = getQuery(sess, queryName, null, queryParams);
			
			logger.startTimer();
			
			//Execute update
			affectedRows = query.executeUpdate();
			
			//get last insert id if requested
			if(getLastInsertId){				
				if(affectedRows>0){
					lastInsertID = getLastInsertID(sess);
				}
				else if(affectedRows == 0){
					lastInsertID = 0;
				}
			}			
		} catch (Throwable t) {
			logger.error(t, "Error executing query: " + queryName);		
			affectedRows = -1;
			lastInsertID = -1;
		}		

		//return the proper value
		if(getLastInsertId){			
			retValue = lastInsertID;
		}
		else{
			retValue = affectedRows;
		}
		
		//log execution time
		logger.infoTime("Rows-> "+  affectedRows);
		return retValue;
	}
	
	/**
	 * Executes an update query. Returns last insert id or affected rows upon request.
	 * @param queryName Query name
	 * @param getLastInsertId Indicates if has to return the last insert ID (true) 
	 * or the number of affected rows (false).
	 * @param queryParams Query params
	 * @return last insert id or affected rows, -1 if error
	 */
	private long updateQuery(String queryName, boolean getLastInsertId, Object... queryParams) {
		long retValue = -1;
		
		try{
			//Begin database transaction
			Session sess = beginTransaction();
			
			//Execute update
			retValue = updateQuery_NoCommit(sess, queryName, queryParams);
			
			//Commit if success, rollback otherwise
			endTransaction(retValue>-1, sess);
		}
		catch (Throwable t) {
			logger.error(t, "Error handling session for: " + queryName);
			retValue = -1;
		}			
		return retValue;
	}
}
