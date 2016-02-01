package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class FollowController extends CommonController {

	private static final long serialVersionUID = -6715205860379822910L;

	private LessonService lessonService = LessonServiceFactory.getService();
	
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params == null || (params != null && params.length == 1 && stringManager.isNumeric(params[0]))){
			
			User profile = null;
			
			if(params != null){
				short idUser = Short.parseShort(params[0]);
			
				profile = userService.getUser(idUser);
			
				if(profile == null){
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			else{
				profile = user;
			}
			
			
			requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);
			
			List<User> authors = userService.getUsersFromIDs(profile.getAuthorFollowed());
			requestManager.setAttribute(request, Attribute.LIST_USER, authors);
			
			List<Lesson> lessons = lessonService.getLessonsFromIDs(profile.getLessonFollowed());
			requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);
					
			request.getRequestDispatcher("/WEB-INF/views/follow.jsp").forward(request, response);				
					
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
