package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.user.model.User;

public abstract class PagerController extends CommonController {

	private static final long serialVersionUID = 1428850419073596728L;

	protected LessonService lessonService = LessonServiceFactory.getService();
	protected TagService tagService = TagServiceFactory.getService();
	protected StringManager stringManager = StringManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	protected final int MAX_RESULTS_PAGE = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {

		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		int pageResult = getPageResult(params);
		
		if(pageResult >= 0){
			boolean hasNextPage = false;
			String searchURI = getSearchURI(params);
					
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
			requestManager.setAttribute(request, Attribute.STRING_NEXTPAGE, nextPage);
			requestManager.setAttribute(request, Attribute.STRING_PREVPAGE, prevPage);
			
			
			List<CloudTag> cloudTags = tagService.getCloudTags();
			List<CloudTag> authorCloudTags = tagService.getAuthorCloudTags();
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG_AUTHOR, authorCloudTags);
			requestManager.setAttribute(request, Attribute.LIST_CLOUDTAG, cloudTags);
	
			requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);
			
			requestManager.setAttribute(request, Attribute.STRING_SEARCHTYPE,request.getServletPath().substring(1));
			
			String searchKeyword = getSearchKeyword(searchURI,lessons);
			
			if(!stringManager.isEmpty(searchKeyword)){
				requestManager.setAttribute(request, Attribute.STRING_SEARCHKEYWORD, searchKeyword);
			}
		    
		    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	protected String getSearchURI(String[] params) {
		String searchURI = null;
		
		if(params!=null && params.length>0 && !stringManager.isNumeric(params[0])){
			searchURI = params[0];
		}
		
		return searchURI;
	}

	protected int getPageResult(String[] params) {
		int pageResult = -1;
		
		if(params!=null && params.length>0){
			if(params.length>1){
				if(stringManager.isNumeric(params[1])){
					pageResult = Integer.parseInt(params[1]);
				}				
			}
			else if(stringManager.isNumeric(params[0])){
				pageResult = Integer.parseInt(params[0]);
			}
			else{
				pageResult = 0;
			}
		}
		else{
			pageResult = 0;
		}
		return pageResult;
	}
	
	protected abstract List<Lesson> getLessons(String searchURI, int pageResult);
	
	protected abstract String getSearchKeyword(String searchURI,List<Lesson> lessons);	
	

}
