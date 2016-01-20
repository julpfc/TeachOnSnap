package com.julvez.pfc.teachonsnap.stats.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

public class StatsRepositoryDBCache implements StatsRepository {

	private StatsRepositoryDB repoDB = new StatsRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager(); 

	@Override
	public int createVisit(String ip) {
		cache.clearCache("getVisitsLastMonth");
		cache.clearCache("getVisitsLastYear");
		return (int)cache.updateImplCached(repoDB, null, null, ip);		
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		return (boolean)cache.updateImplCached(repoDB, null, null, idVisit, idUser);
	}

	@Override
	public boolean saveLesson(int idVisit, int idLesson) {
		boolean result = (boolean)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(idLesson),stringManager.getKey(idLesson),
				stringManager.getKey(idLesson),stringManager.getKey(idLesson),stringManager.getKey(idLesson),
				stringManager.getKey(idLesson),stringManager.getKey(idLesson),stringManager.getKey(idLesson)}, 
				new String[]{"getLessonVisitsLastMonth","getLessonVisitsLastYear",
				"getAuthorVisitsLastMonth","getAuthorVisitsLastYear","getAuthorLessonsVisitsLastMonth",
				"getAuthorLessonsVisitsLastYear","getAuthorLessonMediaVisitsLastMonth","getAuthorLessonMediaVisitsLastYear"}, idVisit, idLesson);
		cache.incCacheValue("getLessonViewsCount", stringManager.getKey(idLesson));
		cache.clearCache("getLessonsVisitsLastMonth");
		cache.clearCache("getLessonsVisitsLastYear");
		cache.clearCache("getAuthorsVisitsLastMonth");
		cache.clearCache("getAuthorsVisitsLastYear");
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
				stringManager.getKey(userTest.getIdLessonTest()),stringManager.getKey(userTest.getIdLessonTest()),stringManager.getKey(userTest.getIdLessonTest())}, 
				new String[]{"getUserTestRank","getUserIDsTestRank","getStatsLessonTestNumTests", "getStatsLessonTestQuestionKOs"}, visit, userTest, betterRank);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Short> getUserIDsTestRank(int idLessonTest) {
		return (List<Short>)cache.executeImplCached(repoDB, idLessonTest);
	}

	@Override
	public boolean saveTag(int idVisit, int idTag) {
		boolean result = (boolean)cache.updateImplCached(repoDB, null, null, idVisit, idTag);
		cache.incCacheValue("getTagViewsCount", stringManager.getKey(idTag));
		return result;	
	}

	@Override
	public int getTagViewsCount(int idTag) {
		return (int)cache.executeImplCached(repoDB, idTag);
	}

	@Override
	public int getStatsLessonTestNumTests(int idLessonTest) {
		return (int)cache.executeImplCached(repoDB, idLessonTest);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getStatsLessonTestQuestionKOs(int idLessonTest) {
		return (Map<String, String>)cache.executeImplCached(repoDB, idLessonTest);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getLessonVisitsLastMonth(int idLesson) {		
		return (List<StatsData>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getLessonVisitsLastYear(int idLesson) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorVisitsLastMonth(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorVisitsLastYear(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorLessonsVisitsLastMonth(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorLessonsVisitsLastYear(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorLessonMediaVisitsLastMonth(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorLessonMediaVisitsLastYear(int idUser) {
		return (List<StatsData>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getVisitsLastMonth() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getVisitsLastYear() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getLessonsVisitsLastMonth() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getLessonsVisitsLastYear() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorsVisitsLastMonth() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StatsData> getAuthorsVisitsLastYear() {
		return (List<StatsData>)cache.executeImplCached(repoDB);
	}
	
}
