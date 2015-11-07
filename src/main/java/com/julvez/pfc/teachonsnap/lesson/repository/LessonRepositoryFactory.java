package com.julvez.pfc.teachonsnap.lesson.repository;



public class LessonRepositoryFactory {

	private static LessonRepository repo;
	
	public static LessonRepository getRepository(){
		if(repo==null){
			repo = new LessonRepositoryDBCache();
		}
		return repo;
	}
}
