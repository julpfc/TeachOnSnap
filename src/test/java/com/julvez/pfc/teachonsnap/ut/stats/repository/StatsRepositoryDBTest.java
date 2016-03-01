package com.julvez.pfc.teachonsnap.ut.stats.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsPropertyName;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryDB;

public class StatsRepositoryDBTest extends StatsRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected StatsRepository getRepository() {		
		return new StatsRepositoryDB(dbm, properties);
	}
	
	@Test
	public void testGetVisit(){
		Visit visit = new Visit(idVisit);	
		
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_VISIT"), eq(Visit.class), eq(invalidIdVisit))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_VISIT"), eq(Visit.class), eq(idVisit))).thenReturn(visit);
		
		super.testGetVisit();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_STATS_GET_VISIT"), eq(Visit.class), anyInt());
	}	
	
	@Test
	public void testCreateVisit() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_STATS_CREATE"), anyString())).thenReturn((long)invalidIdVisit);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_STATS_CREATE"), eq(ip))).thenReturn((long)idVisit);
		super.testCreateVisit();
		verify(dbm, times(4)).insertQueryAndGetLastInserID(eq("SQL_STATS_CREATE"), anyString());
	}

	@Test
	public void testSaveUser() {
		Visit visit = mock(Visit.class);
		when(visit.getIdUser()).thenReturn(invalidIdUser, invalidIdUser, idUser);
		
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_VISIT"), eq(Visit.class), eq(idVisit)))
				.thenReturn(visit);		
		when(dbm.updateQuery(eq("SQL_STATS_SAVE_USER"), anyInt(), anyInt())).thenReturn(-1, 1);
		
		super.testSaveUser();
		verify(dbm, times(2)).updateQuery(eq("SQL_STATS_SAVE_USER"), anyInt(), anyInt());
	}

	@Test
	public void testSaveLesson() {
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_LESSON_VIEWS_COUNT"), eq(BigDecimal.class), eq(idLesson))).thenReturn(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0.01));		
		when(dbm.updateQuery(eq("SQL_STATS_SAVE_LESSON"), anyInt(), anyInt())).thenReturn(-1, 1);
		
		super.testSaveLesson();
		verify(dbm, times(2)).updateQuery(eq("SQL_STATS_SAVE_LESSON"), anyInt(), anyInt());
	}

	@Test
	public void testGetLessonViewsCount() {
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_LESSON_VIEWS_COUNT"), eq(BigDecimal.class), eq(idLesson))).thenReturn(BigDecimal.valueOf(count/100));
		super.testGetLessonViewsCount();
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_STATS_GET_LESSON_VIEWS_COUNT"), eq(BigDecimal.class), anyInt());
	}

	@Test
	public void testSaveUserTest() {
		UserTestRank rank = mock(UserTestRank.class);
		when(rank.getPoints()).thenReturn(count, count, 2*count);
		
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_USERTESTRANK"), eq(UserTestRank.class), eq(idLessonTest), eq(idUser))).thenReturn(null, null, rank, rank, rank);		

		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_STATS_SAVE_USERTEST"), anyInt(), anyInt(), anyInt()))
			.thenReturn((long)1, (long)1, (long)1);
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_STATS_SAVE_USERTESTRANK"), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt()))
			.thenReturn(1);
		
		when(dbm.getQueryResultUnique_NoCommit(anyObject(), eq("SQL_STATS_GET_USERTEST_COUNT"), eq(BigInteger.class), anyInt(), anyInt())).thenReturn(BigInteger.valueOf(1));
		
		super.testSaveUserTest();
		verify(dbm, times(3)).insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_STATS_SAVE_USERTEST"), anyInt(), anyInt(), anyInt());
		verify(dbm, times(2)).updateQuery_NoCommit(anyObject(), eq("SQL_STATS_SAVE_USERTESTRANK"), anyInt(), anyInt(), anyInt(),anyInt(), anyInt(), anyInt());
	}

	@Test
	public void testGetUserTestRank() {
		UserTestRank rank = new UserTestRank();	
		
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_USERTESTRANK"), eq(UserTestRank.class), anyInt(), anyInt())).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_USERTESTRANK"), eq(UserTestRank.class), eq(idLessonTest), eq(idUser))).thenReturn(rank);
		
		super.testGetUserTestRank();
		
		verify(dbm, times(3)).getQueryResultUnique(eq("SQL_STATS_GET_USERTESTRANK"), eq(UserTestRank.class), anyInt(), anyInt());
	}

	@Test
	public void testGetUserIDsTestRank() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_USERIDS_TESTRANK"), eq(Short.class), eq(invalidIdLessonTest), eq((long)2))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_USERIDS_TESTRANK"), eq(Short.class), eq(idLessonTest), eq((long)2))).thenReturn(ids);
		when(properties.getNumericProperty(StatsPropertyName.LIMIT_USER_RANKING)).thenReturn((long)2);

		super.testGetUserIDsTestRank();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_USERIDS_TESTRANK"), eq(Short.class),anyInt(),(long)anyInt());
		verify(properties, times(2)).getNumericProperty(StatsPropertyName.LIMIT_USER_RANKING);
	}

	@Test
	public void testSaveTag() {
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_TAG_VIEWS_COUNT"), eq(BigDecimal.class), eq(idTag))).thenReturn(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0.01));		
		when(dbm.updateQuery(eq("SQL_STATS_SAVE_TAG"), anyInt(), anyInt())).thenReturn(-1, 1);
		
		super.testSaveTag();
		verify(dbm, times(2)).updateQuery(eq("SQL_STATS_SAVE_TAG"), anyInt(), anyInt());
	}

	@Test
	public void testGetTagViewsCount() {
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_TAG_VIEWS_COUNT"), eq(BigDecimal.class), eq(idTag))).thenReturn(BigDecimal.valueOf(count/100));
		super.testGetTagViewsCount();
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_STATS_GET_TAG_VIEWS_COUNT"), eq(BigDecimal.class), anyInt());
	}

	@Test
	public void testGetStatsLessonTestNumTests() {
		when(dbm.getQueryResultUnique(eq("SQL_STATS_GET_STATS_LESSONTEST_NUM_VISIT_TESTS"), eq(BigInteger.class), eq(idLessonTest))).thenReturn(BigInteger.valueOf(count));
		super.testGetStatsLessonTestNumTests();
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_STATS_GET_STATS_LESSONTEST_NUM_VISIT_TESTS"), eq(BigInteger.class), anyInt());
	}

	@Test
	public void testGetStatsLessonTestQuestionKOs() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,1});
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_STATS_LESSONTEST_QUESTION_KOS"), eq(Object[].class), eq(invalidIdLessonTest))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_STATS_LESSONTEST_QUESTION_KOS"), eq(Object[].class), eq(idLessonTest))).thenReturn(ids);

		super.testGetStatsLessonTestQuestionKOs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_STATS_LESSONTEST_QUESTION_KOS"), eq(Object[].class),anyInt());
	}

	@Test
	public void testGetLessonVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(idLesson))).thenReturn(stats);
		
		super.testGetLessonVisitsLastMonth();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetLessonVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(idLesson))).thenReturn(stats);
		
		super.testGetLessonVisitsLastYear();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_LESSON_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetAuthorVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(idUser))).thenReturn(stats);
		
		super.testGetAuthorVisitsLastMonth();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetAuthorVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(idUser))).thenReturn(stats);
		
		super.testGetAuthorVisitsLastYear();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetAuthorLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(invalidIdUser), eq((long)2))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(idUser), eq((long)2))).thenReturn(stats);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetAuthorLessonsVisitsLastMonth();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class),anyInt(), anyInt());
		verify(properties, times(2)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetAuthorLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(invalidIdUser), eq((long)2))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(idUser), eq((long)2))).thenReturn(stats);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetAuthorLessonsVisitsLastYear();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class),anyInt(), anyInt());
		verify(properties, times(2)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq(idUser))).thenReturn(stats);
		
		super.testGetAuthorLessonMediaVisitsLastMonth();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq(idUser))).thenReturn(stats);
		
		super.testGetAuthorLessonMediaVisitsLastYear();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_STATS_GET_AUTHOR_LESSONMEDIA_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class),anyInt());
	}

	@Test
	public void testGetVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class))).thenReturn(stats);
		
		super.testGetVisitsLastMonth();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class));
	}

	@Test
	public void testGetVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class))).thenReturn(stats);
		
		super.testGetVisitsLastYear();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class));
	}

	@Test
	public void testGetLessonsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSONS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq((long)2))).thenReturn(stats);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetLessonsVisitsLastMonth();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_LESSONS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), anyInt());
		verify(properties).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetLessonsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_LESSONS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq((long)2))).thenReturn(stats);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetLessonsVisitsLastYear();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_LESSONS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), anyInt());
		verify(properties).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetAuthorsVisitsLastMonth() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHORS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), eq((long)2))).thenReturn(stats);
				
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetAuthorsVisitsLastMonth();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_AUTHORS_VISITS_LASTMONTH_STATSDATA"), eq(StatsData.class), anyInt());
		verify(properties).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetAuthorsVisitsLastYear() {
		StatsData stat = new StatsData();
		stat.setValue(BigInteger.valueOf(1));
		
		List<StatsData> stats = new ArrayList<StatsData>();
		stats.add(stat);
		
		when(dbm.getQueryResultList(eq("SQL_STATS_GET_AUTHORS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), eq((long)2))).thenReturn(stats);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);

		super.testGetAuthorsVisitsLastYear();
		
		verify(dbm).getQueryResultList(eq("SQL_STATS_GET_AUTHORS_VISITS_LASTYEAR_STATSDATA"), eq(StatsData.class), anyInt());
		verify(properties).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}
}
