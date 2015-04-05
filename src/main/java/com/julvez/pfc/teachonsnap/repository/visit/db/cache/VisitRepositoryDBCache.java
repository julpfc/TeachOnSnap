package com.julvez.pfc.teachonsnap.repository.visit.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.user.test.UserLessonTest;
import com.julvez.pfc.teachonsnap.model.visit.UserTestRank;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepository;
import com.julvez.pfc.teachonsnap.repository.visit.db.VisitRepositoryDB;

public class VisitRepositoryDBCache implements VisitRepository {

	private VisitRepositoryDB repoDB = new VisitRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager(); 

	@Override
	public int createVisit(String ip) {
		return (int)cache.updateImplCached(repoDB, null, null, ip);		
	}

	@Override
	public int saveUser(int idVisit, int idUser) {
		return (int)cache.updateImplCached(repoDB, null, null, idVisit, idUser);
	}

	@Override
	public boolean saveLesson(int idVisit, int idLesson) {
		boolean result = (boolean)cache.updateImplCached(repoDB, null, null, idVisit, idLesson);
		cache.incCacheValue("getLessonViewsCount", stringManager.getKey(idLesson));
		return result;
	}

	@Override
	public int getLessonViewsCount(int idLesson) {
		return (int)cache.executeImplCached(repoDB, idLesson);
	}

	
	@Override
	public UserTestRank getUserTestRank(int idLessonTest, int idUser) {
		return (UserTestRank)cache.executeImplCached(repoDB, idLessonTest, idUser);
	}

	@Override
	public boolean saveUserTest(Visit visit, UserLessonTest userTest, boolean betterRank) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{
				stringManager.getKey(userTest.getIdLessonTest(), visit.getUser().getId()),
				stringManager.getKey(userTest.getIdLessonTest())}, 
				new String[]{"getUserTestRank","getUserIDsTestRank"}, visit, userTest, betterRank);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Short> getUserIDsTestRank(int idLessonTest) {
		return (List<Short>)cache.executeImplCached(repoDB, idLessonTest);
	}

	

}
