package com.julvez.pfc.teachonsnap.service.link.repository;



public class LinkRepositoryFactory {

	private static LinkRepository repo;
	
	public static LinkRepository getRepository(){
		if(repo==null){
			repo = new LinkRepositoryDBCache();
		}
		return repo;
	}
}
