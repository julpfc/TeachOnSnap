package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.julvez.pfc.teachonsnap.user.model.User;

public class DraftsController extends CommentController {

	private static final long serialVersionUID = 6547159334243210678L;

	protected LessonService lessonService = LessonServiceFactory.getService();
	protected StringManager stringManager = StringManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	protected final int MAX_RESULTS_PAGE = properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);

	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)	throws ServletException, IOException {
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		int pageResult = getPageResult(params);
		
		if(pageResult >= 0){
			boolean hasNextPage = false;
			String searchURI = getSearchURI(params);
					
			List<Lesson> lessons = lessonService.getLessonDraftsFromUser(searchURI,pageResult);	
			
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
		
			requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);
			
		    
		    request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);
		}
		else{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}		
	}
	
	@Override
	protected boolean isPrivateZone() {
		return true;
	}

	private int getPageResult(String[] params) {
		int pageResult = -1;
		
		if(params!=null && params.length>0){
			if(params.length>1){
				if(stringManager.isNumeric(params[1])){
					pageResult = Integer.parseInt(params[1]);
				}				
			}
			else{
				pageResult = 0;
			}
		}

		return pageResult;
	}
	
	private String getSearchURI(String[] params) {
		String searchURI = null;
		
		if(params!=null && params.length>0){
			searchURI = params[0];
		}
		
		return searchURI;
	}

}
