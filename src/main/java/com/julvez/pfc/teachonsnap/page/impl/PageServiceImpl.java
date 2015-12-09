package com.julvez.pfc.teachonsnap.page.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.Question;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.page.model.PageNameKey;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.User;

public class PageServiceImpl implements PageService {

	@Override
	public List<Page> getLessonPageStack(Lesson lesson) {
		List<Page> pages = null;
		Page page = null;
		
		if(lesson!=null){
			pages = new ArrayList<Page>(); 
			page = new Page(PageNameKey.LESSON, lesson.getURL());
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
					ControllerURI.ADMIN_USER_MANAGER + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType);
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminUsersPageStack() {
		List<Page> pages = new ArrayList<Page>();
		Page page = new Page(PageNameKey.ADMIN_USERS, ControllerURI.ADMIN_USER_MANAGER.toString());
		pages.add(page);
				
		return pages;
	}

	@Override
	public List<Page> getAdminUserProfilePageStack(User profile) {
		List<Page> pages = getAdminUsersPageStack();
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_USER_PROFILE, ControllerURI.ADMIN_USER_PROFILE.toString() + profile.getId() + "/");
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
					ControllerURI.ADMIN_GROUP_MANAGER + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery);
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getAdminGroupsPageStack() {
		List<Page> pages = new ArrayList<Page>();
		Page page = new Page(PageNameKey.ADMIN_GROUPS, ControllerURI.ADMIN_GROUP_MANAGER.toString());
		pages.add(page);
				
		return pages;
	}

	@Override
	public List<Page> getAdminGroupProfilePageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupsPageStack();
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_PROFILE, profile.getGroupName(),
					ControllerURI.ADMIN_GROUP_PROFILE.toString() + profile.getId() + "/");
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
					ControllerURI.ADMIN_GROUP_FOLLOWS.toString() + profile.getId() + "/");
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
					ControllerURI.ADMIN_GROUP_FOLLOW_AUTHOR.toString() + profile.getId() + "/");
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
					ControllerURI.ADMIN_GROUP_FOLLOW_AUTHOR.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType);
			pages.add(page);
		}		
		return pages;
	}

	@Override
	public List<Page> getAdminGroupFollowTagPageStack(UserGroup profile) {
		List<Page> pages = getAdminGroupFollowPageStack(profile);
		
		if(pages != null && profile != null){
			Page page = new Page(PageNameKey.ADMIN_GROUP_FOLLOW_TAG, 
					ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/");
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
					ControllerURI.ADMIN_GROUP_FOLLOW_TAG.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery);
			pages.add(page);
		}		
		return pages;
	}

	@Override
	public List<Page> getUserFollowPageStack(User profile) {
		List<Page> pages = new ArrayList<Page>();
		
		if(profile != null){
			Page page = new Page(PageNameKey.USER_FOLLOWS, ControllerURI.USER_FOLLOWS.toString() + profile.getId() + "/");
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
					ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/");
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
					ControllerURI.USER_FOLLOW_AUTHOR.toString() + profile.getId() + "/" + "?" + Parameter.SEARCH_QUERY + "=" + searchQuery + "&" + Parameter.SEARCH_TYPE + "=" + searchType);
			pages.add(page);
		}		
		return pages;
	}

}
