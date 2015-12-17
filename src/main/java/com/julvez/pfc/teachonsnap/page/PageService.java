package com.julvez.pfc.teachonsnap.page;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.Question;
import com.julvez.pfc.teachonsnap.page.model.Page;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface PageService {

	public List<Page> getLessonPageStack(Lesson lesson);
	
	public List<Page> getEditLessonPageStack(Lesson lesson);
	
	public List<Page> getNewLessonTestPageStack(Lesson lesson);
	
	public List<Page> getEditLessonTestPageStack(Lesson lesson, LessonTest test);
	
	public List<Page> getEditQuestionPageStack(Lesson lesson, LessonTest test, Question question);
	
	public List<Page> getNewQuestionPageStack(Lesson lesson, LessonTest test);

	public List<Page> getAdminUsersPageStack();
	
	public List<Page> getAdminUsersSearchPageStack(String searchQuery, String searchType);

	public List<Page> getAdminUserProfilePageStack(User profile);

	public List<Page> getAdminGroupsSearchPageStack(String searchQuery);
	
	public List<Page> getAdminGroupsPageStack();

	public List<Page> getAdminGroupProfilePageStack(UserGroup profile);
	
	public List<Page> getAdminGroupFollowPageStack(UserGroup profile);
	
	public List<Page> getAdminGroupFollowAuthorPageStack(UserGroup profile);
	
	public List<Page> getAdminGroupFollowAuthorSearchPageStack(UserGroup profile, String searchQuery, String searchType);

	public List<Page> getAdminGroupFollowTagPageStack(UserGroup profile);
	
	public List<Page> getAdminGroupFollowTagSearchPageStack(UserGroup profile, String searchQuery);

	public List<Page> getUserFollowPageStack(User profile);
	
	public List<Page> getUserFollowAuthorPageStack(User profile);
	
	public List<Page> getUserFollowAuthorSearchPageStack(User profile, String searchQuery, String searchType);

	public List<Page> getLessonTestPageStack(Lesson lesson, LessonTest test);

	public List<Page> getStatsLessonTestPageStack(Lesson lesson, LessonTest test);

	public List<Page> getStatsLessonPageStack(Lesson lesson, String statsType);

	public List<Page> getStatsAuthorPageStack(User profile, String statsType);

	public List<Page> getStatsAuthorLessonPageStack(Lesson lesson, String statsType);
}
