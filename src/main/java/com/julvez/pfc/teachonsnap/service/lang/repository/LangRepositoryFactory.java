package com.julvez.pfc.teachonsnap.service.lang.repository;

import com.julvez.pfc.teachonsnap.service.lang.repository.db.cache.LangRepositoryDBCache;

public class LangRepositoryFactory {

	private static LangRepository repo;
	
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDBCache();
		}
		return repo;
	}
}
