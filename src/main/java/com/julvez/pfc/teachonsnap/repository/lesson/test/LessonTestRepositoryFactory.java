package com.julvez.pfc.teachonsnap.repository.lesson.test;

import com.julvez.pfc.teachonsnap.repository.lesson.test.db.cache.LessonTestRepositoryDBCache;


public class LessonTestRepositoryFactory {

	private static LessonTestRepository repo;
	
	public static LessonTestRepository getRepository(){
		if(repo==null){
			repo = new LessonTestRepositoryDBCache();
		}
		return repo;
	}
}
