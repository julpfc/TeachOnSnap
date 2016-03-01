package com.julvez.pfc.teachonsnap.stats.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.UserQuestion;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsPropertyName;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class StatsRepositoryDB implements StatsRepository {

	/** Database manager providing access/modification capabilities */
	private DBManager dbm;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param dbm Database manager providing access/modification capabilities
	 * @param properties Property manager providing access to properties files
	 */
	public StatsRepositoryDB(DBManager dbm, PropertyManager properties) {
		if(dbm == null || properties == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.dbm = dbm;
		this.properties = properties;
	}
	
	@Override
	public int createVisit(String ip) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_STATS_CREATE", ip);
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		return dbm.updateQuery("SQL_STATS_SAVE_USER", idVisit, idUser)>=0;
	}

	@Override
	public boolean saveLesson(int idVisit, int idLesson) {
		return dbm.updateQuery("SQL_STATS_SAVE_LESSON", idVisit, idLesson)>-1;
	}

	@Override
	public int getLessonViewsCount(int idLesson) {
		int count = 0;
		BigDecimal result = dbm.getQueryResultUnique("SQL_STATS_GET_LESSON_VIEWS_COUNT", BigDecimal.class, idLesson);
		
		if(result!=null){
			//Normalize database result
			count = result.multiply(BigDecimal.valueOf(100)).intValue();
		}
		
		return count;
	}

	@Override
	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank) {
		boolean saved = false;
		long affectedRows = 0;
		
		if(visit != null && userTest != null){
			//Begin transaction on database
			Object session = dbm.beginTransaction();
			
			//Create VisitTest
			long idVisitTest = dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_STATS_SAVE_USERTEST", 
					visit.getId(), userTest.getIdLessonTest(), userTest.getPoints());
			
			if(idVisitTest>0){
				//Save questions results
				for(UserQuestion question:userTest.getQuestions()){
					if(!question.isOK()){
						affectedRows = dbm.updateQuery_NoCommit(session, "SQL_STATS_SAVE_USERTEST_KO",
								idVisitTest, question.getId());
						if(affectedRows == -1) break;					
					}
				}
				
				//If improved result
				if(affectedRows>=0 && betterRank){
				
					//Get current number of attempts
					int attempts = getUserTestCount(session, userTest.getIdLessonTest(), visit.getUser().getId());
					
					//Update best result
					if(attempts>=0){
						affectedRows = dbm.updateQuery_NoCommit(session, "SQL_STATS_SAVE_USERTESTRANK",
								userTest.getIdLessonTest(), visit.getUser().getId(), idVisitTest,
								attempts, idVisitTest, attempts);
						
						saved = affectedRows>0;
					}				
				}
				else if(affectedRows>=0){
					saved = true;
				}
			}
			//Commit if success, rollback otherwise
			dbm.endTransaction(saved, session);		
		}
		return saved;
	}

	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		return dbm.getQueryResultUnique("SQL_STATS_GET_USERTESTRANK", UserTestRank.class, idLessonTest, idUser);
	}

	@Override
	public List<Short> getUserIDsTestRank(int idLessonTest) {
		long limit = properties.getNumericProperty(StatsPropertyName.LIMIT_USER_RANKING);		
		return dbm.getQueryResultList("SQL_STATS_GET_USERIDS_TESTRANK", Short.class, idLessonTest, limit);
	}

	@Override
	public boolean saveTag(int idVisit, int idTag) {
		return dbm.updateQuery("SQL_STATS_SAVE_TAG", idVisit, idTag)>-1;
	}

	@Override
	public int getTagViewsCount(int idTag) {
		int count = 0;
		BigDecimal result = dbm.getQueryResultUnique("SQL_STATS_GET_TAG_VIEWS_COUNT", BigDecimal.class, idTag);
		
		if(result!=null){
			//Normalize database result
			count = result.multiply(BigDecimal.valueOf(100)).intValue();
		}
		
		return count;
	}

	@Override
	public int getStatsLessonTestNumTests(int idLessonTest) {
		int numTests = 0;
		BigInteger result = dbm.getQueryResultUnique("SQL_STATS_GET_STATS_LESSONTEST_NUM_VISIT_TESTS", BigInteger.class, idLessonTest);
		
		if(result!=null){
			//Normalize database result
			numTests = result.intValue();
		}
		
		return numTests;
	}

	@Override
	public Map<String, String> getStatsLessonTestQuestionKOs(int idLessonTest) {
		Map<String, String> questionKOs = new HashMap<String, String>();
		
		List<Object[]> results = dbm.getQueryResultList("SQL_STATS_GET_STATS_LESSONTEST_QUESTION_KOS", Object[].class, idLessonTest);
		
		if(results != null){
			//Save distribution in a map
			for(Object[] obj:results){
				questionKOs.put("["+ obj[0].toString() + "]", obj[1].toString());
			}			
		}
		
		return questionKOs;
	}

	@Override
	public List<StatsData> getLessonVisitsLastMonth(int idLesson) {
		return dbm.getQueryResultList("SQL_STATS_GET_LESSON_VISITS_LASTMONTH_STATSDATA", StatsData.class, idLesson);
	}

	@Override
	public List<StatsData> getLessonVisitsLastYear(int idLesson) {
		return dbm.getQueryResultList("SQL_STATS_GET_LESSON_VISITS_LASTYEAR_STATSDATA", StatsData.class, idLesson);
	}

	@Override
	public List<StatsData> getAuthorVisitsLastMonth(int idUser) {
		return dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_VISITS_LASTMONTH_STATSDATA", StatsData.class, idUser);
	}

	@Override
	public List<StatsData> getAuthorVisitsLastYear(int idUser) {
		return dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_VISITS_LASTYEAR_STATSDATA", StatsData.class, idUser);
	}

	@Override
	public List<StatsData> getAuthorLessonsVisitsLastMonth(int idUser) {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);		
		List<StatsData> stats = dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTMONTH_STATSDATA", StatsData.class, idUser, limit);
		if(stats != null) Collections.reverse(stats);
		return stats; 
	}

	@Override
	public List<StatsData> getAuthorLessonsVisitsLastYear(int idUser) {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);		
		List<StatsData> stats = dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTYEAR_STATSDATA", StatsData.class, idUser, limit);
		if(stats != null) Collections.reverse(stats);
		return stats; 
	}

	@Override
	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(int idUser) {
		return dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTMONTH_STATSDATA", StatsData.class, idUser);
	}

	@Override
	public List<StatsData> getAuthorLessonMediaVisitsLastYear(int idUser) {
		return dbm.getQueryResultList("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTYEAR_STATSDATA", StatsData.class, idUser);
	}

	@Override
	public List<StatsData> getVisitsLastMonth() {
		return dbm.getQueryResultList("SQL_STATS_GET_VISITS_LASTMONTH_STATSDATA", StatsData.class, new Object[0]);
	}

	@Override
	public List<StatsData> getVisitsLastYear() {
		return dbm.getQueryResultList("SQL_STATS_GET_VISITS_LASTYEAR_STATSDATA", StatsData.class, new Object[0]);
	}

	@Override
	public List<StatsData> getLessonsVisitsLastMonth() {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		List<StatsData> stats =  dbm.getQueryResultList("SQL_STATS_GET_LESSONS_VISITS_LASTMONTH_STATSDATA", StatsData.class, limit);
		if(stats != null) Collections.reverse(stats);
		return stats;
	}

	@Override
	public List<StatsData> getLessonsVisitsLastYear() {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		List<StatsData> stats =  dbm.getQueryResultList("SQL_STATS_GET_LESSONS_VISITS_LASTYEAR_STATSDATA", StatsData.class, limit);
		if(stats != null) Collections.reverse(stats);
		return stats;
	}

	@Override
	public List<StatsData> getAuthorsVisitsLastMonth() {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		List<StatsData> stats =  dbm.getQueryResultList("SQL_STATS_GET_AUTHORS_VISITS_LASTMONTH_STATSDATA", StatsData.class, limit);
		if(stats != null) Collections.reverse(stats);
		return stats;
	}

	@Override
	public List<StatsData> getAuthorsVisitsLastYear() {
		long limit = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
		List<StatsData> stats =  dbm.getQueryResultList("SQL_STATS_GET_AUTHORS_VISITS_LASTYEAR_STATSDATA", StatsData.class, limit);
		if(stats != null) Collections.reverse(stats);
		return stats;
	}
	
	/**
	 * Returns current number of attempts for this user and test within the 
	 * current transaction.
	 * @param session Open database transaction.
	 * @param idLessonTest Test
	 * @param idUser User
	 * @return Attempts for this user and test.
	 */
	private int getUserTestCount(Object session, int idLessonTest, int idUser) {
		int attempts = -1;
		//Get stats from database
		BigInteger count = dbm.getQueryResultUnique_NoCommit(session, "SQL_STATS_GET_USERTEST_COUNT",
				BigInteger.class, idLessonTest, idUser);
		
		if(count!=null){
			//Convert to int
			attempts = count.intValue();
		}
		
		return attempts;
	}

	@Override
	public Visit getVisit(int idVisit) {
		return dbm.getQueryResultUnique("SQL_STATS_GET_VISIT", Visit.class, idVisit);
	}
}
