package com.julvez.pfc.teachonsnap.service.link.repository;

import com.julvez.pfc.teachonsnap.service.link.repository.db.cache.LinkRepositoryDBCache;


public class LinkRepositoryFactory {

	private static LinkRepository repo;
	
	public static LinkRepository getRepository(){
		if(repo==null){
			repo = new LinkRepositoryDBCache();
		}
		return repo;
	}
}
