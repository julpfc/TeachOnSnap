package com.julvez.pfc.teachonsnap.ut.stats.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.UserQuestion;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class StatsRepositoryTest extends RepositoryTest<StatsRepository> {

	protected int idVisit = 1;
	protected int invalidIdVisit = -1;
	
	protected String ip = "127.0.0.1";
	
	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idLessonTest = 1;
	protected int invalidIdLessonTest = -1;
	
	protected int count = 100;

	protected int idTag = 1;
	protected int invalidIdTag = -1;
	

	@Test
	public void testGetVisit(){
		Visit visit = test.getVisit(idVisit);
		assertNotNull(visit);
				
		assertNull(test.getVisit(invalidIdVisit));
	}	
	
	@Test
	public void testCreateVisit() {
		assertEquals(idVisit, test.createVisit(ip));
		
		assertEquals(invalidIdVisit, test.createVisit(NULL_STRING));
		assertEquals(invalidIdVisit, test.createVisit(EMPTY_STRING));
		assertEquals(invalidIdVisit, test.createVisit(BLANK_STRING));
	}

	@Test
	public void testSaveUser() {
		Visit visit = test.getVisit(idVisit);
		assertNotNull(visit);
		assertEquals(invalidIdUser, visit.getIdUser());
		
		assertFalse(test.saveUser(invalidIdVisit, invalidIdUser));
		
		visit = test.getVisit(idVisit);
		assertNotNull(visit);
		assertEquals(invalidIdUser, visit.getIdUser());
		
		assertTrue(test.saveUser(idVisit, idUser));
		
		visit = test.getVisit(idVisit);
		assertNotNull(visit);
		assertEquals(idUser, visit.getIdUser());
	}

	@Test
	public void testSaveLesson() {
		assertEquals(0, test.getLessonViewsCount(idLesson));
		
		assertFalse(test.saveLesson(invalidIdVisit, invalidIdLesson));
		
		assertEquals(0, test.getLessonViewsCount(idLesson));
		
		assertTrue(test.saveLesson(idVisit, idLesson));
		
		assertEquals(1, test.getLessonViewsCount(idLesson));
	}

	@Test
	public void testGetLessonViewsCount() {
		assertEquals(count, test.getLessonViewsCount(idLesson));
		
		assertEquals(0, test.getLessonViewsCount(invalidIdLesson));
	}

	@Test
	public void testSaveUserTest() {
		UserLessonTest userTest = mock(UserLessonTest.class);
		when(userTest.getPoints()).thenReturn(count);
		when(userTest.getQuestions()).thenReturn(new ArrayList<UserQuestion>());

		UserLessonTest userTestBest = mock(UserLessonTest.class);
		when(userTestBest.getPoints()).thenReturn(2*count);
		when(userTestBest.getQuestions()).thenReturn(new ArrayList<UserQuestion>());
		
		Visit visit = new Visit(idVisit);
		visit.setUser(new User());
		
		UserTestRank rank = test.getUserTestRank(idLessonTest, idUser);
		assertNull(rank);
		
		assertFalse(test.saveUserTest(null, null, false));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNull(rank);
		
		assertTrue(test.saveUserTest(visit, userTest, true));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
		assertEquals(userTest.getPoints(), rank.getPoints());
		
		assertTrue(test.saveUserTest(visit, userTestBest, false));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
		assertNotEquals(userTestBest.getPoints(), rank.getPoints());
		
		assertTrue(test.saveUserTest(visit, userTestBest, true));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
		assertEquals(userTestBest.getPoints(), rank.getPoints());
	}

	@Test
	public void testGetUserTestRank() {
		UserTestRank rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
				
		assertNull(test.getUserTestRank(invalidIdLessonTest, idUser));
		assertNull(test.getUserTestRank(idLessonTest, invalidIdUser));
	}

	@Test
	public void testGetUserIDsTestRank() {
		List<Short> ids = test.getUserIDsTestRank(invalidIdLessonTest);		
		assertNull(ids);

		ids = test.getUserIDsTestRank(idLessonTest);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testSaveTag() {
		assertEquals(0, test.getTagViewsCount(idTag));
		
		assertFalse(test.saveTag(invalidIdVisit, invalidIdTag));
		
		assertEquals(0, test.getTagViewsCount(idTag));
		
		assertTrue(test.saveTag(idVisit, idTag));
		
		assertEquals(1, test.getTagViewsCount(idTag));
	}

	@Test
	public void testGetTagViewsCount() {
		assertEquals(count, test.getTagViewsCount(idTag));
		
		assertEquals(0, test.getTagViewsCount(invalidIdTag));
	}

	@Test
	public void testGetStatsLessonTestNumTests() {
		assertEquals(count, test.getStatsLessonTestNumTests(idLessonTest));
		
		assertEquals(0, test.getStatsLessonTestNumTests(invalidIdLessonTest));
	}

	@Test
	public void testGetStatsLessonTestQuestionKOs() {
		Map<String, String> ids = test.getStatsLessonTestQuestionKOs(invalidIdLessonTest);		
		assertNotNull(ids);
		assertEquals(0, ids.size());

		ids = test.getStatsLessonTestQuestionKOs(idLessonTest);
		assertNotNull(ids);
		
		int i=1;
		for(String b:ids.keySet()){
			assertEquals("[" + String.valueOf(i) + "]", b);
			assertEquals(String.valueOf(i), ids.get(b));
			i++;
		}
	}

	@Test
	public void testGetLessonVisitsLastMonth() {
		List<StatsData> stats = test.getLessonVisitsLastMonth(invalidIdLesson);		
		assertNull(stats);

		stats = test.getLessonVisitsLastMonth(idLesson);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetLessonVisitsLastYear() {
		List<StatsData> stats = test.getLessonVisitsLastYear(invalidIdLesson);		
		assertNull(stats);

		stats = test.getLessonVisitsLastYear(idLesson);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorVisitsLastMonth() {
		List<StatsData> stats = test.getAuthorVisitsLastMonth(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorVisitsLastMonth(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorVisitsLastYear() {
		List<StatsData> stats = test.getAuthorVisitsLastYear(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorVisitsLastYear(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonsVisitsLastMonth() {
		List<StatsData> stats = test.getAuthorLessonsVisitsLastMonth(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorLessonsVisitsLastMonth(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonsVisitsLastYear() {
		List<StatsData> stats = test.getAuthorLessonsVisitsLastYear(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorLessonsVisitsLastYear(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastMonth() {
		List<StatsData> stats = test.getAuthorLessonMediaVisitsLastMonth(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorLessonMediaVisitsLastMonth(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastYear() {
		List<StatsData> stats = test.getAuthorLessonMediaVisitsLastYear(invalidIdUser);		
		assertNull(stats);

		stats = test.getAuthorLessonMediaVisitsLastYear(idUser);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetVisitsLastMonth() {
		List<StatsData> stats = test.getVisitsLastMonth();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetVisitsLastYear() {
		List<StatsData> stats = test.getVisitsLastYear();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetLessonsVisitsLastMonth() {
		List<StatsData> stats = test.getLessonsVisitsLastMonth();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetLessonsVisitsLastYear() {
		List<StatsData> stats = test.getLessonsVisitsLastYear();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorsVisitsLastMonth() {
		List<StatsData> stats = test.getAuthorsVisitsLastMonth();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetAuthorsVisitsLastYear() {
		List<StatsData> stats = test.getAuthorsVisitsLastYear();		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}
}
