package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;

public class EditLessonTestController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		LessonTest test = lessonTestService.getLessonTest(lesson.getIdLessonTest());
		
		if(request.getMethod().equals("POST")){
		}			
				
		request.setAttribute(Attribute.LESSON.toString(), lesson);
		request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
				
	    request.getRequestDispatcher("/WEB-INF/views/editTest.jsp").forward(request, response);	 
		
	    
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
