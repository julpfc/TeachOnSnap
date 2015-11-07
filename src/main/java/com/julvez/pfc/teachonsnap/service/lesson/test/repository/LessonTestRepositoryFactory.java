package com.julvez.pfc.teachonsnap.service.lesson.test.repository;



public class LessonTestRepositoryFactory {

	private static LessonTestRepository repo;
	
	public static LessonTestRepository getRepository(){
		if(repo==null){
			repo = new LessonTestRepositoryDBCache();
		}
		return repo;
	}
}
