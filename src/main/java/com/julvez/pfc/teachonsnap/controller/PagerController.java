package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.tag.CloudTag;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.tag.TagService;
import com.julvez.pfc.teachonsnap.service.tag.TagServiceFactory;

public abstract class PagerController extends CommonController {

	private static final long serialVersionUID = 1428850419073596728L;

	protected static final int MAX_RESULTS_PAGE = 10;
	
	protected LessonService lessonService = LessonServiceFactory.getService();
	protected TagService tagService = TagServiceFactory.getService();
	protected StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected boolean isPrivateZone() {		
		return false;
	}
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);
		
		int pageResult = 0;
		boolean hasNextPage = false;
		String searchURI = null;
				
		if(params!=null && params.length>0){			
			searchURI = params[0];
			if(params.length>1 && !stringManager.isEmpty(params[1])){
				pageResult = Integer.parseInt(params[1]);
			}
			else if(!stringManager.isEmpty(params[0])){
				try{
					pageResult = Integer.parseInt(params[0]);
					searchURI = null;
				}
				catch(Throwable t){
					//TODO log exception
				}
			}
		}
		
		List<Lesson> lessons = getLessons(searchURI, pageResult);	
		
		if(lessons.size()>MAX_RESULTS_PAGE){
			hasNextPage = true;
			lessons.remove(MAX_RESULTS_PAGE);
		}
		
		String nextPage = null;
		if(hasNextPage){
			if(searchURI == null)
				nextPage = request.getServletPath()+"/"+(pageResult+MAX_RESULTS_PAGE);
			else
				nextPage = request.getServletPath()+"/"+searchURI+"/"+(pageResult+MAX_RESULTS_PAGE);
		}
		
		String prevPage = null;
		if(pageResult>0){
			if(searchURI == null)
				prevPage = request.getServletPath()+"/";
			else
				prevPage = request.getServletPath()+"/"+searchURI+"/";
			if(pageResult>MAX_RESULTS_PAGE){
				prevPage = prevPage + (pageResult-MAX_RESULTS_PAGE);
			}
		}
		request.setAttribute(Attribute.STRING_NEXTPAGE.toString(), nextPage);
		request.setAttribute(Attribute.STRING_PREVPAGE.toString(), prevPage);
		
		
		List<CloudTag> cloudTags = tagService.getCloudTags();
		List<CloudTag> authorCloudTags = tagService.getAuthorCloudTags();
		request.setAttribute(Attribute.LIST_CLOUDTAG_AUTHOR.toString(), authorCloudTags);
		request.setAttribute(Attribute.LIST_CLOUDTAG.toString(), cloudTags);

		request.setAttribute(Attribute.LIST_LESSON.toString(), lessons);
		
		request.setAttribute(Attribute.STRING_SEARCHTYPE.toString(),request.getServletPath().substring(1));
		
		String searchKeyword = getSearchKeyword(searchURI,lessons);
		
		if(!stringManager.isEmpty(searchKeyword)){
			request.setAttribute(Attribute.STRING_SEARCHKEYWORD.toString(), searchKeyword);
		}
	    
	    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);

	}
	
	protected abstract List<Lesson> getLessons(String searchURI, int pageResult);
	
	protected abstract String getSearchKeyword(String searchURI,List<Lesson> lessons);	
	

}
