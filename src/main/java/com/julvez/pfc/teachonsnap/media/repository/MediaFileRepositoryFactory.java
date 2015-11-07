package com.julvez.pfc.teachonsnap.media.repository;



public class MediaFileRepositoryFactory {

	private static MediaFileRepository repo;
	
	public static MediaFileRepository getRepository(){
		if(repo==null){
			repo = new MediaFileRepositoryDBCache();
		}
		return repo;
	}
}
