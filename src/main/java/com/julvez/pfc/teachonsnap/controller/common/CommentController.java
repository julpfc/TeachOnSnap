package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
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

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String lessonURI = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		
		if(lesson != null){
			int commentID = requestManager.getParamCommentID(request);
			String isBanned = requestManager.getParamBanComment(request);
			User user = requestManager.getSessionUser(request);
			
			if(request.getMethod().equals("POST")){			
				String commentBody = requestManager.getParamComment(request);
				boolean isEditing = requestManager.getParamEditComment(request);
				
				if(lesson!=null && commentBody != null){
					if(isEditing){
						commentService.saveCommentBody(commentID, user.getId(), commentBody);
					}
					else if(isBanned==null){
						commentService.createComment(lesson.getId(), user.getId(), commentBody, commentID);
					}
					else if(stringManager.isTrue(isBanned)){
						commentService.blockComment(commentID, user, commentBody);
					}
				}			
			}
			else{
				if(isBanned!= null && !stringManager.isTrue(isBanned)){
					commentService.unblockComment(commentID, user);
				}
			}
			
			response.sendRedirect(lesson.getURL());
		}
		else{
			//La URI no era válida/No existe lección asociada
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
