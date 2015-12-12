package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;

public class TagController extends PagerController {

	private static final long serialVersionUID = -86483240811806295L;
	

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));
				
		
		if(tag != null){
			lessons = tagService.getLessonsFromTag(tag, pageResult);
		}
		
		return lessons;
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		return stringManager.decodeURL(searchURI);
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		if(visit != null && lessons != null){
		
			Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));
		
			if(tag != null){
				statsService.saveTag(visit, tag);
			}
		}
	}
}
