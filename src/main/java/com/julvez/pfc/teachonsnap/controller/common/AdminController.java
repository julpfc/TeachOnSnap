package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * CommonController abstract implementation class AdminController. 
 * <p>
 * Performs administration's common tasks for a controller which will gather 
 * information and be passed to the view to be displayed.
 * <p>
 * Basically performs access permissions checkings as user is an adminsitrator
 * and handles this error. Implementations can be focus on controller logic.  
 */
public abstract class AdminController extends CommonController {

	private static final long serialVersionUID = -2188873315605476969L;

	/** Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack) */
	protected PageService pageService;
	
	/**
     * Default constructor
     */
    public AdminController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager(),
        	PageServiceFactory.getService());        
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
	 * @param pageService Provides the functionality to work with user pages (localized names & links) and the hierarchy (page stack)
	 */
	public AdminController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager, PageService pageService) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);
		
		if(pageService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.pageService = pageService;
	}
	
	
	@Override
	protected final void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		//check if user is logged and is an administrator
		if(user != null && user.isAdmin()){
			//Implementation will process the request
			processAdminController(request, response, visit, user);
		}
		else{
			//Access denied
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}	
	}

	@Override
	protected final boolean isPrivateZone() {
		//User MUST be logged in.
		return true;
	}

	/**
	 * Implementation for this AdminController to process the request and dispath the response to a JSP view.
	 * @param request Request
	 * @param response Response
	 * @param visit Current visit(session)
	 * @param user Logged-in user (administrator)
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected when the controller handles the request
	 */	
	protected abstract void processAdminController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) 
		throws ServletException, IOException ;
}
