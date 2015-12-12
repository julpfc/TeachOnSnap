package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

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

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		//No stats to save.		
	}

}
