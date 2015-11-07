package com.julvez.pfc.teachonsnap.service.visit.repository;


public class VisitRepositoryFactory {
	private static VisitRepository repo;
	
	public static VisitRepository getRepository(){
		if(repo==null){
			repo = new VisitRepositoryDBCache();
		}
		return repo;
	}
}
