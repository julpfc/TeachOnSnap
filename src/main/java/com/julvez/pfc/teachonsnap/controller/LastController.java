package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class LastController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_RESULTS_PAGE = 1;
    
	private LessonService lessonService = LessonServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();

	private RequestManager requestManager = RequestManagerFactory.getManager();	
	private StringManager stringManager = StringManagerFactory.getManager();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LastController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] params = requestManager.getControllerParams(request);
		
		int pageResult = 0;
		boolean hasNextPage = false;
				
		if(params!=null && params.length>0 && !stringManager.isEmpty(params[0])){			
			pageResult = Integer.parseInt(params[0]);
		}
		
		List<Lesson> lessons = lessonService.getLastLessons(pageResult);	
		
		if(lessons.size()>MAX_RESULTS_PAGE){
			hasNextPage = true;
			lessons.remove(MAX_RESULTS_PAGE);
		}
		String nextPage = hasNextPage?"/last/"+(pageResult+MAX_RESULTS_PAGE):null;
		String prevPage = pageResult>0?("/last/"+(pageResult>MAX_RESULTS_PAGE?pageResult-MAX_RESULTS_PAGE:"")):null;
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("prevPage", prevPage);
		
		String acceptLang = requestManager.getAcceptLanguage(request);
		short sessionIdLang = requestManager.getSessionIdLanguage(request);				
		String paramLang = requestManager.getParamChangeLanguage(request);
		User sessionUser = requestManager.getSessionUser(request);
		
		Language userLang = langService.getUserSessionLanguage(acceptLang,sessionIdLang,paramLang,sessionUser);
		// TODO Actualizar usuario BBDD/Cache/Session
		requestManager.setUserSessionLanguage(request,userLang);
		
		request.setAttribute("userLang", userLang);
		
		List<CloudTag> cloudTags = lessonService.getCloudTags();
		request.setAttribute("cloudTags", cloudTags);
		List<CloudTag> authorCloudTags = lessonService.getAuthorCloudTags();
		request.setAttribute("authorCloudTags", authorCloudTags);

		request.setAttribute("lessons", lessons);		 
		request.getRequestDispatcher("/WEB-INF/views/lessons.jsp").forward(request, response);	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
