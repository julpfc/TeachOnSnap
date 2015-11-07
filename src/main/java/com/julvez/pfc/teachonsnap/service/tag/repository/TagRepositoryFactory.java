package com.julvez.pfc.teachonsnap.service.tag.repository;

import com.julvez.pfc.teachonsnap.service.tag.repository.db.cache.TagRepositoryDBCache;


public class TagRepositoryFactory {

	private static TagRepository repo;
	
	public static TagRepository getRepository(){
		if(repo==null){
			repo = new TagRepositoryDBCache();
		}
		return repo;
	}
}
