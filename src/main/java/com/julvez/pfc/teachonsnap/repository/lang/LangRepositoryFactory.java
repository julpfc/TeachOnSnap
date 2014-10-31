package com.julvez.pfc.teachonsnap.repository.lang;

import com.julvez.pfc.teachonsnap.repository.lang.db.cache.LangRepositoryDBCache;

public class LangRepositoryFactory {

	private static LangRepository repo;
	
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDBCache();
		}
		return repo;
	}
}
