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
		return dbm.getQueryResultUnique("SQL_LESSONTEST_GET_TEST", LessonTest.class, idLessonTest);
	}

	@Override
	public List<Integer> getLessonTestQuestionIDs(int idLessonTest) {
		return dbm.getQueryResultList("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST", Integer.class, idLessonTest);
	}

	@Override
	public Question getQuestion(int idQuestion) {
		return dbm.getQueryResultUnique("SQL_LESSONTEST_GET_QUESTION", Question.class, idQuestion);
	}

	@Override
	public List<Integer> getQuestionAnswerIDs(int idQuestion) {
		return dbm.getQueryResultList("SQL_LESSONTEST_GET_ANSWERIDS_FROM_IDQUESTION", Integer.class, idQuestion);		
	}

	@Override
	public Answer getAnswer(int idAnswer) {
		return dbm.getQueryResultUnique("SQL_LESSONTEST_GET_ANSWER", Answer.class, idAnswer);
	}

	@Override
	public int getLessonTestID(int idLesson) {
		int id = -1;
		Integer obj = dbm.getQueryResultUnique("SQL_LESSONTEST_GET_LESSONTESTID_FROM_LESSONID", Integer.class, idLesson);
		if(obj!=null)
			id = obj;
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

	@Override
	public void saveQuestion(int idQuestion, String text, byte priority, int idLessonTest) {
		dbm.updateQuery("SQL_LESSONTEST_SAVE_QUESTION", text, priority, idQuestion);
		
	}

	@Override
	public void saveAnswer(int idAnswer, String text, boolean correct, String reason,
			int idQuestion, int idLessonTest) {
		dbm.updateQuery("SQL_LESSONTEST_SAVE_ANSWER", text, correct, reason, idAnswer);		
	}



}
