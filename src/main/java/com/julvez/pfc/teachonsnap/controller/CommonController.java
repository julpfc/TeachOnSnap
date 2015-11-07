package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyName;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.error.ErrorMessageKey;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.role.RoleService;
import com.julvez.pfc.teachonsnap.service.role.RoleServiceFactory;
import com.julvez.pfc.teachonsnap.service.url.Attribute;
import com.julvez.pfc.teachonsnap.service.url.Parameter;
import com.julvez.pfc.teachonsnap.service.url.SessionAttribute;
import com.julvez.pfc.teachonsnap.service.url.URLService;
import com.julvez.pfc.teachonsnap.service.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.service.visit.VisitService;
import com.julvez.pfc.teachonsnap.service.visit.VisitServiceFactory;


/**
 * Servlet implementation class CommonController
 */
public abstract class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected LangService langService = LangServiceFactory.getService();
	protected UserService userService = UserServiceFactory.getService();
	protected RoleService roleService = RoleServiceFactory.getService();
	protected VisitService visitService = VisitServiceFactory.getService();
	protected URLService requestService = URLServiceFactory.getService();
	
	protected RequestManager requestManager = RequestManagerFactory.getManager();
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
		//TODO Revisar si lo ponemos en más sitios
		request.setCharacterEncoding("UTF-8");
		
		String acceptLang = requestManager.getRequestLanguage(request);
		
		//TODO revisar todos los métodos control interno de errores
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		if(visit == null){			
			if(properties.getBooleanProperty(PropertyName.ENABLE_ANON_VISIT_COUNTER)){
				visit = visitService.createVisit(requestManager.getIP(request));
				requestManager.setSessionAttribute(request, SessionAttribute.VISIT, visit);						
			}
		}
		
		String paramLang = requestManager.getParameter(request,Parameter.CHANGE_LANGUAGE);
		Language userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
		
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
		
		String host = requestService.getHost();
		
		request.setAttribute(Attribute.LANGUAGE_USERLANGUAGE.toString(), userLang);
		request.setAttribute(Attribute.USER.toString(), user);
		request.setAttribute(Attribute.STRING_HOST.toString(), host);
		
		
		// Si es zona restringida pedimos login
		if(user==null && isPrivateZone()){
			setErrorSession(request, ErrorType.ERR_LOGIN, ErrorMessageKey.NONE);
			String lastPage = requestService.getHomeURL();
			response.sendRedirect(lastPage);
		}
		else{
			ErrorBean error = getErrorSession(request);
			
			setAttributeErrorBean(request, error);
						
			setErrorSession(request, ErrorType.ERR_NONE, ErrorMessageKey.NONE);
			
			//TODO Loguear la página en la que estamos	  
			System.out.println("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

			processController(request, response);
			
			//Guardamos la página después d eprocesarla para tener acceso a la página anterior
			requestManager.setSessionAttribute(request, SessionAttribute.LAST_PAGE, request.getRequestURI());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected abstract void processController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
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

