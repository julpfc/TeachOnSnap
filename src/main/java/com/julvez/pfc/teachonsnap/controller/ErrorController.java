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
import com.julvez.pfc.teachonsnap.user.model.User;

public class ErrorController extends HttpServlet{

	private static final long serialVersionUID = 5157231483305341228L;
	
	private RequestManager requestManager = RequestManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	
	private LangService langService = LangServiceFactory.getService();
	private URLService urlService = URLServiceFactory.getService();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String acceptLang = requestManager.getRequestLanguage(request);
		
		Visit visit = requestManager.getSessionAttribute(request, SessionAttribute.VISIT, Visit.class);

		String paramLang = requestManager.getParameter(request,Parameter.CHANGE_LANGUAGE);

		Language userLang = null;
		List<Language> langs = null;
		
		try{
			userLang = langService.getUserSessionLanguage(acceptLang,visit,paramLang);
			langs = langService.getAllLanguages();

		}
		catch(Throwable t){
			logger.error(t, "Error consultando idiomas disponibles.");			
		}
		
		User user = null;
		
		if(visit != null){		
			user = visit.getUser();
		}
		
		String host = urlService.getHost();
		
		requestManager.setAttribute(request, Attribute.LIST_LANGUAGES, langs);
		requestManager.setAttribute(request, Attribute.LANGUAGE_USERLANGUAGE, userLang);
		requestManager.setAttribute(request, Attribute.USER, user);
		requestManager.setAttribute(request, Attribute.STRING_HOST, host);
							
		Object sc = requestManager.getAttribute(request, Attribute.INT_ERROR_STATUS_CODE, Object.class);
		int statusCode = -1;
		
		if(sc != null){
			statusCode = (int)sc;
		}
		
		Throwable exception = requestManager.getAttribute(request, Attribute.THROWABLE_ERROR_EXCEPTION, Throwable.class);
		
		if(exception!=null){			
			requestManager.setAttribute(request, Attribute.STRING_ERROR_EXCEPTION_NAME, exception.getClass().getSimpleName());
			logger.error(exception, "");			
		}
		
		if(statusCode>0){
			logger.info("Status Code: " + statusCode + " ##"+request.getMethod()+"##"+request.getRequestURI()+"?"+request.getParameterMap());
			requestManager.setAttribute(request, Attribute.STRING_ERROR_STATUS_CODE, statusCode);
		}
		
		request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);		
	}
	
}
