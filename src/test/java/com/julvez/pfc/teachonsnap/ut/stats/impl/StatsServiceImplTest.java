package com.julvez.pfc.teachonsnap.ut.stats.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
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
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.impl.StatsServiceImpl;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.ut.stats.StatsServiceTest;

public class StatsServiceImplTest extends StatsServiceTest {

	@Mock
	private StatsRepository statsRepository;
	
	@Mock
	private UserService userService;
	
	@Override
	protected StatsService getService() {
		return new StatsServiceImpl(statsRepository, userService);
	}
	
	@Test
	public void testCreateVisit() {		
		when(statsRepository.createVisit(ip)).thenReturn(idVisit);
		super.testCreateVisit();
		verify(statsRepository, times(4)).createVisit(anyString());
	}

	@Test
	public void testSaveUser() {
		Visit visit = new Visit(idVisit);		
		when(statsRepository.getVisit(eq(idVisit))).thenReturn(visit);

		when(statsRepository.saveUser(idVisit, idUser)).thenReturn(true);
		super.testSaveUser();
		verify(statsRepository).saveUser(anyInt(), anyInt());
	}

	@Test
	public void testSaveLesson() {
		Visit visit = new Visit(idVisit);		
		when(statsRepository.getVisit(eq(idVisit))).thenReturn(visit);

		when(statsRepository.saveLesson(idVisit, idLesson)).thenReturn(true);
		when(statsRepository.getLessonViewsCount(idLesson)).thenReturn(0,0,1);
		super.testSaveLesson();
		verify(statsRepository).saveLesson(anyInt(), anyInt());
	}

	@Test
	public void testSaveUserTest() {
		UserTestRank userTest = mock(UserTestRank.class);
		when(userTest.getPoints()).thenReturn(count);
		when(statsRepository.getUserTestRank(idLessonTest, idUser)).thenReturn(null, null,userTest);
		
		when(statsRepository.saveUserTest(any(Visit.class), any(UserLessonTest.class), anyBoolean())).thenReturn(true);
		super.testSaveUserTest();
		verify(statsRepository, times(2)).saveUserTest(any(Visit.class), any(UserLessonTest.class), anyBoolean());
	}

	@Test
	public void testGetUserTestRank() {		
		UserTestRank userTest = mock(UserTestRank.class);
		when(statsRepository.getUserTestRank(idLessonTest, idUser)).thenReturn(userTest);		
		super.testGetUserTestRank();	
		verify(statsRepository).getUserTestRank(anyInt(), anyInt());
	}

