package com.julvez.pfc.teachonsnap.it.stats;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.ut.stats.StatsServiceTest;

public class StatsServiceIT extends StatsServiceTest {

	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected StatsService getService() {		
		return StatsServiceFactory.getService();
	}

	@BeforeClass
	public static void setUpClass(){
		for(int i=2;i<=30;i++){
			DBManagerFactory.getManager().insertQueryAndGetLastInserID("SQL_LESSON_CREATE_LESSON",1,1,""+i,""+i);
		}
	}
	
	@AfterClass
	public static void cleanUpClass(){
		for(int i=2;i<=30;i++){
			DBManagerFactory.getManager().updateQuery("SQL_IT_LESSON_DELETE_LESSON", i);
		}		
		DBManagerFactory.getManager().updateQuery("SQL_IT_LESSON_RESET_LESSON_ID");
	}

	@Override
	public void testCreateVisit() {
		try{ super.testCreateVisit(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_STATS_DELETE_VISIT", 2);
		dbm.updateQuery("SQL_IT_STATS_DELETE_VISIT", 3);
		dbm.updateQuery("SQL_IT_STATS_DELETE_VISIT", 4);
		dbm.updateQuery("SQL_IT_STATS_DELETE_VISIT", 5);
		dbm.updateQuery("SQL_IT_STATS_RESET_VISIT_ID");
	}

	@Override
	public void testSaveUser() {
		cache.clearCache("getVisit");	
		test.createVisit(NULL_STRING);
		try{ super.testSaveUser();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_STATS_DELETE_USER", idVisit*2);
		dbm.updateQuery("SQL_IT_STATS_DELETE_VISIT", idVisit*2);
		dbm.updateQuery("SQL_IT_STATS_RESET_VISIT_ID");
	}

	@Override
	public void testSaveLesson() {
		cache.clearCache("getLessonViewsCount");		
		dbm.updateQuery("SQL_IT_STATS_DELETE_LESSON", idVisit);
		try{ super.testSaveLesson();} catch(Throwable t){ t.printStackTrace();}
	}

	@Override
	public void testSaveUserTest() {		
		cache.clearCache("getUserTestRank");
		try{ super.testSaveUserTest();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_STATS_DELETE_TESTRANK", idLessonTest);
		dbm.updateQuery("SQL_IT_STATS_DELETE_TEST", 2);
		dbm.updateQuery("SQL_IT_STATS_RESET_TEST_ID");		
	}

	@Override
	public void testGetUserTestRank() {
		dbm.insertQueryAndGetLastInserID("SQL_STATS_SAVE_USERTEST", idVisit, idLessonTest, 0);
		dbm.updateQuery("SQL_STATS_SAVE_USERTESTRANK", idLessonTest, idUser, 2,	1, 2, 1);
		try{ super.testGetUserTestRank();} catch(Throwable t){ t.printStackTrace();}		
		dbm.updateQuery("SQL_IT_STATS_DELETE_TESTRANK", idLessonTest);
		dbm.updateQuery("SQL_IT_STATS_DELETE_TEST", 2);
		dbm.updateQuery("SQL_IT_STATS_RESET_TEST_ID");
	}

	@Override
	public void testGetTestRanks() {
		dbm.insertQueryAndGetLastInserID("SQL_STATS_SAVE_USERTEST", idVisit, idLessonTest, 0);
		dbm.updateQuery("SQL_STATS_SAVE_USERTESTRANK", idLessonTest, idUser, 2,	1, 2, 1);
		try{ super.testGetTestRanks();} catch(Throwable t){ t.printStackTrace();}		
		dbm.updateQuery("SQL_IT_STATS_DELETE_TESTRANK", idLessonTest);
		dbm.updateQuery("SQL_IT_STATS_DELETE_TEST", 2);
		dbm.updateQuery("SQL_IT_STATS_RESET_TEST_ID");
	}

	@Override
	public void testSaveTag() {
		cache.clearCache("getTagViewsCount");		
		dbm.updateQuery("SQL_IT_STATS_DELETE_TAG", idVisit);
		try{ super.testSaveTag();} catch(Throwable t){ t.printStackTrace();}
	}
}
