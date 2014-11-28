package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public abstract class PagerController extends CommonController {

	private static final long serialVersionUID = 1428850419073596728L;

	protected static final int MAX_RESULTS_PAGE = 1;
	
	protected LessonService lessonService = LessonServiceFactory.getService();
	protected StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);
		
		int pageResult = 0;
		boolean hasNextPage = false;
		String searchURI = null;
				
		if(params!=null && params.length>0){			
			searchURI = params[0];
			if(params.length>1 && !stringManager .isEmpty(params[1])){
				pageResult = Integer.parseInt(params[1]);
			}
		}
		
		List<Lesson> lessons = getLessons(searchURI, pageResult);	
		
		if(lessons.size()>MAX_RESULTS_PAGE){
			hasNextPage = true;
			lessons.remove(MAX_RESULTS_PAGE);
		}
		
		String nextPage = null;
		if(hasNextPage){
			nextPage = request.getServletPath()+"/"+searchURI+"/"+(pageResult+MAX_RESULTS_PAGE);
		}
		
		String prevPage = null;
		if(pageResult>0){
			prevPage = request.getServletPath()+"/"+searchURI+"/";
			if(pageResult>MAX_RESULTS_PAGE){
				prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
			}
		}
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("prevPage", prevPage);
		
		
		List<CloudTag> cloudTags = lessonService.getCloudTags();
		List<CloudTag> authorCloudTags = lessonService.getAuthorCloudTags();
		request.setAttribute("authorCloudTags", authorCloudTags);
		request.setAttribute("cloudTags", cloudTags);

		request.setAttribute("lessons", lessons);
		
		request.setAttribute("searchType",request.getServletPath().substring(1));
		
		String searchKeyword = getSearchKeyword(searchURI,lessons);
		
		if(!stringManager.isEmpty(searchKeyword)){
			request.setAttribute("searchKeyword", searchKeyword);
		}
	    
	    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);

	}
	
	protected abstract List<Lesson> getLessons(String searchURI, int pageResult);
	
	protected abstract String getSearchKeyword(String searchURI,List<Lesson> lessons);	
	

}
