package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

public class AuthorController extends PagerController {

	private static final long serialVersionUID = 6547159334243210678L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		return lessonService.getLessonsFromAuthor(searchURI,pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		return lessons.size()>0?lessons.get(0).getAuthor().getFullName():searchURI;
	}

	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)	throws ServletException, IOException {
		
		boolean follow = false;
		
		if(user != null){
			int idFollowAuthor = requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR);
			
			if(idFollowAuthor > 0){
				follow = true;
			
				User followAuthor = userService.getUser(idFollowAuthor);
				
				if(followAuthor != null){
					User modUser = userService.followAuthor(user, followAuthor);
					
					if(modUser != null){
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
						user = modUser;
					}
					else{
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}		
					response.sendRedirect(request.getRequestURI());
				}
				else{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			
			int idUnFollowAuthor = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR);
			
			if(idUnFollowAuthor > 0){
				follow = true;
			
				User unfollowAuthor = userService.getUser(idUnFollowAuthor);
				
				if(unfollowAuthor != null){
					User modUser = userService.unfollowAuthor(user, unfollowAuthor);
					
					if(modUser != null){
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
						user = modUser;
					}
					else{
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}		
					response.sendRedirect(request.getRequestURI());
				}
				else{
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
		}	
		if(!follow){			
			super.processController(request, response, visit, user);
		}
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		// TODO Ver si guardamos stats por autor		
	}
	
}
