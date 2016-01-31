package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;

/**
 * TagController extends {@link PagerController}.
 * <p>
 * Paginates through the lessons which contains an specific tag. 
 * <p>
 * Mapped in {@link ControllerURI#TAG}
 */
public class TagController extends PagerController {

	private static final long serialVersionUID = -8636543880785473414L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		List<Lesson> lessons = Collections.emptyList();
		
		//get tag from controller's URI
		Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));				
		
		if(tag != null){
			//get lessons from tag (pagination)
			lessons = tagService.getLessonsFromTag(tag, pageResult);
		}
		
		return lessons;
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		//Use the specified tag in controller's URI as Search keyword
		return stringManager.decodeURL(searchURI);
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		if(visit != null && lessons != null){
			//get tag from controller's URI
			Tag tag = tagService.getTag(stringManager.decodeURL(searchURI));
		
			if(tag != null){
				//save searched tag stats
				statsService.saveTag(visit, tag);
			}
		}
	}
}
