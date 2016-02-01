package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.controller.model.Attribute;
import com.julvez.pfc.teachonsnap.controller.model.Parameter;
import com.julvez.pfc.teachonsnap.controller.model.SessionAttribute;
import com.julvez.pfc.teachonsnap.error.model.ErrorBean;
import com.julvez.pfc.teachonsnap.error.model.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.error.model.ErrorType;
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
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
import com.julvez.pfc.teachonsnap.stats.model.StatsPropertyName;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Servlet abstract implementation class CommonController. 
 * <p>
 * Performs common tasks for a controller which will gather information and be
 * passed to the view to be displayed.  
 */
public abstract class CommonController extends HttpServlet {
	
	private static final long serialVersionUID = -2556147248125570983L;
	
	/** Provides the functionality to work with application's users. */
	protected UserService userService;
	
	/** Provides the functionality to work with different languages to the application */
	protected LangService langService;
	
	/** Provides the functionality to work with application's URLs */
	protected URLService urlService;
	
	/** Provides the functionality to work with application's stats */
	protected StatsService statsService;
	
	/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
	protected RequestManager requestManager;

	/** Log manager providing logging capabilities */
	protected LogManager logger;
	
	/** Property manager providing access to properties files */
	protected PropertyManager properties;
	
	/** String manager providing string manipulation utilities */
	protected StringManager stringManager;
	
		
	/**
     * Default constructor
     */
    public CommonController() {
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
	public CommonController(UserService userService,
			LangService langService, URLService urlService,
			StatsService statsService, RequestManager requestManager,
			LogManager logger, PropertyManager properties,
			StringManager stringManager) {

		super();
		
		if(userService == null || langService == null || urlService == null
				|| statsService == null || requestManager == null || logger == null
				|| properties == null || stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}		
		this.userService = userService;
		this.langService = langService;
		this.urlService = urlService;
		this.statsService = statsService;
		this.requestManager = requestManager;
		this.logger = logger;
		this.properties = properties;
		this.stringManager = stringManager;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Fixes Tomcat's bug because ISO-Latin1 while dispatching a view (see project's document)
		request.setCharacterEncoding("UTF-8");
		
		logger.info("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

		//get accept language
		String acceptLang = requestManager.getRequestLanguage(request);
		
		//get current visit from HTTP session
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		//check if visit should be created for anon users
		if(visit == null){			
			if(properties.getBooleanProperty(StatsPropertyName.ENABLE_ANON_VISIT_COUNTER)){
				visit = statsService.createVisit(requestManager.getIP(request));
				requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);						
			}
		}

		//check if user asked to change the navigation language
		String paramLang = requestManager.getParameter(request,Parameter.CHANGE_LANGUAGE);
		
		//decide user language
		Language userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
		
		//get list of supported languages
		List<Language> langs = langService.getAllLanguages();

		User user = null;
		if(visit != null){
			//update navigation language to session
			visit.setIdLanguage(userLang.getId());
			requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);

			//get user from visit
			user = visit.getUser();				
		
			//update user's language if necessary
			if(user!=null && user.getLanguage().getId() != userLang.getId()){
				User modUser = userService.saveUserLanguage(user, userLang);
				if(modUser!=null){				
					visit.setUser(modUser);
					requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
				}
			}
		}
		
		//get host where the app is running
		String host = urlService.getHost();
		
		//Store model data to the request to be displayed on the view
		requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);
		requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, userLang);
		requestManager.setAttribute(request, Attribute.USER, user);
		requestManager.setAttribute(request, Attribute.STRING_HOST, host);
		
		//Ask for login in private areas
		if(user==null && isPrivateZone()){
			setErrorSession(request, ErrorType.ERR_LOGIN, ErrorMessageKey.NONE);
			String lastPage = urlService.getHomeURL();
			response.sendRedirect(lastPage);
		}
		else{
			//Reset previous errors
			ErrorBean error = getErrorSession(request);			
			setAttributeErrorBean(request, error);						
			setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.NONE);
			
			//Process implementation of this class
			processController(request, response, visit, user);			
			
			//Store this as the last page
			requestManager.setSessionAttribute(request, SessionAttribute.LAST_PAGE, request.getRequestURI());
		}		
		logger.info("####END#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Process HTTP POST requests at doGet()
		doGet(request, response);
	}

	/**
	 * Implementation for this controller to process the request and dispath the response to a JSP view.
	 * @param request Request
	 * @param response Response
	 * @param visit Current visit(session)
	 * @param user Logged-in user
	 * @throws ServletException if the request could not be handled
	 * @throws IOException if an input or output error is detected when the controller handles the request
	 */
	protected abstract void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException;
	
	/**
	 * Indicates if the implementation requires user to be logged-in to access.
	 * @return true if user must be logged-in to access
	 */
	protected abstract boolean isPrivateZone();
		
	/**
	 * Returns last error ErrorBean from HTTP session (creates a new one if necessary)
	 * @param request Request
	 * @return last error ErrorBean from HTTP session (creates a new one if necessary)
	 */
	private ErrorBean getErrorSession(HttpServletRequest request) {
		ErrorBean error = requestManager.getSessionAttribute(request, SessionAttribute.ERROR, ErrorBean.class);		
		if(error == null){
			error = new ErrorBean(ErrorType.ERR_NONE,ErrorMessageKey.NONE);
		}
		return error;
	}
	
	/**
	 * Stores error related information on request
	 * @param request Request
	 * @param error ErrorBean containing error information
	 */
	protected void setAttributeErrorBean(HttpServletRequest request, ErrorBean error) {
		if(error!=null){		
			switch(error.getType()){
				case ERR_LOGIN:
					//This will trigger the login modal
					requestManager.setAttribute(request, Attribute.STRING_LOGINERROR, "loginError");
					break;
				case ERR_NONE:
					if(error.getMessageKey()!=null){
						//Sets Message key to be displayed in a dialog
						requestManager.setAttribute(request, Attribute.STRING_ERRORMESSAGEKEY, error.getMessageKey());
					}
					break;
				default:
					if(error.getMessageKey()!=null){
						//Sets Message key to be displayed in a dialog
						requestManager.setAttribute(request, Attribute.STRING_ERRORMESSAGEKEY, error.getMessageKey());
					}
					requestManager.setAttribute(request, Attribute.STRING_ERRORTYPE, error.getType().toString());
					break;				
			}
		}
	}
	
	/**
	 * Creates and sets the last error to the session 
	 * @param request Request
	 * @param type Error type
	 * @param messageKey Error localization message key
	 */
	protected void setErrorSession(HttpServletRequest request, ErrorType type, ErrorMessageKey messageKey){
		requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(type, messageKey));
	}
	
}

