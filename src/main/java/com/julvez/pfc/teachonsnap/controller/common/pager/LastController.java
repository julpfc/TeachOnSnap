package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;

/**
 * LastController extends {@link PagerController}.
 * <p>
 * Paginates through the last lessons published. 
 * <p>
 * Mapped in {@link ControllerURI#LESSONS_BY_LAST}
 */
public class LastController extends PagerController {

	private static final long serialVersionUID = -5529039511072519457L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {	
		//get last lessons published (pagination)
		return lessonService.getLastLessons(pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {		
		//No keyword needed
		return null;
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		//No stats to save.		
	}

}
