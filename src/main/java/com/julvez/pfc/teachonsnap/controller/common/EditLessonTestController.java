package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestServiceFactory;

public class EditLessonTestController extends CommonController {

	private static final long serialVersionUID = 7608540908435958036L;
	
	private LessonService lessonService = LessonServiceFactory.getService();
	private LessonTestService lessonTestService = LessonTestServiceFactory.getService();

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String[] params = requestManager.getControllerParams(request);
		
		if(params!=null && params.length>0 && stringManager.isNumeric(params[0])){
			
			int idLessonTest = Integer.parseInt(params[0]);
			
			LessonTest test = lessonTestService.getLessonTest(idLessonTest);
			
			if(test!=null){				
				
				Lesson lesson = lessonService.getLesson(test.getIdLesson());
				
				if(lesson!=null){
					
					User user = requestManager.getSessionUser(request);
					
					if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
						String publish = requestManager.getParamPublishLessonTest(request);
						
						if(publish!=null){
							if(stringManager.isTrue(publish)){
								lessonTestService.publish(test.getId(), user);
							}				
							else{
								lessonTestService.unpublish(test.getId(), user);
							}
							response.sendRedirect(lesson.getEditURL());
						}
						else {						
							//Editamos un test
							if(request.getMethod().equals("POST")){
							}			
							
						      response.setContentType("application/json");
						      response.setCharacterEncoding("UTF-8");
						 	 
						        // 3. Convert List<FileMetadata> into JSON format
						        ObjectMapper mapper = new ObjectMapper();
						 
						        // 4. Send resutl to client
						        mapper.writeValue(response.getOutputStream(), test);
							
							/*
							request.setAttribute(Attribute.LESSON.toString(), lesson);
							request.setAttribute(Attribute.LESSONTEST_QUESTIONS.toString(), test);
									
							request.getRequestDispatcher("/WEB-INF/views/editTest.jsp").forward(request, response);
							*/	 
						}
					
					}
					else{
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}						
				}
				else{
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}				
			}
			else{
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	    
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
