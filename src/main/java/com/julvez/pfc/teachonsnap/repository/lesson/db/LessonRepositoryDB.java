package com.julvez.pfc.teachonsnap.repository.lesson.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;



public class LessonRepositoryDB implements LessonRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public Lesson getLesson(int id) {
		return (Lesson) dbm.getQueryResultUnique("SQL_LESSON_GET_LESSON", Lesson.class, id);
	}	
	
	@Override
	public List<Integer> getLessonIDsFromTag(String tag) {
		
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LESSONIDS_FROM_TAG", null, tag);
						
		return ids;
	}

}
