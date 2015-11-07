package com.julvez.pfc.teachonsnap.stats.repository;

import java.math.BigInteger;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.UserQuestion;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.model.StatsPropertyName;

public class StatsRepositoryDB implements StatsRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public int createVisit(String ip) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_VISIT_CREATE", ip);
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		return dbm.updateQuery("SQL_VISIT_SAVE_USER", idVisit, idUser)>0;
	}

	@Override
	public boolean saveLesson(int idVisit, int idLesson) {
		return dbm.updateQuery("SQL_VISIT_SAVE_LESSON", idVisit, idLesson)>-1;
	}

	@Override
	public int getLessonViewsCount(int idLesson) {
		return dbm.getQueryResultUnique("SQL_VISIT_GET_LESSON_VIEWS_COUNT", Integer.class, idLesson);
	}

	@Override
	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank) {
		boolean saved = false;
		long affectedRows = 0;
		
		Object session = dbm.beginTransaction();
		
		long idVisitTest = dbm.insertQueryAndGetLastInserID_NoCommit(session, "SQL_VISIT_SAVE_USERTEST", 
				visit.getId(), userTest.getIdLessonTest(), userTest.getPoints());
		
		if(idVisitTest>0){
			for(UserQuestion question:userTest.getQuestions()){
				if(!question.isOK()){
					affectedRows = dbm.updateQuery_NoCommit(session, "SQL_VISIT_SAVE_USERTEST_KO",
							idVisitTest, question.getId());
					if(affectedRows == -1) break;					
				}
			}
			
			if(affectedRows>=0 && betterRank){
			
				int attempts = getUserTestCount(session, userTest.getIdLessonTest(), visit.getUser().getId());
				
				if(attempts>=0){
					affectedRows = dbm.updateQuery_NoCommit(session, "SQL_VISIT_SAVE_USERTESTRANK",
							userTest.getIdLessonTest(), visit.getUser().getId(), idVisitTest,
							attempts, idVisitTest, attempts);
					
					saved = affectedRows>0;
				}				
			}
			else if(affectedRows>=0){
				saved = true;
			}
		}
		
		dbm.endTransaction(saved, session);		
		
		return saved;
	}

	private int getUserTestCount(Object session, int idLessonTest, int idUser) {
		int attempts = -1;
		BigInteger count = dbm.getQueryResultUnique_NoCommit(session, "SQL_VISIT_GET_USERTEST_COUNT",
				BigInteger.class, idLessonTest, idUser);
		
		if(count!=null){
			attempts = count.intValue();
		}
		
		return attempts;
	}

	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		return dbm.getQueryResultUnique("SQL_VISIT_GET_USERTESTRANK", UserTestRank.class, idLessonTest, idUser);
	}

	@Override
	public List<Short> getUserIDsTestRank(int idLessonTest) {
		int limit = properties.getNumericProperty(StatsPropertyName.LIMIT_USER_RANKING);
		
		return dbm.getQueryResultList("SQL_VISIT_GET_USERIDS_TESTRANK", Short.class, idLessonTest, limit);
	}

	

	
}
