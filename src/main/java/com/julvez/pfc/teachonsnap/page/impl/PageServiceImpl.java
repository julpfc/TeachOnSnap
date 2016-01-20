package com.julvez.pfc.teachonsnap.page.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.page.model.PageNameKey;
import com.julvez.pfc.teachonsnap.stats.model.StatsType;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * Implementation of the PageService interface, uses an internal {@link URLService} 
 * to work with app's URLs.
 */
public class PageServiceImpl implements PageService {
	
	/** Provides the functionality to work with application's URLs. */
	private URLService urlService;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param urlService Provides the functionality to work with application's URLs.
	 */
	public PageServiceImpl(URLService urlService) {
		if(urlService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.urlService = urlService;
	}

	@Override
	public List<Page> getLessonPageStack(Lesson lesson) {
		List<Page> pages = null;
		Page page = null;
		
		if(lesson!=null){
			pages = new ArrayList<Page>(); 
			page = new Page(PageNameKey.LESSON, lesson.getTitle(), lesson.getURL());
			pages.add(page);
		}		
		
		return pages;
	}

	@Override
	public List<Page> getEditLessonPageStack(Lesson lesson) {
		List<Page> pages = getLessonPageStack(lesson);
		
		if(pages != null){
			Page page = new Page(PageNameKey.EDIT_LESSON, lesson.getEditURL());
			pages.add(page);
		}
		return pages;
	}

	@Override
	public List<Page> getNewLessonTestPageStack(Lesson lesson) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null){
			Page page = new Page(PageNameKey.NEW_LESSON_TEST, lesson.getNewTestURL());
			pages.add(page);
		}
		return pages;
	}

	@Override
	public List<Page> getEditLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null && test!=null){
			Page page = new Page(PageNameKey.EDIT_LESSON_TEST, test.getEditURL());
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getEditQuestionPageStack(Lesson lesson, LessonTest test, Question question) {
		List<Page> pages = getEditLessonTestPageStack(lesson,test);
		
		if(pages != null && question!=null){
			Page page = new Page(PageNameKey.EDIT_QUESTION, question.getEditURL());
			pages.add(page);
		}
		else pages = null;
		return pages;

	}

	@Override
	public List<Page> getNewQuestionPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getEditLessonTestPageStack(lesson,test);
		
		if(pages != null && test!=null){
			Page page = new Page(PageNameKey.NEW_QUESTION, test.getNewQuestionURL());
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminUsersSearchPageStack(String searchQuery, String searchType) {
		List<Page> pages = getAdminUsersPageStack();
		
		if(pages != null && searchQuery!=null && searchType!=null){
			Page page = new Page("email".equalsIgnoreCase(searchType)?PageNameKey.ADMIN_USERS_SEARCH_BY_EMAIL:PageNameKey.ADMIN_USERS_SEARCH_BY_NAME, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_MANAGER + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminUsersPageStack() {
		List<Page> pages = new ArrayList<Page>();
		Page page = new Page(PageNameKey.ADMIN_USERS, urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_MANAGER.toString()));
		pages.add(page);
				
		return pages;
	}

	@Override
	public List<Page> getAdminUserProfilePageStack(User profile) {
		List<Page> pages = getAdminUsersPageStack();
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_USER_PROFILE, urlService.getAbsoluteURL(ControllerURI.ADMIN_USER_PROFILE.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupsSearchPageStack(String searchQuery) {
		List<Page> pages = getAdminGroupsPageStack();
		
		if(pages != null && searchQuery!=null){
			Page page = new Page(PageNameKey.ADMIN_GROUPS_SEARCH_BY_NAME,
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupsPageStack() {
		List<Page> pages = new ArrayList<Page>();
		Page page = new Page(PageNameKey.ADMIN_GROUPS, urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_MANAGER.toString()));
		pages.add(page);
				
		return pages;
	}

	@Override
	public List<Page> getAdminGroupProfilePageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupsPageStack();
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_PROFILE, profile.getGroupName(),
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowPageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupProfilePageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_FOLLOWS, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowAuthorPageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupFollowPageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_FOLLOW_AUTHOR, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_AUTHOR.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowAuthorSearchPageStack(UserGroup profile, String searchQuery, String searchType) {
		List<Page> pages = getAdminGroupFollowAuthorPageStack(profile);
		
		if(pages != null && searchQuery!=null && searchType!=null && profile != null){
			Page page = new Page("email".equalsIgnoreCase(searchType)?PageNameKey.ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_EMAIL:PageNameKey.ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_NAME, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_AUTHOR.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType));
			pages.add(page);
		}		
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowTagPageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupFollowPageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_FOLLOW_TAG, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowTagSearchPageStack(UserGroup profile, String searchQuery) {
		List<Page> pages = getAdminGroupFollowTagPageStack(profile);
		
		if(pages != null && searchQuery!=null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_FOLLOW_TAG_SEARCH, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery));
			pages.add(page);
		}		
		return pages;
	}

	@Override
	public List<Page> getUserFollowPageStack(User profile) {
		List<Page> pages = new ArrayList<Page>();
		
		if(profile != null){
			Page page = new Page(PageNameKey.USER_FOLLOWS, urlService.getAbsoluteURL(ControllerURI.USER_FOLLOWS.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getUserFollowAuthorPageStack(User profile) {
		List<Page> pages = getUserFollowPageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.USER_FOLLOW_AUTHOR, 
					urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getUserFollowAuthorSearchPageStack(User profile, String searchQuery, String searchType) {
		List<Page> pages = getUserFollowAuthorPageStack(profile);
		
		if(pages != null && searchQuery!=null && searchType!=null && profile != null){
			Page page = new Page("email".equalsIgnoreCase(searchType)?PageNameKey.USER_FOLLOW_AUTHOR_SEARCH_BY_EMAIL:PageNameKey.USER_FOLLOW_AUTHOR_SEARCH_BY_NAME, 
					urlService.getAbsoluteURL(ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType));
			pages.add(page);
		}		
		return pages;
	}

	@Override
	public List<Page> getLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getLessonPageStack(lesson);
		
		if(pages != null && test != null){
			Page page = new Page(PageNameKey.LESSON_TEST, test.getURL());
			pages.add(page);
		}
		return pages;
	}

	@Override
	public List<Page> getStatsTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null && test!=null){
			Page page = new Page(PageNameKey.STATS_LESSON_TEST, urlService.getAbsoluteURL(ControllerURI.STATS_TEST.toString() + test.getId()));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsLessonPageStack(Lesson lesson, StatsType statsType) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null && statsType!=null && lesson!=null){
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_LESSON_MONTH, urlService.getAbsoluteURL(ControllerURI.STATS_LESSON_MONTH.toString() + lesson.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_LESSON_YEAR, urlService.getAbsoluteURL(ControllerURI.STATS_LESSON_YEAR.toString() + lesson.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAuthorPageStack(User profile, StatsType statsType) {
		List<Page> pages = new ArrayList<Page>();
		
		if(pages != null && statsType!=null && profile!=null){
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_AUTHOR_MONTH, profile.getFullName(), urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_MONTH.toString() + profile.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_AUTHOR_YEAR, profile.getFullName(), urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_YEAR.toString() + profile.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAuthorLessonPageStack(Lesson lesson, StatsType statsType) {
		List<Page> pages = new ArrayList<Page>();
		
		if(pages != null && statsType!=null && lesson != null){
			pages = getStatsAuthorPageStack(lesson.getAuthor(),statsType);
			
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_LESSON_MONTH, lesson.getTitle(), urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_LESSON_MONTH.toString() + lesson.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_LESSON_YEAR, lesson.getTitle(), urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_LESSON_YEAR.toString() + lesson.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;		
	}

	@Override
	public List<Page> getStatsAuthorLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getStatsAuthorLessonPageStack(lesson, StatsType.MONTH);
		
		if(pages != null && test!=null && lesson!=null){
			Page page = new Page(PageNameKey.STATS_LESSON_TEST, urlService.getAbsoluteURL(ControllerURI.STATS_AUTHOR_LESSON_TEST.toString() + test.getId()));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getStatsLessonPageStack(lesson, StatsType.MONTH);
		
		if(pages != null && test!=null && lesson!=null){
			Page page = new Page(PageNameKey.STATS_LESSON_TEST, urlService.getAbsoluteURL(ControllerURI.STATS_LESSON_TEST.toString() + test.getId()));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminStatsPageStack(StatsType statsType) {
		List<Page> pages = new ArrayList<Page>();
		
		if(pages != null && statsType!=null){
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.ADMIN_STATS_MONTH, urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_MONTH.toString()));
			}
			else{
				page = new Page(PageNameKey.ADMIN_STATS_YEAR, urlService.getAbsoluteURL(ControllerURI.ADMIN_STATS_YEAR.toString()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAdminAuthorLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getStatsAdminAuthorLessonPageStack(lesson, StatsType.MONTH);
		
		if(pages != null && test!=null && lesson!=null){
			Page page = new Page(PageNameKey.STATS_LESSON_TEST, urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_LESSON_TEST.toString() + test.getId()));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAdminLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getStatsAdminLessonPageStack(lesson, StatsType.MONTH);
		
		if(pages != null && test!=null && lesson!=null){
			Page page = new Page(PageNameKey.STATS_LESSON_TEST, urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_LESSON_TEST.toString() + test.getId()));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAdminAuthorPageStack(User profile, StatsType statsType) {
		List<Page> pages = getAdminStatsPageStack(statsType);
		
		if(pages != null && statsType!=null && profile!=null){
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_AUTHOR_MONTH, profile.getFullName(), urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_MONTH.toString() + profile.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_AUTHOR_YEAR, profile.getFullName(), urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_YEAR.toString() + profile.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAdminAuthorLessonPageStack(Lesson lesson,	StatsType statsType) {
		List<Page> pages = new ArrayList<Page>();
		
		if(pages != null && statsType!=null && lesson != null){
			pages = getStatsAdminAuthorPageStack(lesson.getAuthor(),statsType);
			
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_LESSON_MONTH, lesson.getTitle(), urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_LESSON_MONTH.toString() + lesson.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_LESSON_YEAR, lesson.getTitle(), urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_AUTHOR_LESSON_YEAR.toString() + lesson.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getStatsAdminLessonPageStack(Lesson lesson, StatsType statsType) {
		List<Page> pages = getAdminStatsPageStack(statsType);
		
		if(pages != null && statsType!=null && lesson!=null){
			Page page = null;
			if(StatsType.MONTH == statsType){
				page = new Page(PageNameKey.STATS_LESSON_MONTH, urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_LESSON_MONTH.toString() + lesson.getId()));
			}
			else{
				page = new Page(PageNameKey.STATS_LESSON_YEAR, urlService.getAbsoluteURL(ControllerURI.STATS_ADMIN_LESSON_YEAR.toString() + lesson.getId()));
			}
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminBroadcastGroupPageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupProfilePageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_BROADCAST_GROUP, 
					urlService.getAbsoluteURL(ControllerURI.ADMIN_BROADCAST.toString() + profile.getId() + "/"));
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

}
