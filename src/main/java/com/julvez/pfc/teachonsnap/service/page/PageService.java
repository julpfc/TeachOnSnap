package com.julvez.pfc.teachonsnap.service.page;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.page.Page;

public interface PageService {

	public List<Page> getLessonPageStack(Lesson lesson);
	
	public List<Page> getEditLessonPageStack(Lesson lesson);
	
	public List<Page> getNewLessonTestPageStack(Lesson lesson);
	
	public List<Page> getEditLessonTestPageStack(Lesson lesson, LessonTest test);
	
	public List<Page> getEditQuestionPageStack(Lesson lesson, LessonTest test, Question question);
	
	public List<Page> getNewQuestionPageStack(Lesson lesson, LessonTest test);
}
