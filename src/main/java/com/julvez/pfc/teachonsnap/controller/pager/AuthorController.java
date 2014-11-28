package com.julvez.pfc.teachonsnap.controller.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.PagerController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public class AuthorController extends PagerController {

	private static final long serialVersionUID = 6547159334243210678L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		return lessonService.getLessonsFromAuthor(searchURI,pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		return lessons.size()>0?lessons.get(0).getAuthor().getFullName():searchURI;
	}

}
