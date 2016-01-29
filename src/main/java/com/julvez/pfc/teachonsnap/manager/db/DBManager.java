package com.julvez.pfc.teachonsnap.manager.db;

import java.util.List;

/** Database manager providing access/modification capabilities */
public interface DBManager {
		
	/**
	 * Executes the query in the database with the query params and 
	 * returns the result as a list of entities. 
	 * @param queryName Query name
	 * @param entityClass Entity describing the result of the query
	 * @param queryParams Query params
	 * @return List of entities as the result of query's execution, null if error
	 */
	public <T> List<T> getQueryResultList(String queryName, Class<T> entityClass, Object... queryParams);
	
	/**
	 * Executes the query in the database with the query params and 
	 * returns the unique result as an entity object. 
	 * @param queryName Query name
	 * @param entityClass Entity describing the result of the query
	 * @param queryParams Query params
	 * @return Unique result as an entity object of query's execution, null if error
	 */
	public <T> T getQueryResultUnique(String queryName, Class<T> entityClass, Object... queryParams);
	
	/**
	 * Executes an update/delete query in the database with the query params and 
	 * returns the number of affected rows. 
	 * @param queryName Query name
	 * @param queryParams Query params
	 * @return affected rows or -1 if error
	 */
	public int updateQuery(String queryName, Object... queryParams);
	
	/**
	 * Executes an insert query in the database with the query params and 
	 * returns the last insert ID. 
	 * @param queryName Query name
	 * @param queryParams Query params
	 * @return last insert ID or -1 if error
	 */
	public long insertQueryAndGetLastInserID(String queryName, Object... queryParams);
	
	/**
	 * Begins a database transaction in the database.
	 * @return the object session referencing the open transaction.
	 */
	public Object beginTransaction();
	
	/**
	 * Ends an open transaction on the database.
	 * @param commit indicates if the transaction has to be ended with a 
	 * commit (true) or rollback (false).
	 * @param session Open transaction in the database
	 */
	public void endTransaction(boolean commit, Object session);
	
	/**
	 * Executes the query within an open transaction in the database with 
	 * the query params and returns the result as a list of entities. 
	 * @param session Open transaction in the database
	 * @param queryName Query name
	 * @param entityClass Entity describing the result of the query
	 * @param queryParams Query params
	 * @return List of entities as the result of query's execution, null if error
	 */
	public <T> List<T> getQueryResultList_NoCommit(Object session, String queryName, Class<T> entityClass, Object... queryParams);
	
	/**
	 * Executes the query within an open transaction in the database with
	 * the query params and returns the unique result as an entity object. 
	 * @param session Open transaction in the database
	 * @param queryName Query name
	 * @param entityClass Entity describing the result of the query
	 * @param queryParams Query params
	 * @return Unique result as an entity object of query's execution, null if error
	 */
	public <T> T getQueryResultUnique_NoCommit(Object session, String queryName, Class<T> entityClass, Object... queryParams);
	
	/**
	 * Executes an update/delete query within an open transaction in the 
	 * database with the query params and returns the number of affected rows. 
	 * @param session Open transaction in the database
	 * @param queryName Query name
	 * @param queryParams Query params
	 * @return affected rows or -1 if error
	 */
	public int updateQuery_NoCommit(Object session, String queryName, Object... queryParams);
	
	/**
	 * Executes an insert query within an open transaction in the database 
	 * with the query params and returns the last insert ID. 
	 * @param session Open transaction in the database
	 * @param queryName Query name
	 * @param queryParams Query params
	 * @return last insert ID or -1 if error
	 */
	public long insertQueryAndGetLastInserID_NoCommit(Object session, String queryName, Object... queryParams);
			
	/**
	 * Cleanups DBManager conenctions, JDBC drivers, etc, and frees resources.
	 */
	public void close();
	
}
