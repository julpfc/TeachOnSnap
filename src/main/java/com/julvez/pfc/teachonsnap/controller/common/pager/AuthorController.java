package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;

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
