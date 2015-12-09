package com.julvez.pfc.teachonsnap.manager.db;

import java.util.List;

public interface DBManager {
		
	public <T> List<T> getQueryResultList(String queryName,Class<T> entityClass,Object... queryParams);
	public <T> T getQueryResultUnique(String queryName,Class<T> entityClass,Object... queryParams);
	public int updateQuery(String queryName,Object... queryParams);
	public long insertQueryAndGetLastInserID(String queryName,Object... queryParams);
	
	public Object beginTransaction();
	public void endTransaction(boolean commit,Object session);
	
	public <T> List<T> getQueryResultList_NoCommit(Object session, String queryName,Class<T> entityClass,Object... queryParams);
	public <T> T getQueryResultUnique_NoCommit(Object session, String queryName,Class<T> entityClass,Object... queryParams);
	public int updateQuery_NoCommit(Object session, String queryName,Object... queryParams);
	public long insertQueryAndGetLastInserID_NoCommit(Object session, String queryName,Object... queryParams);
			
	public void close();
	
}
