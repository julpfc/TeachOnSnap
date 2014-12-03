package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class HomeController extends CommonController {

	private static final long serialVersionUID = 1L;

	private LessonService lessonService = LessonServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Buscador
		// TODO TOP de lessons (más vistas, activas)
		// TODO Páginador
		
		List<Lesson> lastLessons = lessonService.getLastLessons(0);
		List<CloudTag> cloudTags = lessonService.getCloudTags();
		
		List<CloudTag> authorCloudTags = lessonService.getAuthorCloudTags();
		request.setAttribute("authorCloudTags", authorCloudTags);
		
		request.setAttribute("lastLessons", lastLessons);
		request.setAttribute("cloudTags", cloudTags);
		
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);	 

	}

	@Override
	protected boolean isPrivateZone() {		
		return false;
	}

}
