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
public class AuthorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private LessonService lessonService = LessonServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String author = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		List<Lesson> lessons = lessonService.getLessonsFromAuthor(author);		
		
		String acceptLang = request.getHeader("accept-language");
		Language browserLang = langService.getLanguageFromAccept(acceptLang);
		request.setAttribute("browserLang", browserLang);
		
		request.setAttribute("searchType", "author");
		request.setAttribute("searchKeyword", lessons.size()>0?lessons.get(0).getAuthor().getFullName():author);
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