	@Test
	public void testGetTestRanks() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);		
		when(statsRepository.getUserIDsTestRank(idLessonTest)).thenReturn(ids);
		
		UserTestRank userTest = mock(UserTestRank.class);
		when(userTest.getIdLessonTest()).thenReturn(idLessonTest);
		when(statsRepository.getUserTestRank(idLessonTest, idUser)).thenReturn(userTest);
		
		super.testGetTestRanks();
		verify(statsRepository, times(2)).getUserIDsTestRank(anyInt());
	}

	@Test
	public void testSaveTag() {
		Visit visit = new Visit(idVisit);		
		when(statsRepository.getVisit(eq(idVisit))).thenReturn(visit);

		when(statsRepository.saveTag(idVisit, idTag)).thenReturn(true);
		when(statsRepository.getTagViewsCount(idTag)).thenReturn(0,0,1);
		super.testSaveTag();
		verify(statsRepository).saveTag(anyInt(), anyInt());
	}

	@Test
	public void testGetTagViewsCount() {
		when(statsRepository.getTagViewsCount(idTag)).thenReturn(count);				
		super.testGetTagViewsCount();
		verify(statsRepository).getTagViewsCount(anyInt());
	}

	@Test
	public void testGetLessonViewsCount() {
		when(statsRepository.getLessonViewsCount(idLesson)).thenReturn(count);		
		super.testGetLessonViewsCount();
		verify(statsRepository).getLessonViewsCount(anyInt());
	}

	@Test
	public void testGetStatsLessonTest() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]", "1");
		when(statsRepository.getStatsLessonTestQuestionKOs(idLessonTest)).thenReturn(ids);
		when(statsRepository.getStatsLessonTestNumTests(idLessonTest)).thenReturn(count);
		super.testGetStatsLessonTest();
		
		verify(statsRepository).getStatsLessonTestQuestionKOs(anyInt());
		verify(statsRepository).getStatsLessonTestNumTests(anyInt());
	}

	@Test
	public void testGetLessonVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getLessonVisitsLastMonth(idLesson)).thenReturn(stats);
		super.testGetLessonVisitsLastMonth();
		verify(statsRepository).getLessonVisitsLastMonth(anyInt());
	}

	@Test
	public void testGetLessonVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getLessonVisitsLastYear(idLesson)).thenReturn(stats);
		super.testGetLessonVisitsLastYear();
		verify(statsRepository).getLessonVisitsLastYear(anyInt());
	}

	@Test
	public void testGetAuthorVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorVisitsLastMonth(idUser)).thenReturn(stats);
		super.testGetAuthorVisitsLastMonth();
		verify(statsRepository).getAuthorVisitsLastMonth(anyInt());
	}

	@Test
	public void testGetAuthorVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorVisitsLastYear(idUser)).thenReturn(stats);
		super.testGetAuthorVisitsLastYear();
		verify(statsRepository).getAuthorVisitsLastYear(anyInt());
	}

	@Test
	public void testGetAuthorLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorLessonsVisitsLastMonth(idUser)).thenReturn(stats);
		super.testGetAuthorLessonsVisitsLastMonth();
		verify(statsRepository).getAuthorLessonsVisitsLastMonth(anyInt());
	}

	@Test
	public void testGetAuthorLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorLessonsVisitsLastYear(idUser)).thenReturn(stats);
		super.testGetAuthorLessonsVisitsLastYear();
		verify(statsRepository).getAuthorLessonsVisitsLastYear(anyInt());
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorLessonMediaVisitsLastMonth(idUser)).thenReturn(stats);
		super.testGetAuthorLessonMediaVisitsLastMonth();
		verify(statsRepository).getAuthorLessonMediaVisitsLastMonth(anyInt());
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorLessonMediaVisitsLastYear(idUser)).thenReturn(stats);
		super.testGetAuthorLessonMediaVisitsLastYear();
		verify(statsRepository).getAuthorLessonMediaVisitsLastYear(anyInt());
	}

	@Test
	public void testGetVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getVisitsLastMonth()).thenReturn(stats);
		super.testGetVisitsLastMonth();
		verify(statsRepository).getVisitsLastMonth();
	}

	@Test
	public void testGetVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getVisitsLastYear()).thenReturn(stats);
		super.testGetVisitsLastYear();
		verify(statsRepository).getVisitsLastYear();
	}

	@Test
	public void testGetLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getLessonsVisitsLastMonth()).thenReturn(stats);
		super.testGetLessonsVisitsLastMonth();
		verify(statsRepository).getLessonsVisitsLastMonth();
	}

	@Test
	public void testGetLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getLessonsVisitsLastYear()).thenReturn(stats);
		super.testGetLessonsVisitsLastYear();
		verify(statsRepository).getLessonsVisitsLastYear();
	}

	@Test
	public void testGetAuthorsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorsVisitsLastMonth()).thenReturn(stats);
		super.testGetAuthorsVisitsLastMonth();
		verify(statsRepository).getAuthorsVisitsLastMonth();
	}

	@Test
	public void testGetAuthorsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(statsRepository.getAuthorsVisitsLastYear()).thenReturn(stats);
		super.testGetAuthorsVisitsLastYear();
		verify(statsRepository).getAuthorsVisitsLastYear();
	}

	@Test
	public void testGetVisit() {
		Visit visit = mock(Visit.class);
		when(visit.getId()).thenReturn(idVisit);		
		when(statsRepository.getVisit(idVisit)).thenReturn(visit);
		super.testGetVisit();
		verify(statsRepository).getVisit(anyInt());
	}
}
