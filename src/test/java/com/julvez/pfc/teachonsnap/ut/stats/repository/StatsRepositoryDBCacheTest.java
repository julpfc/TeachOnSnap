package com.julvez.pfc.teachonsnap.ut.stats.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryDB;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryDBCache;

public class StatsRepositoryDBCacheTest extends StatsRepositoryTest {

	@Mock
	private StatsRepositoryDB repoDB;	
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected StatsRepository getRepository() {
		return new StatsRepositoryDBCache(repoDB, cache, stringManager);
	}	
	
	@Test
	public void testGetVisit(){
		Visit visit = new Visit(idVisit);
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdVisit))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idVisit))).thenReturn(visit);
		
		super.testGetVisit();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());	
	}	
		
	@Test
	public void testCreateVisit() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString())).thenReturn(invalidIdVisit);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(ip))).thenReturn(idVisit);
		
		super.testCreateVisit();
		
		verify(cache, times(4)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString());
	}

	@Test
	public void testSaveUser() {
		Visit visit = mock(Visit.class);
		when(visit.getIdUser()).thenReturn(invalidIdUser, invalidIdUser, idUser);
		
		when(cache.executeImplCached(eq(repoDB), eq(idVisit))).thenReturn(visit);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt())).thenReturn(false, true);
		
		super.testSaveUser();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSaveLesson() {
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(0,0,1);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt())).thenReturn(false, true);
		
		super.testSaveLesson();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testGetLessonViewsCount() {
		when(cache.executeImplCached(eq(repoDB), anyInt())).thenReturn(0);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(count);
		super.testGetLessonViewsCount();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetUserTestRank() {
		UserTestRank rank = new UserTestRank();	
		
		when(cache.executeImplCached(eq(repoDB), anyInt(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLessonTest), eq(idUser))).thenReturn(rank);
		
		super.testGetUserTestRank();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB), anyInt(), anyInt());
	}

	@Test
	public void testSaveUserTest() {
		UserTestRank rank = mock(UserTestRank.class);
		when(rank.getPoints()).thenReturn(count, count, 2*count);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLessonTest), eq(idUser))).thenReturn(null, null, rank, rank, rank);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Visit.class), any(UserLessonTest.class), anyBoolean()))
			.thenReturn(true, true, true);
		
		super.testSaveUserTest();
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Visit.class), any(UserLessonTest.class), anyBoolean());
	}

	@Test
	public void testGetUserIDsTestRank() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLessonTest))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLessonTest))).thenReturn(null);
		
		super.testGetUserIDsTestRank();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testSaveTag() {
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(0,0,1);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt())).thenReturn(false, true);
		
		super.testSaveTag();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testGetTagViewsCount() {
		when(cache.executeImplCached(eq(repoDB), anyInt())).thenReturn(0);
		when(cache.executeImplCached(eq(repoDB), eq(idTag))).thenReturn(count);
		super.testGetTagViewsCount();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetStatsLessonTestNumTests() {
		when(cache.executeImplCached(eq(repoDB), anyInt())).thenReturn(0);
		when(cache.executeImplCached(eq(repoDB), eq(idLessonTest))).thenReturn(count);
		super.testGetStatsLessonTestNumTests();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetStatsLessonTestQuestionKOs() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");		
		
		when(cache.executeImplCached(eq(repoDB), eq(idLessonTest))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLessonTest))).thenReturn(new HashMap<String, String>());
		
		super.testGetStatsLessonTestQuestionKOs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetLessonVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		
		super.testGetLessonVisitsLastMonth();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetLessonVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		
		super.testGetLessonVisitsLastYear();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorVisitsLastMonth();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorVisitsLastYear();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorLessonsVisitsLastMonth();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorLessonsVisitsLastYear();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorLessonMediaVisitsLastMonth();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(stats);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorLessonMediaVisitsLastYear();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetVisitsLastMonth();
		
		verify(cache).executeImplCached(eq(repoDB));	
	}

	@Test
	public void testGetVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetVisitsLastYear();
		
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetLessonsVisitsLastMonth();
		
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetLessonsVisitsLastYear();
		
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetAuthorsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetAuthorsVisitsLastMonth();
		
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetAuthorsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(stats);
				
		super.testGetAuthorsVisitsLastYear();
		
		verify(cache).executeImplCached(eq(repoDB));
	}
}
