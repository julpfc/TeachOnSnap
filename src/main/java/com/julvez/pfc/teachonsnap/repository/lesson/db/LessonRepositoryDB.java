package com.julvez.pfc.teachonsnap.repository.lesson.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Question;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;



public class LessonRepositoryDB implements LessonRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public Lesson getLesson(int idLesson) {
		return (Lesson) dbm.getQueryResultUnique("SQL_LESSON_GET_LESSON", Lesson.class, idLesson);
	}
	
	@Override
	public List<Integer> getLessonIDsFromTag(String tag) {
		
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LESSONIDS_FROM_TAG", null, tag);
						
		return ids; 
	}

	@Override
	public int getLessonIDFromURI(String lessonURI) {
		return (int) dbm.getQueryResultUnique("SQL_LESSON_GET_LESSONID_FROM_URI", null, lessonURI);
	}

	@Override
	public List<Integer> getLessonTagIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LESSONTAGIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public List<Integer> getLinkedLessonIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LINKEDLESSONIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public List<Integer> getMoreInfoLinkIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_MOREINFOLINKIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public List<Integer> getSourceLinkIDs(int idLesson) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_SOURCELINKIDS", null, idLesson);
						
		return ids;
	}

	@Override
	public Tag getTag(int idTag) {
		return (Tag) dbm.getQueryResultUnique("SQL_LESSON_GET_TAG", Tag.class, idTag);
	}

	@Override
	public Link getLink(int idLink) {
		return (Link) dbm.getQueryResultUnique("SQL_LESSON_GET_LINK", Link.class, idLink);
	}

	@Override
	public List<Integer> getLastLessonIDs() {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LASTLESSONIDS", null, new Object[0]);
						
		return ids;
	}

	@Override
	public List<Object[]> getCloudTags() {
		@SuppressWarnings("unchecked")
		List<Object[]> ids =  (List<Object[]>) dbm.getQueryResultList("SQL_LESSON_GET_IDTAGWEIGHTS", null, new Object[0]);
		return ids;
	}

	@Override
	public List<Integer> getLessonIDsFromAuthor(String author) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR", null, author);
						
		return ids; 
	}

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		return (LessonTest) dbm.getQueryResultUnique("SQL_LESSON_GET_TEST", LessonTest.class, idLessonTest);
	}

	@Override
	public List<Integer> getLessonTestQuestionIDs(int idLessonTest) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_QUESTIONIDS_FROM_IDLESSONTEST", null, idLessonTest);
						
		return ids; 
	}

	@Override
	public Question getQuestion(int idQuestion) {
		return (Question) dbm.getQueryResultUnique("SQL_LESSON_GET_QUESTION", Question.class, idQuestion);
	}

	@Override
	public List<Integer> getQuestionAnswerIDs(int idQuestion) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSON_GET_ANSWERIDS_FROM_IDQUESTION", null, idQuestion);
		
		return ids;
	}

	@Override
	public Answer getAnswer(int idAnswer) {
		return (Answer) dbm.getQueryResultUnique("SQL_LESSON_GET_ANSWER", Answer.class, idAnswer);
	}

}
