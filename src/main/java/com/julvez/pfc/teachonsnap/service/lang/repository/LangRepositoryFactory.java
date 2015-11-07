package com.julvez.pfc.teachonsnap.service.lang.repository;


public class LangRepositoryFactory {

	private static LangRepository repo;
	
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDBCache();
		}
		return repo;
	}
}
