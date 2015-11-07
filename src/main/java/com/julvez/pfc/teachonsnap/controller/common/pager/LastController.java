package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public class LastController extends PagerController {

	private static final long serialVersionUID = 5242969293667451025L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {		
		return lessonService.getLastLessons(pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {		
		return null;
	}

}
