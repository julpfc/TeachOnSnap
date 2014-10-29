package com.julvez.pfc.teachonsnap.repository.lang;

import com.julvez.pfc.teachonsnap.repository.lang.db.LangRepositoryDB;

public class LangRepositoryFactory {

	private static LangRepository repo;
	
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDB();
		}
		return repo;
	}
}
