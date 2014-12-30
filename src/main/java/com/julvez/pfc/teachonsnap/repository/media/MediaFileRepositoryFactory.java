package com.julvez.pfc.teachonsnap.repository.media;

import com.julvez.pfc.teachonsnap.repository.media.db.cache.MediaFileRepositoryDBCache;


public class MediaFileRepositoryFactory {

	private static MediaFileRepository repo;
	
	public static MediaFileRepository getRepository(){
		if(repo==null){
			repo = new MediaFileRepositoryDBCache();
		}
		return repo;
	}
}
