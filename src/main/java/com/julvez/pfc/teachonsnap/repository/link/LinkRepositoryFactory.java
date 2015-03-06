package com.julvez.pfc.teachonsnap.repository.link;

import com.julvez.pfc.teachonsnap.repository.link.db.cache.LinkRepositoryDBCache;


public class LinkRepositoryFactory {

	private static LinkRepository repo;
	
	public static LinkRepository getRepository(){
		if(repo==null){
			repo = new LinkRepositoryDBCache();
		}
		return repo;
	}
}
