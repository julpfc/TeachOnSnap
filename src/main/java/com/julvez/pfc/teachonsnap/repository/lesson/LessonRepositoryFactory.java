package com.julvez.pfc.teachonsnap.repository.lesson;

import com.julvez.pfc.teachonsnap.repository.lesson.db.LessonRepositoryDB;


public class LessonRepositoryFactory {

	private static LessonRepository repo;
	
	public static LessonRepository getRepository(){
		if(repo==null){
			repo = new LessonRepositoryDB();
		}
		return repo;
	}
}
