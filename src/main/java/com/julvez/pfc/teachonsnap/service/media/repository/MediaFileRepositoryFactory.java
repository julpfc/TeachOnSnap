package com.julvez.pfc.teachonsnap.service.media.repository;

import com.julvez.pfc.teachonsnap.service.media.repository.db.cache.MediaFileRepositoryDBCache;


public class MediaFileRepositoryFactory {

	private static MediaFileRepository repo;
	
	public static MediaFileRepository getRepository(){
		if(repo==null){
			repo = new MediaFileRepositoryDBCache();
		}
		return repo;
	}
}
