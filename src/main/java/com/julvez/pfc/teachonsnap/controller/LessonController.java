package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;


/**
 * Servlet implementation class TagController
 */
public class LessonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private LessonService lessonService = LessonServiceFactory.getService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LessonController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		List<Tag> tags = lessonService.getLessonTags(lesson.getId());
		List<Lesson> linkedLessons = lessonService.getLinkedLessons(lesson.getId());
		List<Link> moreInfoLinks = lessonService.getMoreInfoLinks(lesson.getId());
		List<Link> sourceLinks = lessonService.getSourceLinks(lesson.getId());		
		
		request.setAttribute("lesson", lesson);
		request.setAttribute("tags", tags);
		request.setAttribute("linkedLessons", linkedLessons);
		request.setAttribute("moreInfoLinks", moreInfoLinks);
		request.setAttribute("sourceLinks", sourceLinks);
				
	    request.getRequestDispatcher("/WEB-INF/views/lesson.jsp").forward(request, response);	 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
