package com.julvez.pfc.teachonsnap.lessontest.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class LessonTestRepositoryDB implements LessonTestRepository {

	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
		
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 */
	public LessonTestRepositoryDB(DBManager dbm) {
		if(dbm == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.dbm = dbm;
	}

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
		
		if(question != null){
			//Begin database transaction
			Object session = dbm.beginTransaction();
			
			//Create question
			idQuestion = (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_LESSONTEST_CREATE_QUESTION", question.getIdLessonTest(),
					question.getPriority(), question.getText());
			
			if(idQuestion>0){
				question.setId(idQuestion);
				
				//Create answers
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
				
				//Increase number of test's questions
				if(idQuestion>0){
					if(changeLessonTestNumQuestions(session, question.getIdLessonTest(), true) != 1){
						idQuestion = -1;
					}
				}
			}
			//Commit if success, rollback otherwise 
			dbm.endTransaction(idQuestion>0, session);
		}
		return idQuestion;
	}

	
	

	@Override
	public void removeQuestion(LessonTest test, Question question) {				
		Object session = dbm.beginTransaction();		
		boolean removed = removeQuestion(session, test, question);		
		dbm.endTransaction(removed, session);		
	}

	@Override
	public void removeLessonTest(LessonTest test) {
		int affectedRows = -1;
		
		if(test != null){
			//Begin database transaction
			Object session = dbm.beginTransaction();
			
			//Remove questions
			for(Question question:test.getQuestions()){
				if(!removeQuestion(session, test, question)){
					affectedRows = -1;
					break;
				}
				else affectedRows = 1;
			}
			
			//Remove test
			if(affectedRows>0 || test.getQuestions()==null || test.getQuestions().size()==0){
				affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_LESSONTEST", test.getId());							
			}
			
			//Commit if success, rollback otherwise
			dbm.endTransaction(affectedRows>0, session);
		}		
	}

	@Override
	public int createLessonTest(int idLesson, boolean multipleChoice, int numAnswers) {
		int idLessonTest = -1;
		
		//Begin database transaction
		Object session = dbm.beginTransaction();
		
		//create test
		idLessonTest = (int)dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_LESSONTEST_CREATE", idLesson, multipleChoice, numAnswers);
		
		//Create test as draft
		if(idLessonTest>0){
			unpublish(session, idLessonTest);			
		}
		//Commit if success, rollback otherwise		
		dbm.endTransaction(idLessonTest>0, session);
		
		return idLessonTest;		
	}

	/**
	 * Unpublishes a test within an open database transaction.
	 * @param session Open database transaction
	 * @param idLessonTest Test to be unpublished
	 */
	private void unpublish(Object session, int idLessonTest) {
		dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_PUBLISHED", idLessonTest);		
	}

	/**
	 * Increases o decreases the number of test's questions within
	 * an open database transaction.
	 * @param session Open database transaction
	 * @param idLessonTest Test's id
	 * @param inc indicates if it's a positive increment
	 * @return number of rows affected
	 */
	private int changeLessonTestNumQuestions(Object session, int idLessonTest, boolean inc) {
		return dbm.updateQuery_NoCommit(session, inc?"SQL_LESSONTEST_ADD_NUM_QUESTIONS":"SQL_LESSONTEST_DEC_NUM_QUESTIONS", idLessonTest);
	}
	
	/**
	 * Removes a question within an open database transaction
	 * @param session Open database transaction
	 * @param test Test
	 * @param question to be removed
	 * @return true if success
	 */
	private boolean removeQuestion(Object session, LessonTest test, Question question) {
	
		int affectedRows = -1;
		
		if(question != null && test!= null){
			
			//Remove answers from question
			affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_ANSWERS", question.getId());
			
			if(affectedRows>=0){
				//remove question
				affectedRows = dbm.updateQuery_NoCommit(session, "SQL_LESSONTEST_REMOVE_QUESTION", question.getId());
				
				if(affectedRows>=0){
					//decrease number of test's questions
					affectedRows = changeLessonTestNumQuestions(session, question.getIdLessonTest(), false);
					
					if(affectedRows>=0){
						//Recalculate question's positions
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
		}
		
		return affectedRows>=0;
	}

}
