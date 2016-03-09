package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.ErrorBean;
import com.julvez.pfc.teachonsnap.controller.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.controller.model.ErrorType;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Servlet implementation class ChangePWController. 
 * <p>
 * Process the user's change of password. It requires a temporary identification token
 * that will be sent to the user's email to verify his identity. It allows user to
 * change the password and redirects to the home.   
 * <p>
 * It's called from changepw.jsp view to process POST requests.
 * <p>
 * It's a view controller, processes the request and dispath to the changepw.jsp view.
 * <p>
 * Mapped in {@link ControllerURI#CHANGE_PASSWORD}
 */
public class ChangePWController extends HttpServlet {
	
	private static final long serialVersionUID = -8840555670107967669L;
	
	/** Provides the functionality to work with application's users. */
	private UserService userService;
	
	/** Provides the functionality to work with different languages to the application */
	private LangService langService;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
	private RequestManager requestManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;

	
	/**
     * Default constructor
     */
    public ChangePWController() {
    	this(UserServiceFactory.getService(),
        	LangServiceFactory.getService(),
        	URLServiceFactory.getService(),        	
        	RequestManagerFactory.getManager(),
        	LogManagerFactory.getManager(),
        	StringManagerFactory.getManager());        
    }
    
    /**
	 * Constructor requires all parameters not to be null
	 * @param userService Provides the functionality to work with application's users.
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public ChangePWController(UserService userService,
			LangService langService, URLService urlService,
			RequestManager requestManager, LogManager logger, 
			StringManager stringManager) {

		super();
		
		if(userService == null || langService == null || urlService == null
				|| requestManager == null || logger == null
				|| stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.userService = userService;
		this.langService = langService;
		this.urlService = urlService;
		this.requestManager = requestManager;
		this.logger = logger;
		this.stringManager = stringManager;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Fixes Tomcat's bug because ISO-Latin1 while dispatching a view (see project's document)
		request.setCharacterEncoding("UTF-8");
		
		//Loguear la página en la que estamos
		logger.info("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

		//Get params from controller's URI
		String[] params = requestManager.splitParamsFromControllerURI(request);
		
		if(params!=null && params.length>0){
			//get token from URI params
			String token = params[0];
			
			//get user from temporary identification token
			User user = userService.getUserFromPasswordTemporaryToken(token);
			
			//if user was identified
			if(user != null){
				//get basic information for the view and store to the request
				String host = urlService.getHost();
				List<Language> langs = langService.getAllLanguages();
				requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);
				requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, user.getLanguage());
				requestManager.setAttribute(request, Attribute.USER, user);
				requestManager.setAttribute(request, Attribute.STRING_HOST, host);
				
				//if it's a POST, user is saving new password
				if(request.getMethod().equals("POST")){
					//get new password
					String newPassword = requestManager.getParameter(request,Parameter.NEW_PASSWORD);					
					
					if(!stringManager.isEmpty(newPassword)){
						//Save new password
						userService.savePassword(user, newPassword);
						
						//Delete the temporary token
						userService.deletePasswordTemporaryToken(user);
						
						//Sets message for the user and redirects to home
						requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.PASSWORD_CHANGED));
						response.sendRedirect(urlService.getHomeURL());
					}
					else{
						//Empty password -> Bad request
						response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					}				
				}
				else{
					request.getRequestDispatcher("/WEB-INF/views/changepw.jsp").forward(request, response);	 
				}
			}
			else{
				//No user for this token
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
		else{
			//No token -> bad request
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		logger.info("####END#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Process HTTP POST requests at doGet()
		doGet(request, response);
	}

}
