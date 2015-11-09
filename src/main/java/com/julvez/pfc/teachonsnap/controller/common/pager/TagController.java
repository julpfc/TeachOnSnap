package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;

public class TagController extends PagerController {

	private static final long serialVersionUID = -86483240811806295L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		return tagService.getLessonsFromTag(stringManager.decodeURL(searchURI), pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		return stringManager.decodeURL(searchURI);
	}

}
