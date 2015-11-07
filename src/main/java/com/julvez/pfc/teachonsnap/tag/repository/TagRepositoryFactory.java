package com.julvez.pfc.teachonsnap.tag.repository;



public class TagRepositoryFactory {

	private static TagRepository repo;
	
	public static TagRepository getRepository(){
		if(repo==null){
			repo = new TagRepositoryDBCache();
		}
		return repo;
	}
}
