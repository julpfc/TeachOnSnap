package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

/**
 * Servlet implementation class WebController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	/*	HttpSession session = request.getSession(true);
	    // Set the session valid for 5 secs
	    session.setMaxInactiveInterval(5);
	  */  
	 //   Lesson less = new Lesson();
	//	less.setId(1);
	//	less.setTitle(session.getId());
				
	//	request.setAttribute("lesson", less); 
		
		// TODO Buscador
		// TODO Nube de tags (más lessons)
		// TODO TOP de lessons (más vistas, activas)
		// TODO Últimas lessons
		
		
		List<Lesson> lastLessons = lessonService.getLastLessons();
		
		request.setAttribute("lastLessons", lastLessons);
	    request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
