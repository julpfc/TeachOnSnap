package com.julvez.pfc.teachonsnap.ut.stats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.UserLessonTest;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.model.StatsData;
import com.julvez.pfc.teachonsnap.stats.model.StatsLessonTest;
import com.julvez.pfc.teachonsnap.stats.model.UserTestRank;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class StatsServiceTest extends ServiceTest<StatsService> {

	protected int idVisit = 1;
	protected int invalidIdVisit = -1;
	
	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected String ip = "127.0.0.1";
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int count = 1;
	
	protected int idLessonTest = 1;
	protected int invalidIdLessonTest = -1;

	protected int idTag = 1;
	protected int invalidIdTag = -1;
	
	@Test
	public void testCreateVisit() {
		assertEquals(idVisit*2, test.createVisit(ip).getId());
		
		assertNotNull(test.createVisit(NULL_STRING));
		assertNotNull(test.createVisit(EMPTY_STRING));
		assertNotNull(test.createVisit(BLANK_STRING));
	}

	@Test
	public void testSaveUser() {
		Visit visit = test.getVisit(idVisit*2);
		assertNotNull(visit);
		assertEquals(invalidIdUser, visit.getIdUser());
		
		assertNull(test.saveUser(null, null));
		
		visit = test.getVisit(idVisit*2);
		assertNotNull(visit);
		assertEquals(invalidIdUser, visit.getIdUser());
		
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		assertNotNull(test.saveUser(visit, user));
		
		visit = test.getVisit(idVisit*2);
		assertNotNull(visit);
		assertSame(user, visit.getUser());
	}

	@Test
	public void testSaveLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		Visit visit = test.getVisit(idVisit);
		assertNotNull(visit);
		
		assertEquals(0, test.getLessonViewsCount(lesson));
		
		assertNull(test.saveLesson(null, null));
		
		assertEquals(0, test.getLessonViewsCount(lesson));
		
		assertNotNull(test.saveLesson(visit, lesson));
		
		assertEquals(1, test.getLessonViewsCount(lesson));
	}

	@Test
	public void testGetLessonViewsCount() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		assertEquals(0, test.getLessonViewsCount(lesson));
		
		assertEquals(-1, test.getLessonViewsCount(null));
	}

	@Test
	public void testSaveUserTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idLessonTest);
		when(lessonTest.getNumQuestions()).thenReturn((short)1);
		when(lessonTest.getNumAnswers()).thenReturn((short)1);
		
		Map<String, String[]> userAnswers = new HashMap<String, String[]>();		
		UserLessonTest userTest = new UserLessonTest(lessonTest, userAnswers);				

		Visit visit = new Visit(idVisit);
		User user = new User();
		user.setId(idUser);
		visit.setUser(user);
		
		UserTestRank rank = test.getUserTestRank(idLessonTest, idUser);
		assertNull(rank);
		
		assertFalse(test.saveUserTest(null, null));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNull(rank);
		
		assertTrue(test.saveUserTest(visit, userTest));
		
		rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
		assertEquals(0, rank.getPoints());
	}

	@Test
	public void testGetUserTestRank() {
		UserTestRank rank = test.getUserTestRank(idLessonTest, idUser);
		assertNotNull(rank);
				
		assertNull(test.getUserTestRank(invalidIdLessonTest, idUser));
		assertNull(test.getUserTestRank(idLessonTest, invalidIdUser));
	}

	@Test
	public void testGetTestRanks() {
		List<UserTestRank> testRanks = test.getTestRanks(invalidIdLessonTest);		
		assertNotNull(testRanks);
		assertEquals(0, testRanks.size());

		testRanks = test.getTestRanks(idLessonTest);		
		assertNotNull(testRanks);
		
		short i=1;
		for(UserTestRank b:testRanks){
			assertEquals(i++, b.getIdLessonTest());
		}
	}

	@Test
	public void testSaveTag() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);
		
		Visit visit = test.getVisit(idVisit);
		assertNotNull(visit);
		
		assertEquals(0, test.getTagViewsCount(tag));
		
		assertNull(test.saveTag(null, null));
		
		assertEquals(0, test.getTagViewsCount(tag));
		
		assertNotNull(test.saveTag(visit, tag));
		
		assertEquals(1, test.getTagViewsCount(tag));
	}

	@Test
	public void testGetTagViewsCount() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);
		
		assertEquals(0, test.getTagViewsCount(tag));
		
		assertEquals(-1, test.getTagViewsCount(null));
	}

	@Test
	public void testGetStatsLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idLessonTest);
		
		StatsLessonTest stat = test.getStatsLessonTest(lessonTest);
		assertNotNull(stat);
		assertEquals(count, stat.getNumTests());
			
		Map<String, String> ids = stat.getQuestionKOs();		
		assertNotNull(ids);
		assertEquals(String.valueOf(0), ids.get("[1]"));		
		
		assertNull(test.getStatsLessonTest(null));		
	}

	@Test
	public void testGetLessonVisitsLastMonth() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<StatsData> stats = test.getLessonVisitsLastMonth(null);		
		assertNull(stats);

		stats = test.getLessonVisitsLastMonth(lesson);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetLessonVisitsLastYear() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		List<StatsData> stats = test.getLessonVisitsLastYear(null);		
		assertNull(stats);

		stats = test.getLessonVisitsLastYear(lesson);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetAuthorVisitsLastMonth() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorVisitsLastMonth(null);		
		assertNull(stats);

		stats = test.getAuthorVisitsLastMonth(user);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetAuthorVisitsLastYear() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorVisitsLastYear(null);		
		assertNull(stats);

		stats = test.getAuthorVisitsLastYear(user);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonsVisitsLastMonth() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorLessonsVisitsLastMonth(null);		
		assertNull(stats);

		stats = test.getAuthorLessonsVisitsLastMonth(user);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}
	
	@Test
	public void testGetAuthorLessonsVisitsLastYear() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorLessonsVisitsLastYear(null);		
		assertNull(stats);

		stats = test.getAuthorLessonsVisitsLastYear(user);		
		assertNotNull(stats);
		
		int i=1;
		for(StatsData b:stats){
			assertEquals(i++, b.getValue());
		}
	}

	@Test
	public void testGetCSVFromStats() {
		StatsData stat = mock(StatsData.class);
		when(stat.getKey()).thenReturn("1");
		when(stat.getValue()).thenReturn(2);

		List<StatsData> stats = new ArrayList<StatsData>();
		
		assertNull(test.getCSVFromStats(null));
		assertEquals(EMPTY_STRING, test.getCSVFromStats(stats));
		
		stats.add(stat);
		assertEquals("1;2\n", test.getCSVFromStats(stats));		
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastMonth() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorLessonMediaVisitsLastMonth(null);		
		assertNull(stats);

		stats = test.getAuthorLessonMediaVisitsLastMonth(user);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetAuthorLessonMediaVisitsLastYear() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<StatsData> stats = test.getAuthorLessonMediaVisitsLastYear(null);		
		assertNull(stats);

		stats = test.getAuthorLessonMediaVisitsLastYear(user);		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}
	
	@Test
	public void testGetVisitsLastMonth() {
		List<StatsData> stats = test.getVisitsLastMonth();		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetVisitsLastYear() {
		List<StatsData> stats = test.getVisitsLastYear();		
		assertNotNull(stats);
		
		int i=0;
		for(StatsData b:stats){
			assertEquals(i, b.getValue());
		}
	}

	@Test
	public void testGetLessonsVisitsLastMonth() {
		List<StatsData> stats = test.getLessonsVisitsLastMonth();		
		assertNotNull(stats);
		
		int i=0;
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

	@Test
	public void testGetVisit(){
		Visit visit = test.getVisit(idVisit);
		assertNotNull(visit);
				
		assertNull(test.getVisit(invalidIdVisit));
	}	
}
