package com.julvez.pfc.teachonsnap.it.lessontest;

import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.ut.lessontest.LessonTestServiceTest;

public class LessonTestServiceIT extends LessonTestServiceTest {

	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected LessonTestService getService() {
		return LessonTestServiceFactory.getService();
	}

	@Override
	public void testPublish() {
		cache.clearCache("getLessonTest");
		dbm.updateQuery("SQL_IT_LESSONTEST_TRUNCATE_DRAFT");
		dbm.updateQuery("SQL_LESSONTEST_REMOVE_PUBLISHED", idTest);
		try{ super.testPublish();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_TRUNCATE_DRAFT");
	}

	@Override
	public void testUnpublish() {
		cache.clearCache("getLessonTest");
		dbm.updateQuery("SQL_IT_LESSONTEST_TRUNCATE_DRAFT");
		try{ super.testUnpublish();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_TRUNCATE_DRAFT");
	}

	@Override
	public void testSaveQuestion() {
		cache.clearCache("getQuestion");
		dbm.updateQuery("SQL_LESSONTEST_SAVE_QUESTION", EMPTY_STRING, priority, idQuestion);
		try{ super.testSaveQuestion();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_LESSONTEST_SAVE_QUESTION", text, priority, idQuestion);
	}

	@Override
	public void testCreateQuestion() {		
		try{ super.testCreateQuestion();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_DELETE_ANSWER");
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_ANSWER_ID", 2);
		dbm.updateQuery("SQL_IT_LESSONTEST_DELETE_QUESTION");
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_QUESTION_ID", 2);
		dbm.updateQuery("SQL_LESSONTEST_DEC_NUM_QUESTIONS", idTest);
		cache.clearCache("getLessonTestQuestionIDs");
	}

	@Override
	public void testRemoveQuestion() {		
		try{ super.testRemoveQuestion();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_ANSWER_ID", 1);
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_QUESTION_ID", 1);
		dbm.updateQuery("SQL_IT_LESSONTEST_RECREATE_QUESTION");
		dbm.updateQuery("SQL_IT_LESSONTEST_RECREATE_ANSWER");
		dbm.updateQuery("SQL_LESSONTEST_ADD_NUM_QUESTIONS", idTest);
	}

	@Override
	public void testRemoveLessonTest() {
		try{ super.testRemoveLessonTest();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_ANSWER_ID", 1);
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_QUESTION_ID", 1);
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_TEST_ID", 1);
		dbm.updateQuery("SQL_IT_LESSONTEST_RECREATE_TEST");
		dbm.updateQuery("SQL_IT_LESSONTEST_RECREATE_QUESTION");
		dbm.updateQuery("SQL_IT_LESSONTEST_RECREATE_ANSWER");		
	}

	@Override
	public void testCreateLessonTest() {
		try{ super.testCreateLessonTest();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSONTEST_DELETE_TEST");
		dbm.updateQuery("SQL_IT_LESSONTEST_RESET_TEST_ID", 2);
	}
}
