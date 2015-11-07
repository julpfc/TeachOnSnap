package com.julvez.pfc.teachonsnap.service.visit.repository;

import com.julvez.pfc.teachonsnap.service.visit.repository.db.cache.VisitRepositoryDBCache;

public class VisitRepositoryFactory {
	private static VisitRepository repo;
	
	public static VisitRepository getRepository(){
		if(repo==null){
			repo = new VisitRepositoryDBCache();
		}
		return repo;
	}
}
