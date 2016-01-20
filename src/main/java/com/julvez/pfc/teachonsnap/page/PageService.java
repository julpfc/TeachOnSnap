package com.julvez.pfc.teachonsnap.page;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) 
 */
public interface PageService {

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's page
	 * @return Page stack from the lesson page
	 */
	public List<Page> getLessonPageStack(Lesson lesson);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's edit page
	 * @return Page stack from the lesson edit page
	 */
	public List<Page> getEditLessonPageStack(Lesson lesson);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's new test page
	 * @return Page stack from the new lesson test page
	 */
	public List<Page> getNewLessonTestPageStack(Lesson lesson);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test edit page
	 * @param test 's edit page
	 * @return Page stack from the lesson test edit page
	 */
	public List<Page> getEditLessonTestPageStack(Lesson lesson, LessonTest test);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test edit question page
	 * @param test 's edit question page
	 * @param question edit page
	 * @return Page stack from the lesson test question edit page
	 */
	public List<Page> getEditQuestionPageStack(Lesson lesson, LessonTest test, Question question);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test new question page
	 * @param test 's new question page
	 * @return Page stack from the lesson new question page
	 */
	public List<Page> getNewQuestionPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @return Page stack from the Adminsitrator User manager page
	 */
	public List<Page> getAdminUsersPageStack();
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param searchQuery query searched
	 * @param searchType Type of search by (email, name, ...)
	 * @return Page stack from the Administrator user manager search result page
	 */
	public List<Page> getAdminUsersSearchPageStack(String searchQuery, String searchType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's page
	 * @return Page stack from the Administrator user profile page
	 */
	public List<Page> getAdminUserProfilePageStack(User profile);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param searchQuery query searched
	 * @return Page stack from the Administrator group manager search result page
	 */
	public List<Page> getAdminGroupsSearchPageStack(String searchQuery);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @return Page stack from the Administrator group manager page
	 */
	public List<Page> getAdminGroupsPageStack();

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's page
	 * @return Page stack from the Administrator group profile page
	 */
	public List<Page> getAdminGroupProfilePageStack(UserGroup profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's followings page
	 * @return Page stack from the Administrator group followings page
	 */
	public List<Page> getAdminGroupFollowPageStack(UserGroup profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's author followings page
	 * @return Page stack from the Administrator group author followings page
	 */
	public List<Page> getAdminGroupFollowAuthorPageStack(UserGroup profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's author followings page
	 * @param searchQuery query searched
	 * @param searchType Type of search by (email, name, ...)
	 * @return Page stack from the Administrator group followings search result page
	 */
	public List<Page> getAdminGroupFollowAuthorSearchPageStack(UserGroup profile, String searchQuery, String searchType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's tag followings page
	 * @return Page stack from the Administrator group tag followings page
	 */
	public List<Page> getAdminGroupFollowTagPageStack(UserGroup profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's tag followings page
	 * @param searchQuery query searched
	 * @return Page stack from the Administrator group tag followings search result page
	 */
	public List<Page> getAdminGroupFollowTagSearchPageStack(UserGroup profile, String searchQuery);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's followings page
	 * @return Page stack from the user followings page
	 */
	public List<Page> getUserFollowPageStack(User profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's author followings page
	 * @return Page stack from the user author followings page
	 */
	public List<Page> getUserFollowAuthorPageStack(User profile);
	
	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's author followings page
	 * @param searchQuery query searched
	 * @param searchType Type of search by (email, name, ...)
	 * @return Page stack from the user author followings search result page
	 */
	public List<Page> getUserFollowAuthorSearchPageStack(User profile, String searchQuery, String searchType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test page
	 * @param test 's page
	 * @return Page stack from the lesson test page
	 */
	public List<Page> getLessonTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson
	 * @param test 's stats page
	 * @return Page stack from the lesson test stats page
	 */
	public List<Page> getStatsTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's stats page
	 * @param statsType Last month or last year
	 * @return Page stack from the lesson stats page
	 */
	public List<Page> getStatsLessonPageStack(Lesson lesson, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's stats page
	 * @param statsType Last month or last year
	 * @return Page stack from the author stats page
	 */
	public List<Page> getStatsAuthorPageStack(User profile, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's stats page
	 * @param statsType Last month or last year
	 * @return Page stack from the author's lesson stats page
	 */
	public List<Page> getStatsAuthorLessonPageStack(Lesson lesson, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test stats page
	 * @param test 's stats page
	 * @return Page stack from the author's lesson test stats page
	 */
	public List<Page> getStatsAuthorLessonTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test stats page
	 * @param test 's stats page
	 * @return Page stack from the lesson test stats page
	 */
	public List<Page> getStatsLessonTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param statsType Last month or last year
	 * @return Page stack from the Administrator global stats page
	 */
	public List<Page> getAdminStatsPageStack(StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test stats page
	 * @param test 's stats page
	 * @return Page stack from the Administrator author's lesson test stats page
	 */
	public List<Page> getStatsAdminAuthorLessonTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's test stats page
	 * @param test 's stats page
	 * @return Page stack from the Administrator lesson test stats page
	 */
	public List<Page> getStatsAdminLessonTestPageStack(Lesson lesson, LessonTest test);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's stats page
	 * @param statsType Last month or year
	 * @return Page stack from the Administrator author stats page
	 */
	public List<Page> getStatsAdminAuthorPageStack(User profile, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's stats page
	 * @param statsType Last month or year
	 * @return Page stack from the Administrator author's lesson stats page
	 */
	public List<Page> getStatsAdminAuthorLessonPageStack(Lesson lesson, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param lesson 's stats page
	 * @param statsType Last month or year
	 * @return Page stack from the Adminsitrator lesson stats page
	 */
	public List<Page> getStatsAdminLessonPageStack(Lesson lesson, StatsType statsType);

	/**
	 * Returns page stack from current page (list of pages user browsed to reach current page)
	 * @param profile 's broadcast page
	 * @return Page stack from the Adminsitrator group broadcast page
	 */
	public List<Page> getAdminBroadcastGroupPageStack(UserGroup profile);
}
