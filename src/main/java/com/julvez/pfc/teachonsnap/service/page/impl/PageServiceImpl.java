package com.julvez.pfc.teachonsnap.service.page.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.page.Page;
import com.julvez.pfc.teachonsnap.model.page.PageNameKey;
import com.julvez.pfc.teachonsnap.service.page.PageService;

public class PageServiceImpl implements PageService {

	@Override
	public List<Page> getLessonPageStack(Lesson lesson) {
		List<Page> pages = null;
		Page page = null;
		
		if(lesson!=null){
			pages = new ArrayList<Page>(); 
			page = new Page(PageNameKey.LESSON.toString(), lesson.getURL());
			pages.add(page);
		}		
		
		return pages;
	}

	@Override
	public List<Page> getEditLessonPageStack(Lesson lesson) {
		List<Page> pages = getLessonPageStack(lesson);
		
		if(pages != null){
			Page page = new Page(PageNameKey.EDIT_LESSON.toString(), lesson.getEditURL());
			pages.add(page);
		}
		return pages;
	}

	@Override
	public List<Page> getNewLessonTestPageStack(Lesson lesson) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null){
			Page page = new Page(PageNameKey.NEW_LESSON_TEST.toString(), lesson.getNewTestURL());
			pages.add(page);
		}
		return pages;
	}

	@Override
	public List<Page> getEditLessonTestPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getEditLessonPageStack(lesson);
		
		if(pages != null && test!=null){
			Page page = new Page(PageNameKey.EDIT_LESSON_TEST.toString(), test.getEditURL());
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

	@Override
	public List<Page> getEditQuestionPageStack(Lesson lesson, LessonTest test, Question question) {
		List<Page> pages = getEditLessonTestPageStack(lesson,test);
		
		if(pages != null && question!=null){
			Page page = new Page(PageNameKey.EDIT_QUESTION.toString(), question.getEditURL());
			pages.add(page);
		}
		else pages = null;
		return pages;

	}

	@Override
	public List<Page> getNewQuestionPageStack(Lesson lesson, LessonTest test) {
		List<Page> pages = getEditLessonTestPageStack(lesson,test);
		
		if(pages != null && test!=null){
			Page page = new Page(PageNameKey.NEW_QUESTION.toString(), test.getNewQuestionURL());
			pages.add(page);
		}
		else pages = null;
		return pages;
	}

}
