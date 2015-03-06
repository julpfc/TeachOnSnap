package com.julvez.pfc.teachonsnap.repository.tag;

import com.julvez.pfc.teachonsnap.repository.tag.db.cache.TagRepositoryDBCache;


public class TagRepositoryFactory {

	private static TagRepository repo;
	
	public static TagRepository getRepository(){
		if(repo==null){
			repo = new TagRepositoryDBCache();
		}
		return repo;
	}
}
