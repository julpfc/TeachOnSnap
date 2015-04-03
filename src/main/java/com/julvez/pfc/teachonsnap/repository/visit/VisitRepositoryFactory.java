package com.julvez.pfc.teachonsnap.repository.visit;

import com.julvez.pfc.teachonsnap.repository.visit.db.cache.VisitRepositoryDBCache;

public class VisitRepositoryFactory {
	private static VisitRepository repo;
	
	public static VisitRepository getRepository(){
		if(repo==null){
			repo = new VisitRepositoryDBCache();
		}
		return repo;
	}
}
