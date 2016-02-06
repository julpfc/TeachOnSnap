package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.Attribute;
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
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * FollowController extends {@link CommonController}.
 * <p>
 * Lists followed authors and lessons by the user.   
 * <p> 
 * Loads information about a user, followers and authors and redirects 
 * to the follow.jsp view to select author/lesson to be un/followed.
 * <p>
 * Mapped in {@link ControllerURI#USER_FOLLOWS}
 */
public class FollowController extends CommonController {

	private static final long serialVersionUID = 1246453638086965160L;
	/** Provides the functionality to work with lessons. */
	
	protected LessonService lessonService;
			
	/**
     * Default constructor
     */
    public FollowController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	LessonServiceFactory.getService());        
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
	 */
	public FollowController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, LessonService lessonService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(lessonService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.lessonService = lessonService;		
	}
	
	
	@Override
	protected void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user)
			throws ServletException, IOException {
		
		//get controller params from URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		//check valid params
		if(params == null || (params != null && params.length == 1 && stringManager.isNumeric(params[0]))){
			User profile = null;
			boolean error = false;
			
			//try to get user from URI (admin mode)
			if(params != null){
				short idUser = Short.parseShort(params[0]);			
				profile = userService.getUser(idUser);
			
				//if user not found
				if(profile == null){
					error = true;
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			else{
				//use current user
				profile = user;
			}
			
			//if no admin error, continue to the view
			if(!error){
				//get page common information and store for the view
				requestManager.setAttribute(request, Attribute.USER_PROFILE, profile);				
				List<User> authors = userService.getUsersFromIDs(profile.getAuthorFollowed());
				requestManager.setAttribute(request, Attribute.LIST_USER, authors);				
				List<Lesson> lessons = lessonService.getLessonsFromIDs(profile.getLessonFollowed());
				requestManager.setAttribute(request, Attribute.LIST_LESSON, lessons);
				
				//dispatch to view
				request.getRequestDispatcher("/WEB-INF/views/follow.jsp").forward(request, response);
			}					
		}
		else{
			//bad params
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
}
