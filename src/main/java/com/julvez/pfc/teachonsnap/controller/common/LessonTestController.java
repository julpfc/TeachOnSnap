package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.user.UserLessonTest;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class LessonTestController extends CommonController {

	private static final long serialVersionUID = 3593648651182715069L;
	private LessonService lessonService = LessonServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		LessonTest test = lessonService.getLessonTest(lesson.getIdLessonTest());
		
		if(request.getMethod().equals("POST")){
			UserLessonTest userTest = new UserLessonTest(test, request.getParameterMap());
			request.setAttribute("userTest", userTest);
		}			
				
		request.setAttribute("lesson", lesson);
		request.setAttribute("test", test);
				
	    request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);	 

	}

}
