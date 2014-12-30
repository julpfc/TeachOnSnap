package com.julvez.pfc.teachonsnap.manager.db;

import java.util.List;

public interface DBManager {
		
	public List<?> getQueryResultList(String queryName,Class<?> entityClass,Object... queryParams);
	
	public Object getQueryResultUnique(String queryName,Class<?> entityClass,Object... queryParams);
	
	public long updateQuery(String queryName,Object... queryParams);
	
	public Object beginTransaction();
	
	public void endTransaction(boolean commit,Object session);
	
	public List<?> getQueryResultList_NoCommit(Object session, String queryName,Class<?> entityClass,Object... queryParams);
	
	public Object getQueryResultUnique_NoCommit(Object session, String queryName,Class<?> entityClass,Object... queryParams);
	
	public long updateQuery_NoCommit(Object session, String queryName,Object... queryParams);

	public void close();
			
}
