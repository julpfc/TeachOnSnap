package com.julvez.pfc.teachonsnap.controller.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.PagerController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public class TagController extends PagerController {

	private static final long serialVersionUID = -86483240811806295L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		return tagService.getLessonsFromTag(searchURI,pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		// TODO Revisar si lo del URI del tag es válido
		return searchURI;
	}

}
