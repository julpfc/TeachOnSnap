package com.julvez.pfc.teachonsnap.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;


public class LessonRepositoryDB implements LessonRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public Lesson getLesson(int idLesson) {
		return dbm.getQueryResultUnique("SQL_LESSON_GET_LESSON", Lesson.class, idLesson);
	}
	

	@Override
	public int getLessonIDFromURI(String lessonURI) {
		int id = -1;
		Integer obj = dbm.getQueryResultUnique("SQL_LESSON_GET_LESSONID_FROM_URI", Integer.class, lessonURI);
		if(obj!=null)
			id = obj;
		return id; 
	}


	@Override
	public List<Integer> getLastLessonIDs(int firstResult) {
		int maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_LESSON_GET_LASTLESSONIDS", Integer.class, firstResult, maxResults + 1);
	}

	@Override
	public List<Integer> getLessonIDsFromAuthor(String author,int firstResult) {
		int maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR", Integer.class, author,firstResult, maxResults + 1);						
	}

	@Override
	public int createLesson(Lesson newLesson) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_LESSON_CREATE_LESSON", newLesson.getIdUser(),
				newLesson.getIdLanguage(),newLesson.getTitle(),newLesson.getURIname());
	}

	@Override
	public void saveLessonText(int idLesson, String newText) {
		dbm.updateQuery("SQL_LESSON_SAVE_TEXT", idLesson,newText,newText);
	}


	@Override
	public void saveLessonLanguage(int idLesson, short idLanguage) {
		dbm.updateQuery("SQL_LESSON_SAVE_LANGUAGE", idLanguage, idLesson);
		
	}


	@Override
	public boolean saveLessonTitle(Lesson lesson, String title, String URIName) {
		return dbm.updateQuery("SQL_LESSON_SAVE_TITLE", title, URIName, lesson.getId()) >= 0;
	}


	@Override
	public void removeLessonText(int idLesson) {
		dbm.updateQuery("SQL_LESSON_DELETE_TEXT", idLesson);
	}


	@Override
	public void publish(int idLesson) {
		dbm.updateQuery("SQL_LESSON_PUBLISH", idLesson);		
	}


	@Override
	public void unpublish(int idLesson) {
		dbm.updateQuery("SQL_LESSON_UNPUBLISH", idLesson);
	}


	@Override
	public List<Integer> getDraftLessonIDsFromUser(short idUser, int firstResult) {
		int maxResults = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_LESSON_GET_DRAFT_LESSONIDS_FROM_USER", Integer.class, idUser, firstResult, maxResults + 1);
	}

}
