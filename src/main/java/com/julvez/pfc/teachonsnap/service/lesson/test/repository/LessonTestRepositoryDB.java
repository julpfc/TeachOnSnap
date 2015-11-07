package com.julvez.pfc.teachonsnap.service.lesson.test.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;

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
		Object session = dbm.beginTransaction();
		unpublish(session, idLessonTest);
		dbm.endTransaction(true, session);
	}
	
	private void unpublish(Object session, int idLessonTest) {
		dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_PUBLISHED", idLessonTest);		
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

	@Override
	public int createQuestion(Question question) {
		int idQuestion = -1;
		
		Object session = dbm.beginTransaction();
		idQuestion = (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_LESSONTEST_CREATE_QUESTION", question.getIdLessonTest(),
				question.getPriority(), question.getText());
		
		if(idQuestion>0){
			question.setId(idQuestion);
			
			for(Answer answer:question.getAnswers()){
				int idAnswer = (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_LESSONTEST_CREATE_ANSWER", idQuestion, 
						answer.getReason(), answer.getText(), answer.isCorrect());
				if(idAnswer >0){
					answer.setId(idAnswer);
					answer.setIdQuestion(idQuestion);
				}
				else{
					idQuestion = -1;
					break;
				}
				
			}	
			
			if(idQuestion>0){
				if(changeLessonTestNumQuestions(session, question.getIdLessonTest(), true) != 1){
					idQuestion = -1;
				}
			}
		}
		dbm.endTransaction(idQuestion>0, session);
		
		return idQuestion;
	}

	
	private int changeLessonTestNumQuestions(Object session, int idLessonTest, boolean inc) {
		return dbm.updateQuery_NoCommit(session, inc?"SQL_LESSONTEST_ADD_NUM_QUESTIONS":"SQL_LESSONTEST_DEC_NUM_QUESTIONS", idLessonTest);
	}
	

	@Override
	public void removeQuestion(LessonTest test, Question question) {
				
		Object session = dbm.beginTransaction();
		
		boolean removed = removeQuestion(session, test, question);
		
		dbm.endTransaction(removed, session);
		
	}
	
	private boolean removeQuestion(Object session, LessonTest test, Question question) {
	
		int affectedRows = -1;
		
		affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_ANSWERS", question.getId());
		
		if(affectedRows>0){
			affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_QUESTION", question.getId());
			
			if(affectedRows>0){
				affectedRows = changeLessonTestNumQuestions(session, question.getIdLessonTest(),false);
				
				if(affectedRows>0){
					for(Question q:test.getQuestions()){
						if(q.getId() != question.getId()){
							if(question.getPriority()<q.getPriority()){								
								saveQuestion(q.getId(), q.getText(), (byte)(q.getPriority()-1), q.getIdLessonTest());
							}
						}
					} 
				}
			}			
		}
		
		return affectedRows>0;
	}

	@Override
	public void removeLessonTest(LessonTest test) {
		int affectedRows = -1;
		
		Object session = dbm.beginTransaction();
		
		for(Question question:test.getQuestions()){
			if(!removeQuestion(session, test, question)){
				affectedRows = -1;
				break;
			}
			else affectedRows = 1;
		}
		
		if(affectedRows>0 || test.getQuestions()==null || test.getQuestions().size()==0){
			affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_LESSONTEST", test.getId());							
		}
		
		dbm.endTransaction(affectedRows>0, session);
	
	}

	@Override
	public int createLessonTest(int idLesson, boolean multipleChoice, int numAnswers) {
		int idLessonTest = -1;
		
		Object session = dbm.beginTransaction();
		
		idLessonTest = (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_LESSONTEST_CREATE", idLesson, multipleChoice, numAnswers);
		
		if(idLessonTest>0){
			unpublish(session, idLessonTest);			
		}
				
		dbm.endTransaction(idLessonTest>0, session);
		
		return idLessonTest;		
	}

}
