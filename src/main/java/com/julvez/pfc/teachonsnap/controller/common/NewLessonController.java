package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;

public class NewLessonController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		User user = requestManager.getSessionUser(request);
		
		if(request.getMethod().equals("POST")){
			
			Lesson newLesson = requestManager.getParamNewLesson(request.getParameterMap());
			//TODO Le falta el idtest, el uriname
			if(newLesson!=null){
				newLesson.setIdUser(user.getId());
				//TODO Crear lesson en BBDD
				
				
				//TODO recuperar video o audio
				
				//TODO crear video en BBDD y mover fichero a disco
			}
			else{
				//TODO Error
			}
			
		}
		

		request.getRequestDispatcher("/WEB-INF/views/newLesson.jsp").forward(request, response);
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
