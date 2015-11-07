package com.julvez.pfc.teachonsnap.link.repository;



public class LinkRepositoryFactory {

	private static LinkRepository repo;
	
	public static LinkRepository getRepository(){
		if(repo==null){
			repo = new LinkRepositoryDBCache();
		}
		return repo;
	}
}
