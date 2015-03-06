package com.julvez.pfc.teachonsnap.repository.lesson.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;


public class LessonRepositoryDB implements LessonRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public Lesson getLesson(int idLesson) {
		return (Lesson) dbm.getQueryResultUnique("SQL_LESSON_GET_LESSON", Lesson.class, idLesson);
	}
	

	@Override
	public int getLessonIDFromURI(String lessonURI) {
		int id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_LESSON_GET_LESSONID_FROM_URI", null, lessonURI);
		if(obj!=null)
			id = Integer.parseInt(obj.toString());
		return id; 
	}

	@Override
	public List<Integer> getLinkedLessonIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LINKEDLESSONIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public List<Integer> getLastLessonIDs(int firstResult) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LASTLESSONIDS", null, firstResult);
						
		return ids;
	}

	@Override
	public List<Integer> getLessonIDsFromAuthor(String author,int firstResult) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR", null, author,firstResult);
						
		return ids; 
	}

	@Override
	public int createLesson(Lesson newLesson) {
		return (int)dbm.updateQuery("SQL_LESSON_CREATE_LESSON", newLesson.getIdUser(),
				newLesson.getIdLanguage(),newLesson.getTitle(),newLesson.getURIname());
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		dbm.updateQuery("SQL_LESSON_SAVE_TEXT", idLesson,newText,newText);
	}

}
