package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = requestManager.getSessionUser(request);
		
		if(request.getMethod().equals("POST")){
			
			Lesson newLesson = requestManager.getParamNewLesson(request.getParameterMap());
			//TODO Le falta el idtest
			if(newLesson!=null){
				newLesson.setIdUser(user.getId());
				newLesson = lessonService.createLesson(newLesson);
				
				
				//TODO Ver como hacer repositorio(BBDD) para ficheros de audio o video
				//TODO recuperar video o audio
				
				//TODO crear video en BBDD y mover fichero a disco
				
				List<String> tags = requestManager.getParamNewTags(request.getParameterMap());
				newLesson = lessonService.addLessonTags(newLesson, tags);
				
				//TODO otros
			}
			else{
				//TODO Error
			}
			
			
			//TODO SI todo es correcto cargarse los temporales que no hemos usado
		}
		

		request.getRequestDispatcher("/WEB-INF/views/newLesson.jsp").forward(request, response);
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
