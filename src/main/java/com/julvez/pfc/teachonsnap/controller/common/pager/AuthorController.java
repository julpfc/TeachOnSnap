package com.julvez.pfc.teachonsnap.controller.common.pager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.common.PagerController;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
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

	/**
     * Default constructor
     */
    public AuthorController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService(),
        	TagServiceFactory.getService());        
    }
    
    /**
	 * Constructor requires all parameters not to be null
	 * @param userService Provides the functionality to work with application's users.
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param statsService Provides the functionality to work with application's stats
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 * @param properties Property manager providing access to properties files
	 * @param stringManager String manager providing string manipulation utilities
	 * @param lessonService Provides the functionality to work with lessons.
	 * @param tagService Provides the functionality to work with lessons.
	 */
	public AuthorController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService, 
			TagService tagService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager,
				lessonService, tagService);
	}
	
	
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
