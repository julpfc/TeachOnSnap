package com.julvez.pfc.teachonsnap.repository.lesson.test.db;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepository;

public class LessonTestRepositoryDB implements LessonTestRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		return (LessonTest) dbm.getQueryResultUnique("SQL_LESSONTEST_GET_TEST", LessonTest.class, idLessonTest);
	}

	@Override
	public List<Integer> getLessonTestQuestionIDs(int idLessonTest) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST", null, idLessonTest);
						
		return ids; 
	}

	@Override
	public Question getQuestion(int idQuestion) {
		return (Question) dbm.getQueryResultUnique("SQL_LESSONTEST_GET_QUESTION", Question.class, idQuestion);
	}

	@Override
	public List<Integer> getQuestionAnswerIDs(int idQuestion) {
		@SuppressWarnings("unchecked")
		List<Integer> ids =  (List<Integer>) dbm.getQueryResultList("SQL_LESSONTEST_GET_ANSWERIDS_FROM_IDQUESTION", null, idQuestion);
		
		return ids;
	}

	@Override
	public Answer getAnswer(int idAnswer) {
		return (Answer) dbm.getQueryResultUnique("SQL_LESSONTEST_GET_ANSWER", Answer.class, idAnswer);
	}

	@Override
	public int getLessonTestID(int idLesson) {
		int id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_LESSONTEST_GET_LESSONTESTID_FROM_LESSONID", null, idLesson);
		if(obj!=null)
			id = Integer.parseInt(obj.toString());
		return id; 
	}

	@Override
	public void publish(int idLessonTest, int idLesson) {
		dbm.updateQuery("SQL_LESSONTEST_ADD_PUBLISHED", idLessonTest);
		
	}

	@Override
	public void unpublish(int idLessonTest, int idLesson) {
		dbm.updateQuery("SQL_LESSONTEST_REMOVE_PUBLISHED", idLessonTest);
		
	}



}
