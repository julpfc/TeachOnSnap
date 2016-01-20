package com.julvez.pfc.teachonsnap.lessontest.repository;



public class LessonTestRepositoryFactory {

	private static LessonTestRepository repo;
	
	public static LessonTestRepository getRepository(){
		if(repo==null){
			repo = new LessonTestRepositoryDBCache();
		}
		return repo;
	}
}
