package com.julvez.pfc.teachonsnap.page;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.test.model.LessonTest;
import com.julvez.pfc.teachonsnap.lesson.test.model.Question;
import com.julvez.pfc.teachonsnap.page.model.Page;
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
}
