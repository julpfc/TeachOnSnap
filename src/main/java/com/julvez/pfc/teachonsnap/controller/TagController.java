package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class TagController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private LessonService lessonService = LessonServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
			
		List<Lesson> lessons = lessonService.getLessonsFromTag(tag);		
	
		String acceptLang = request.getHeader("accept-language");
		Language browserLang = langService.getLanguageFromAccept(acceptLang);
		request.setAttribute("browserLang", browserLang);
				
		request.setAttribute("searchType", "tag");
		request.setAttribute("searchKeyword", tag);
		request.setAttribute("searchResults", lessons.size()); //Hasta que tengamos paginación
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
