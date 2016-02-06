package com.julvez.pfc.teachonsnap.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.CommonController;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
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
 * UserProfileController extends {@link CommonController}.
 * <p>
 * Lists user details, and allows to edit them.
 * <p>
 * Manages GET requests to send password reminder and POST request to edit
 * user's details from userProfile.jsp view.
 * <p>
 * Loads information about a user and redirects to the userProfile.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#USER_PROFILE}
 */
public class UserProfileController extends CommonController {

	private static final long serialVersionUID = 8479797206169892631L;
	
	/**
     * Default constructor
     */
	public UserProfileController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	StatsServiceFactory.getService(),
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	PropertyManagerFactory.getManager(),
        	StringManagerFactory.getManager());        
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
	 */
	public UserProfileController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager) {

		super(userService, langService, urlService, statsService, 
				requestManager, logger, properties, stringManager);						
	}

	@Override
	protected void processController(HttpServletRequest request,
			HttpServletResponse response, Visit visit, User user) throws ServletException, IOException {
		
		String prevPage = requestManager.getSessionAttribute(request, SessionAttribute.LAST_PAGE);
		
		//user is requesting modifications...
		if(request.getMethod().equals("POST")){
			//get user details from view form
			String firstname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.FIRST_NAME));
			String lastname = stringManager.unescapeHTML(requestManager.getParameter(request,Parameter.LAST_NAME));
			String oldPassword = requestManager.getParameter(request,Parameter.OLD_PASSWORD);
			String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);
		
			//check if user wants to edit names..
			if(!stringManager.isEmpty(firstname) && !stringManager.isEmpty(lastname)){
				//check if data changed
				if(firstname.equals(user.getFirstName()) && lastname.equals(user.getLastName())){
					//Nothing change
					setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);						
				}
				else{					
					if(!firstname.equals(user.getFirstName())){
						//save first name
						user = userService.saveFirstName(user, firstname);					
					}
					if(!lastname.equals(user.getLastName())){
						//save last name
						user = userService.saveLastName(user, lastname);
					}
					requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
					setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.USERNAME_SAVED);			
				}	
				//redirect to previous page
				response.sendRedirect(prevPage);
			}
			else if(!stringManager.isEmpty(oldPassword) && !stringManager.isEmpty(newPassword)){
				//check if user wants to change password..
				if(userService.validatePassword(user, oldPassword)){
					if(newPassword.equals(oldPassword)){
						//Nothing change
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.SAVE_NOCHANGES);						
					}
					else{
						//save password
						userService.savePassword(user, newPassword);
						setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED);			
					}
				}
				else{
					setErrorSession(request, ErrorType.ERR_INVALID_INPUT, ErrorMessageKey.WRONG_CURRENT_PASSWORD);
				}
				//redirect to previous page
				response.sendRedirect(prevPage);
			}
			else{
				//bad params
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}			
			
		}
		else{
			//No modifications requested
			//User information is already loaded in CommonController
			//dispatch to view
			request.getRequestDispatcher("/WEB-INF/views/userProfile.jsp").forward(request, response);
		}
	}

	@Override
	protected boolean isPrivateZone() {
		return true;
	}
}
