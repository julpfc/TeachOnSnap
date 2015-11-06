package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyName;
import com.julvez.pfc.teachonsnap.manager.request.Attribute;
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
import com.julvez.pfc.teachonsnap.service.request.RequestService;
import com.julvez.pfc.teachonsnap.service.request.RequestServiceFactory;
import com.julvez.pfc.teachonsnap.service.role.RoleService;
import com.julvez.pfc.teachonsnap.service.role.RoleServiceFactory;
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
	protected RequestService requestService = RequestServiceFactory.getService();
	
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
		Visit visit = requestManager.getSessionVisit(request);

		if(visit == null){			
			if(properties.getBooleanProperty(PropertyName.ENABLE_ANON_VISIT_COUNTER)){
				visit = visitService.createVisit(requestManager.getIP(request));
				requestManager.setVisitSession(request, visit);						
			}
		}
		
		String paramLang = requestManager.getParamChangeLanguage(request);
		Language userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
		
		User user = null;
		
		if(visit != null){
		
			visit.setIdLanguage(userLang.getId());
			requestManager.setVisitSession(request, visit);

			user = visit.getUser();				
		
			if(user!=null && user.getLanguage().getId() != userLang.getId()){
				User modUser = userService.saveUserLanguage(user, userLang);
				if(modUser!=null){				
					visit.setUser(modUser);
					requestManager.setVisitSession(request, visit);
				}
			}
		}
		
		String host = requestService.getHost();
		
		request.setAttribute(Attribute.LANGUAGE_USERLANGUAGE.toString(), userLang);
		request.setAttribute(Attribute.USER.toString(), user);
		request.setAttribute(Attribute.STRING_HOST.toString(), host);
		
		
		// Si es zona restringida pedimos login
		if(user==null && isPrivateZone()){
			requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_LOGIN, ErrorMessageKey.NONE));
			String lastPage = requestService.getHomeURL();
			response.sendRedirect(lastPage);
		}
		else{
			ErrorBean error = requestManager.getErrorSession(request);
			
			requestManager.setAttributeErrorBean(request, error);
						
			requestManager.setErrorSession(request, new ErrorBean(ErrorType.ERR_NONE, ErrorMessageKey.NONE));
			
			//TODO Loguear la página en la que estamos	  
			System.out.println("####"+request.getMethod()+"#####"+request.getRequestURI()+"?"+request.getParameterMap()+"#########"+this.getClass().getName());

			processController(request, response);
			
			//Guardamos la página después d eprocesarla para tener acceso a la página anterior
			requestManager.setLastPage(request);
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
}

