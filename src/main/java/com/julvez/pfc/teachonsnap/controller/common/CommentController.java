package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class CommentController extends CommonController {

	private static final long serialVersionUID = 5012290740498686847L;

	private LessonService lessonService = LessonServiceFactory.getService();
	private CommentService commentService = CommentServiceFactory.getService();
	
	
	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		String[] params = requestManager.splitParamsFromControllerURI(request); 
		
		String lessonURI = null;
		
		if(params != null && params.length>0){
			lessonURI = params[0];
		}
		
		Lesson lesson = lessonService.getLessonFromURI(lessonURI);
		
		if(lesson != null){
			int commentID = requestManager.getNumericParameter(request, Parameter.LESSON_COMMENTID);
			String isBanned = requestManager.getParameter(request,Parameter.LESSON_COMMENT_BAN);
			
			if(request.getMethod().equals("POST")){			
				String commentBody = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LESSON_COMMENT));
				boolean isEditing = requestManager.getBooleanParameter(request,Parameter.LESSON_COMMENT_EDIT);
				
				if(commentBody != null){
					if(isEditing){
						commentService.saveCommentBody(commentID, user.getId(), commentBody);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_SAVED);
					}
					else if(isBanned==null){
						commentService.createComment(lesson.getId(), user.getId(), commentBody, commentID);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_CREATED);
					}
					else if(stringManager.isTrue(isBanned)){
						commentService.blockComment(commentID, user, commentBody);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_BLOCKED);
					}
					else{
						//isEditing=false, isBanned=false
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
				}
				else{
					//No es correcto llamar sin body
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			else{
				//GET
				if(isBanned!= null){
					if(!stringManager.isTrue(isBanned)){
						commentService.unblockComment(commentID, user);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.COMMENT_UNBLOCKED);
					}
					else{
						//No es correcto llamar para banear por GET
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}
				}
				else{
					//No se debería llamar a este controlador sin parámetro
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			
			response.sendRedirect(lesson.getURL());
		}
		else{
			//La URI no era válida/No existe lección asociada
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}

}
