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
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.stats.model.StatsPropertyName;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;


/**
 * Servlet implementation class CommonController
 */
public abstract class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected LangService langService = LangServiceFactory.getService();
	protected UserService userService = UserServiceFactory.getService();	
	protected StatsService statsService = StatsServiceFactory.getService();
	protected URLService urlService = URLServiceFactory.getService();
	
	protected RequestManager requestManager = RequestManagerFactory.getManager();
	protected LogManager logger = LogManagerFactory.getManager();
	protected PropertyManager properties = PropertyManagerFactory.getManager();
	
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommonController() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.addPrefix(this.getClass().getSimpleName());

		//TODO Revisar si lo ponemos en más sitios
		request.setCharacterEncoding("UTF-8");
		
		String acceptLang = requestManager.getRequestLanguage(request);
		
		//TODO revisar todos los métodos control interno de errores
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		if(visit == null){			
			if(properties.getBooleanProperty(StatsPropertyName.ENABLE_ANON_VISIT_COUNTER)){
				visit = statsService.createVisit(requestManager.getIP(request));
				requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);						
			}
		}
		
		String paramLang = requestManager.getParameter(request,Parameter.CHANGE_LANGUAGE);
		Language userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
		List<Language> langs = langService.getAllLanguages();

		
		User user = null;
		
		if(visit != null){
		
			visit.setIdLanguage(userLang.getId());
			requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);

			user = visit.getUser();				
		
			if(user!=null && user.getLanguage().getId() != userLang.getId()){
				User modUser = userService.saveUserLanguage(user, userLang);
				if(modUser!=null){				
					visit.setUser(modUser);
					requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);
				}
			}
		}
		
		String host = urlService.getHost();
		
		requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);
		requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, userLang);
		requestManager.setAttribute(request, Attribute.USER, user);
		requestManager.setAttribute(request, Attribute.STRING_HOST, host);
		
		
		// Si es zona restringida pedimos login
		if(user==null && isPrivateZone()){
			setErrorSession(request, ErrorType.ERR_LOGIN, ErrorMessageKey.NONE);
			String lastPage = urlService.getHomeURL();
			response.sendRedirect(lastPage);
		}
		else{
			ErrorBean error = getErrorSession(request);
			
			setAttributeErrorBean(request, error);
						
			setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.NONE);
			
			logger.info("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

			processController(request, response, visit, user);
			
			//Guardamos la página después d eprocesarla para tener acceso a la página anterior
			requestManager.setSessionAttribute(request, SessionAttribute.LAST_PAGE, request.getRequestURI());
		}
		logger.removePrefix();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected abstract void processController(HttpServletRequest request, HttpServletResponse response, Visit visit, User user) throws ServletException, IOException;
	
	protected abstract boolean isPrivateZone();
	
	
	
	private ErrorBean getErrorSession(HttpServletRequest request) {
		ErrorBean error = requestManager.getSessionAttribute(request, SessionAttribute.ERROR, ErrorBean.class);
		
		if(error == null){
			error = new ErrorBean(ErrorType.ERR_NONE,ErrorMessageKey.NONE);
		}
		return error;
	}
	
	
	protected void setAttributeErrorBean(HttpServletRequest request, ErrorBean error) {
		if(error!=null){		
			switch(error.getType()){
				case ERR_LOGIN:
					requestManager.setAttribute(request, Attribute.STRING_LOGINERROR, "loginError");
					break;
				case ERR_NONE:
					if(error.getMessageKey()!=null){
						requestManager.setAttribute(request, Attribute.STRING_ERRORMESSAGEKEY, error.getMessageKey());
					}
					break;
				default:
					if(error.getMessageKey()!=null){
						requestManager.setAttribute(request, Attribute.STRING_ERRORMESSAGEKEY, error.getMessageKey());
					}
					requestManager.setAttribute(request, Attribute.STRING_ERRORTYPE, error.getType().toString());
					break;				
			}
		}
	}
	
	protected void setErrorSession(HttpServletRequest request, ErrorType type, ErrorMessageKey messageKey){
		requestManager.setSessionAttribute(request, SessionAttribute.ERROR, new ErrorBean(type, messageKey));
	}

	
}

