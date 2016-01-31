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
import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Servlet implementation class ErrorController. 
 * <p>
 * Extracts last error/exception information and parses it to be displayed on 
 * the error.jsp view. 
 * <p>
 * It's called from other controllers and default error handler.
 * <p>
 * Sends the information to the error.jsp view to be displayed.
 * <p>
 * Mapped in {@link ControllerURI#ERROR}
 */
public class ErrorController extends HttpServlet{

	private static final long serialVersionUID = 1007605973429696090L;

	/** Provides the functionality to work with different languages to the application */
	private LangService langService;
	
	/** Provides the functionality to work with application's URLs. */
	private URLService urlService;

	/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
	private RequestManager requestManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	
	/**
     * Default constructor
     */
    public ErrorController() {
        this(LangServiceFactory.getService(),
        	URLServiceFactory.getService(),
        	RequestManagerFactory.getManager(),        	
        	LogManagerFactory.getManager());
    }
            
    /**
     * Constructor requires all parameters not to be null
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs. 
	 * @param requestManager Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 */
	public ErrorController(LangService langService, URLService urlService,
			RequestManager requestManager, LogManager logger) {
		super();
		
		if(langService == null || requestManager == null || urlService == null
				|| logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.langService = langService;
		this.requestManager = requestManager;
		this.urlService = urlService;
		this.logger = logger;
	}
		
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Fixes Tomcat's bug because ISO-Latin1 while dispatching a view (see project's document)
		request.setCharacterEncoding("UTF-8");

		//get accept language
		String acceptLang = requestManager.getRequestLanguage(request);
		
		//get current visit from HTTP session
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		//check if user asked to change the navigation language
		String paramLang = requestManager.getParameter(request,Parameter.CHANGE_LANGUAGE);

		Language userLang = null;
		List<Language> langs = null;
		
		try{
			//get user's language and list of available languages 
			userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
			langs = langService.getAllLanguages();
		}
		catch(Throwable t){
			logger.error(t, "Error asking for available languages.");			
		}
		
		User user = null;		

		//get user from visit
		if(visit != null){		
			user = visit.getUser();
		}
		
		//get host where the app is running
		String host = urlService.getHost();
		
		//Store model data to the request to be displayed on the view
		requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);
		requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, userLang);
		requestManager.setAttribute(request, Attribute.USER, user);
		requestManager.setAttribute(request, Attribute.STRING_HOST, host);

		//get last status code
		Object sc = requestManager.getAttribute(request, Attribute.INT_ERROR_STATUS_CODE, Object.class);
		int statusCode = -1;
		if(sc != null){
			statusCode = (int)sc;
		}
		
		//get last exception
		Throwable exception = requestManager.getAttribute(request, Attribute.THROWABLE_ERROR_EXCEPTION, Throwable.class);
		
		if(exception!=null){
			//save exception details for the view
			requestManager.setAttribute(request, Attribute.STRING_ERROR_EXCEPTION_NAME, exception.getClass().getSimpleName());
			logger.error(exception, "");			
		}
		
		if(statusCode>0){
			//save status code for the view
			logger.info("Status Code: " + statusCode + " ##"+request.getMethod()+"##"+request.getRequestURI()+"?"+request.getParameterMap());
			requestManager.setAttribute(request, Attribute.STRING_ERROR_STATUS_CODE, statusCode);
		}
		
		//forward to the view
		request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);		
	}
	
}
