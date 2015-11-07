package com.julvez.pfc.teachonsnap.service.lesson.repository;

import com.julvez.pfc.teachonsnap.service.lesson.repository.db.cache.LessonRepositoryDBCache;


public class LessonRepositoryFactory {

	private static LessonRepository repo;
	
	public static LessonRepository getRepository(){
		if(repo==null){
			repo = new LessonRepositoryDBCache();
		}
		return repo;
	}
}
