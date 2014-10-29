package com.julvez.pfc.teachonsnap.manager.db;

import java.util.List;

public interface DBManager {
		
	public List<?> getQueryResultList(String queryName,Class<?> entityClass,Object... queryParams);
	
	public Object getQueryResultUnique(String queryName,Class<?> entityClass,Object... queryParams);
			
}
