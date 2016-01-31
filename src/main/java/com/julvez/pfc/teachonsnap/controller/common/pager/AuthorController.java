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
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * AuthorController extends {@link PagerController}.
 * <p>
 * Paginates through the lessons from the author specified.. 
 * <p>
 * Mapped in {@link ControllerURI#AUTHOR}
 */
public class AuthorController extends PagerController {

	private static final long serialVersionUID = 6385337507495032569L;

	@Override
	protected List<Lesson> getLessons(String searchURI, int pageResult) {
		//get author from controller's URI and get lessons from author (pagination)
		return lessonService.getLessonsFromAuthor(searchURI, pageResult);
	}

	@Override
	protected String getSearchKeyword(String searchURI, List<Lesson> lessons) {
		//Use the specified author in controller's URI as Search keyword.
		//Try to get the full name from lessons.
		return lessons.size()>0?lessons.get(0).getAuthor().getFullName():stringManager.decodeURL(searchURI);
	}

	/* (non-Javadoc)
	 * Method overriden to provide extra "follow/unfollow author" functionality to the current controller.
	 */
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)	throws ServletException, IOException {
		 
		boolean follow = false;
		
		//if user is logged-in
		if(user != null){
			//check if the "follow Author" parameter is set
			int idFollowAuthor = requestManager.getNumericParameter(request, Parameter.FOLLOW_AUTHOR);
			
			//if it's set...
			if(idFollowAuthor > 0){
				follow = true;
			
				//get author from id
				User followAuthor = userService.getUser(idFollowAuthor);
				
				if(followAuthor != null){
					//follow author
					User modUser = userService.followAuthor(user, followAuthor);
					
					if(modUser != null){
						//success
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
						user = modUser;
					}
					else{
						//error
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					//reload page
					response.sendRedirect(request.getRequestURI());
				}
				else{
					//No author for this id -> error page
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			//check if the "unfollow Author" parameter is set
			int idUnFollowAuthor = requestManager.getNumericParameter(request, Parameter.UNFOLLOW_AUTHOR);
			
			//if it's set...
			if(idUnFollowAuthor > 0){
				follow = true;

				//get author from id
				User unfollowAuthor = userService.getUser(idUnFollowAuthor);
				
				if(unfollowAuthor != null){
					//unfollow author
					User modUser = userService.unfollowAuthor(user, unfollowAuthor);
					
					if(modUser != null){
						//success
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USER_SAVED);
						user = modUser;
					}
					else{
						//error
						setErrorSession(request, ErrorType.ERR_SAVE, ErrorMessageKey.SAVE_ERROR);
					}
					//reload page
					response.sendRedirect(request.getRequestURI());
				}
				else{
					//No author for this id -> error page
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
		}
		//If no "following" functionality was needed we use the super implementation
		if(!follow){			
			super.processController(request, response, visit, user);
		}
	}

	@Override
	protected void saveStats(Visit visit, String searchURI, List<Lesson> lessons) {
		// No stats for searched Author. Maybe in the future (see TagController for reference)
	}
	
}
