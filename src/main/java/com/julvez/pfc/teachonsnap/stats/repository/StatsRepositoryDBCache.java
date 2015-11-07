package com.julvez.pfc.teachonsnap.stats.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.test.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

public class StatsRepositoryDBCache implements StatsRepository {

	private StatsRepositoryDB repoDB = new StatsRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager(); 

	@Override
	public int createVisit(String ip) {
		return (int)cache.updateImplCached(repoDB, null, null, ip);		
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		return (boolean)cache.updateImplCached(repoDB, null, null, idVisit, idUser);
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
