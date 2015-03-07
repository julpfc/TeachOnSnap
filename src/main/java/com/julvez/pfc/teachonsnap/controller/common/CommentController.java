package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.comment.CommentService;
import com.julvez.pfc.teachonsnap.service.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class CommentController extends CommonController {

	private static final long serialVersionUID = 5012290740498686847L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private CommentService commentService = CommentServiceFactory.getService();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		
		if(request.getMethod().equals("POST")){			
			String commentBody = requestManager.getParamComment(request);
			int commentID = requestManager.getParamCommentID(request);
			
			if(commentBody != null){
				User user = requestManager.getSessionUser(request);
				commentService.createComment(lesson.getId(), user.getId(), commentBody, commentID);
			}			
		}
		response.sendRedirect(lesson.getURL());
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
