package com.julvez.pfc.teachonsnap.service.lesson.repository.test;

import com.julvez.pfc.teachonsnap.service.lesson.repository.test.db.cache.LessonTestRepositoryDBCache;


public class LessonTestRepositoryFactory {

	private static LessonTestRepository repo;
	
	public static LessonTestRepository getRepository(){
		if(repo==null){
			repo = new LessonTestRepositoryDBCache();
		}
		return repo;
	}
}
