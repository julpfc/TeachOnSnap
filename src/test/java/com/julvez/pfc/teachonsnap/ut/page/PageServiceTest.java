package com.julvez.pfc.teachonsnap.ut.page;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.url.model.SearchType;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class PageServiceTest extends ServiceTest<PageService> {

	protected String URL = "url";
	protected String URL2 = "url";
	protected String URL3 = "url";
	protected String URL4 = "url";
	protected String URL5 = "url";
	protected String URL6 = "url";
	protected String extraName = "extraname";
	protected String query = "search";
	
	@Test
	public void testGetLessonPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getTitle()).thenReturn(extraName);
				
		List<Page> pages = test.getLessonPageStack(lesson);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(extraName, pages.get(0).getExtraName());

		assertNull(test.getLessonPageStack(null));
	}

	@Test
	public void testGetEditLessonPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
				
		List<Page> pages = test.getEditLessonPageStack(lesson);
		assertNotNull(pages);		
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
		
		assertNull(test.getEditLessonPageStack(null));
	}

	@Test
	public void testGetNewLessonTestPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
		when(lesson.getNewTestURL()).thenReturn(URL);
				
		List<Page> pages = test.getNewLessonTestPageStack(lesson);
		assertNotNull(pages);		
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
		assertEquals(URL3, pages.get(0).getLink());
		assertNull(test.getNewLessonTestPageStack(null));
	}

	@Test
	public void testGetEditLessonTestPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getEditURL()).thenReturn(URL);
				
		List<Page> pages = test.getEditLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);		
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
		assertEquals(URL3, pages.get(0).getLink());
		assertNull(test.getEditLessonTestPageStack(null, null));
		assertNull(test.getEditLessonTestPageStack(null, lessonTest));
		assertNull(test.getEditLessonTestPageStack(lesson, null));
	}

	@Test
	public void testGetEditQuestionPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getEditURL()).thenReturn(URL);
		
		Question question = mock(Question.class);
		when(question.getEditURL()).thenReturn(URL);
				
		List<Page> pages = test.getEditQuestionPageStack(lesson, lessonTest, question);
		assertNotNull(pages);		
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
		assertEquals(URL3, pages.get(0).getLink());
		assertEquals(URL4, pages.get(0).getLink());
		assertNull(test.getEditQuestionPageStack(null, null, null));
		assertNull(test.getEditQuestionPageStack(null, lessonTest, null));
		assertNull(test.getEditQuestionPageStack(lesson, null, null));
		assertNull(test.getEditQuestionPageStack(null, null, question));
		assertNull(test.getEditQuestionPageStack(null, lessonTest, question));
		assertNull(test.getEditQuestionPageStack(lesson, null, question));
	}

	@Test
	public void testGetNewQuestionPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getEditURL()).thenReturn(URL);
		when(lessonTest.getNewQuestionURL()).thenReturn(URL);
				
		List<Page> pages = test.getNewQuestionPageStack(lesson, lessonTest);
		assertNotNull(pages);		
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
		assertEquals(URL3, pages.get(0).getLink());
		assertEquals(URL4, pages.get(0).getLink());
		assertNull(test.getNewQuestionPageStack(null, null));
		assertNull(test.getNewQuestionPageStack(null, lessonTest));
		assertNull(test.getNewQuestionPageStack(lesson, null));
	}

	@Test
	public void testGetAdminUsersPageStack() {
		List<Page> pages = test.getAdminUsersPageStack();
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
	}

	@Test
	public void testGetAdminUsersSearchPageStack() {
		List<Page> pages = test.getAdminUsersSearchPageStack(query, SearchType.EMAIL.toString());
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());
		assertEquals(URL2, pages.get(1).getLink());
		
		pages = test.getAdminUsersSearchPageStack(query, SearchType.NAME.toString());
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL3, pages.get(1).getLink());		
				
		assertNull(test.getAdminUsersSearchPageStack(null, null));
		assertNull(test.getAdminUsersSearchPageStack(query, null));
		assertNull(test.getAdminUsersSearchPageStack(null, SearchType.EMAIL.toString()));
	}

	@Test
	public void testGetAdminUserProfilePageStack() {
		User profile = mock(User.class);
		
		List<Page> pages = test.getAdminUserProfilePageStack(profile);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(0).getLink());		
						
		assertNull(test.getAdminUserProfilePageStack(null));		
	}

	@Test
	public void testGetAdminGroupsSearchPageStack() {
		List<Page> pages = test.getAdminGroupsSearchPageStack(query);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
				
		assertNull(test.getAdminGroupsSearchPageStack(null));	
	}

	@Test
	public void testGetAdminGroupsPageStack() {
		List<Page> pages = test.getAdminGroupsPageStack();
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());
	}

	@Test
	public void testGetAdminGroupProfilePageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupProfilePageStack(group);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
				
		assertNull(test.getAdminGroupProfilePageStack(null));		
	}

	@Test
	public void testGetAdminGroupFollowPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupFollowPageStack(group);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		
		assertNull(test.getAdminGroupFollowPageStack(null));
	}

	@Test
	public void testGetAdminGroupFollowAuthorPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupFollowAuthorPageStack(group);
		assertNotNull(pages);
		assertEquals(4, pages.size());
		
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		
		assertNull(test.getAdminGroupFollowAuthorPageStack(null));
	}

	@Test
	public void testGetAdminGroupFollowAuthorSearchPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupFollowAuthorSearchPageStack(group, query, SearchType.EMAIL.toString());
		assertNotNull(pages);
		assertEquals(5, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		assertEquals(URL5, pages.get(4).getLink());
		
		pages = test.getAdminGroupFollowAuthorSearchPageStack(group, query, SearchType.NAME.toString());
		assertNotNull(pages);
		assertEquals(5, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		assertEquals(URL6, pages.get(4).getLink());
				
		assertNull(test.getAdminGroupFollowAuthorSearchPageStack(null, null, null));
		assertNull(test.getAdminGroupFollowAuthorSearchPageStack(null, query, null));
		assertNull(test.getAdminGroupFollowAuthorSearchPageStack(null, null, SearchType.EMAIL.toString()));
		assertNull(test.getAdminGroupFollowAuthorSearchPageStack(null, query, SearchType.EMAIL.toString()));

		pages = test.getAdminGroupFollowAuthorSearchPageStack(group, null, null);
		assertNotNull(pages);
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
	}

	@Test
	public void testGetAdminGroupFollowTagPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupFollowTagPageStack(group);
		assertNotNull(pages);
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		
		assertNull(test.getAdminGroupFollowTagPageStack(null));
	}

	@Test
	public void testGetAdminGroupFollowTagSearchPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminGroupFollowTagSearchPageStack(group, query);
		assertNotNull(pages);
		assertEquals(5, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());	 	
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		assertEquals(URL5, pages.get(4).getLink());
		
		assertNull(test.getAdminGroupFollowTagSearchPageStack(null, null));
		assertNull(test.getAdminGroupFollowTagSearchPageStack(null, query));
		
		pages = test.getAdminGroupFollowTagSearchPageStack(group, null);
		assertNotNull(pages);
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
	}

	@Test
	public void testGetUserFollowPageStack() {
		User profile = mock(User.class);
				
		List<Page> pages = test.getUserFollowPageStack(profile);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());		

		assertNull(test.getUserFollowPageStack(null));
	}

	@Test
	public void testGetUserFollowAuthorPageStack() {
		User profile = mock(User.class);
		
		List<Page> pages = test.getUserFollowAuthorPageStack(profile);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());			

		assertNull(test.getUserFollowAuthorPageStack(null));
	}

	@Test
	public void testGetUserFollowAuthorSearchPageStack() {
		User profile = mock(User.class);
		
		List<Page> pages = test.getUserFollowAuthorSearchPageStack(profile, query, SearchType.EMAIL.toString());
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		
		pages = test.getUserFollowAuthorSearchPageStack(profile, query, SearchType.NAME.toString());
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL4, pages.get(2).getLink());
				
		assertNull(test.getUserFollowAuthorSearchPageStack(null, null, null));
		assertNull(test.getUserFollowAuthorSearchPageStack(null, query, null));
		assertNull(test.getUserFollowAuthorSearchPageStack(null, null, SearchType.EMAIL.toString()));
		assertNull(test.getUserFollowAuthorSearchPageStack(null, query, SearchType.EMAIL.toString()));

		pages = test.getUserFollowAuthorSearchPageStack(profile, null, null);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());
	}

	@Test
	public void testGetLessonTestPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getURL()).thenReturn(URL);
				
		List<Page> pages = test.getLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);		
		assertEquals(2, pages.size());
		for(Page page:pages){
			assertEquals(URL, page.getLink());		
		}
		assertNull(test.getLessonTestPageStack(null, null));
		assertNull(test.getLessonTestPageStack(null, lessonTest));
		assertNull(test.getLessonTestPageStack(lesson, null));
	}

	@Test
	public void testGetStatsTestPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
		
		LessonTest lessonTest = mock(LessonTest.class);		
				
		List<Page> pages = test.getStatsTestPageStack(lesson, lessonTest);
		assertNotNull(pages);		
		assertEquals(3, pages.size());
		for(Page page:pages){
			assertEquals(URL, page.getLink());		
		}
		assertNull(test.getStatsTestPageStack(null, null));
		assertNull(test.getStatsTestPageStack(null, lessonTest));
		assertNull(test.getStatsTestPageStack(lesson, null));
	}

	@Test
	public void testGetStatsLessonPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL);
				
		List<Page> pages = test.getStatsLessonPageStack(lesson, StatsType.MONTH);
		assertNotNull(pages);		
		assertEquals(3, pages.size());
		for(Page page:pages){
			assertEquals(URL, page.getLink());		
		}	
		
		pages = test.getStatsLessonPageStack(lesson, StatsType.YEAR);
		assertNotNull(pages);		
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL, pages.get(1).getLink());		
		assertEquals(URL2, pages.get(2).getLink());
				
		assertNull(test.getStatsLessonPageStack(null, null));
		assertNull(test.getStatsLessonPageStack(lesson, null));
		assertNull(test.getStatsLessonPageStack(null, StatsType.MONTH));
	}

	@Test
	public void testGetStatsAuthorPageStack() {
		User profile = mock(User.class);
		when(profile.getFullName()).thenReturn(extraName);
		
		List<Page> pages = test.getStatsAuthorPageStack(profile, StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());	
		assertEquals(extraName, pages.get(0).getExtraName());
		
		pages = test.getStatsAuthorPageStack(profile, StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL2, pages.get(0).getLink());
		assertEquals(extraName, pages.get(0).getExtraName());

		assertNull(test.getStatsAuthorPageStack(null, null));
		assertNull(test.getStatsAuthorPageStack(profile, null));
		assertNull(test.getStatsAuthorPageStack(null, StatsType.MONTH));
	}

	@Test
	public void testGetStatsAuthorLessonPageStack() {
		User profile = mock(User.class);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getAuthor()).thenReturn(profile);
		
		List<Page> pages = test.getStatsAuthorLessonPageStack(lesson, StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
				
		
		pages = test.getStatsAuthorLessonPageStack(lesson, StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL3, pages.get(0).getLink());		
		assertEquals(URL4, pages.get(1).getLink());

		assertNull(test.getStatsAuthorLessonPageStack(null, null));
		assertNull(test.getStatsAuthorLessonPageStack(lesson, null));
		assertNull(test.getStatsAuthorLessonPageStack(null, StatsType.MONTH));
	}

	@Test
	public void testGetStatsAuthorLessonTestPageStack() {
		User profile = mock(User.class);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getAuthor()).thenReturn(profile);
		
		LessonTest lessonTest = mock(LessonTest.class);
		
		List<Page> pages = test.getStatsAuthorLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());	
		assertEquals(URL3, pages.get(2).getLink());	
		
		assertNull(test.getStatsAuthorLessonTestPageStack(null, null));
		assertNull(test.getStatsAuthorLessonTestPageStack(lesson, null));
		assertNull(test.getStatsAuthorLessonTestPageStack(null, lessonTest));
	}

	@Test
	public void testGetStatsLessonTestPageStack() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getURL()).thenReturn(URL);
		when(lesson.getEditURL()).thenReturn(URL2);
		
		LessonTest lessonTest = mock(LessonTest.class);
				
		List<Page> pages = test.getStatsLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);		
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());
		assertEquals(URL4, pages.get(3).getLink());
		
		assertNull(test.getStatsLessonTestPageStack(null, null));
		assertNull(test.getStatsLessonTestPageStack(lesson, null));
		assertNull(test.getStatsLessonTestPageStack(null, lessonTest));
	}

	@Test
	public void testGetAdminStatsPageStack() {		
		List<Page> pages = test.getAdminStatsPageStack(StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		
		pages = test.getAdminStatsPageStack(StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(1, pages.size());
		assertEquals(URL2, pages.get(0).getLink());		

		assertNull(test.getAdminStatsPageStack(null));		
	}

	@Test
	public void testGetStatsAdminAuthorLessonTestPageStack() {
		User profile = mock(User.class);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getAuthor()).thenReturn(profile);
		
		LessonTest lessonTest = mock(LessonTest.class);
		
		List<Page> pages = test.getStatsAdminAuthorLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);
		assertEquals(4, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());	
		assertEquals(URL4, pages.get(3).getLink());	
		
		assertNull(test.getStatsAdminAuthorLessonTestPageStack(null, null));
		assertNull(test.getStatsAdminAuthorLessonTestPageStack(lesson, null));
		assertNull(test.getStatsAdminAuthorLessonTestPageStack(null, lessonTest));
	}

	@Test
	public void testGetStatsAdminLessonTestPageStack() {
		Lesson lesson = mock(Lesson.class);		
		LessonTest lessonTest = mock(LessonTest.class);
		
		List<Page> pages = test.getStatsAdminLessonTestPageStack(lesson, lessonTest);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());	
		
		assertNull(test.getStatsAdminLessonTestPageStack(null, null));
		assertNull(test.getStatsAdminLessonTestPageStack(lesson, null));
		assertNull(test.getStatsAdminLessonTestPageStack(null, lessonTest));	
	}

	@Test
	public void testGetStatsAdminAuthorPageStack() {
		User profile = mock(User.class);
		
		List<Page> pages = test.getStatsAdminAuthorPageStack(profile, StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
				
		pages = test.getStatsAdminAuthorPageStack(profile, StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL3, pages.get(0).getLink());		
		assertEquals(URL4, pages.get(1).getLink());		

		assertNull(test.getStatsAdminAuthorPageStack(null, null));
		assertNull(test.getStatsAdminAuthorPageStack(profile, null));
		assertNull(test.getStatsAdminAuthorPageStack(null, StatsType.MONTH));	
	}

	@Test
	public void testGetStatsAdminAuthorLessonPageStack() {
		User profile = mock(User.class);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getAuthor()).thenReturn(profile);
		
		List<Page> pages = test.getStatsAdminAuthorLessonPageStack(lesson, StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());		
		
		
		pages = test.getStatsAdminAuthorLessonPageStack(lesson, StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL4, pages.get(0).getLink());		
		assertEquals(URL5, pages.get(1).getLink());		
		assertEquals(URL6, pages.get(2).getLink());	

		assertNull(test.getStatsAdminAuthorLessonPageStack(null, null));
		assertNull(test.getStatsAdminAuthorLessonPageStack(lesson, null));
		assertNull(test.getStatsAdminAuthorLessonPageStack(null, StatsType.MONTH));
	}

	@Test
	public void testGetStatsAdminLessonPageStack() {
		Lesson lesson = mock(Lesson.class);
				
		List<Page> pages = test.getStatsAdminLessonPageStack(lesson, StatsType.MONTH);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());
		
		pages = test.getStatsAdminLessonPageStack(lesson, StatsType.YEAR);
		assertNotNull(pages);
		assertEquals(2, pages.size());
		assertEquals(URL3, pages.get(0).getLink());		
		assertEquals(URL4, pages.get(1).getLink());		
		
		assertNull(test.getStatsAdminLessonPageStack(null, null));
		assertNull(test.getStatsAdminLessonPageStack(lesson, null));
		assertNull(test.getStatsAdminLessonPageStack(null, StatsType.MONTH));	
	}

	@Test
	public void testGetAdminBroadcastGroupPageStack() {
		UserGroup group = mock(UserGroup.class);
		
		List<Page> pages = test.getAdminBroadcastGroupPageStack(group);
		assertNotNull(pages);
		assertEquals(3, pages.size());
		assertEquals(URL, pages.get(0).getLink());		
		assertEquals(URL2, pages.get(1).getLink());		
		assertEquals(URL3, pages.get(2).getLink());		
				
		assertNull(test.getAdminBroadcastGroupPageStack(null));	
	}
}
