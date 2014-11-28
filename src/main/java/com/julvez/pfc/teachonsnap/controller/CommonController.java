package com.julvez.pfc.teachonsnap.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;


/**
 * Servlet implementation class CommonController
 */
public abstract class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected LangService langService = LangServiceFactory.getService();
	protected RequestManager requestManager = RequestManagerFactory.getManager();
		
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
		
		//TODO Revisar si es mejor sacarlo del Locale que del Accept a pelo
		//	System.out.println(request.getLocale().getLanguage());
		String acceptLang = requestManager .getAcceptLanguage(request);
		short sessionIdLang = requestManager.getSessionIdLanguage(request);				
		String paramLang = requestManager.getParamChangeLanguage(request);
		User user = requestManager.getSessionUser(request);
		
		Language userLang = langService.getUserSessionLanguage(acceptLang,sessionIdLang,paramLang,user);
		// TODO Actualizar usuario BBDD/Cache/Session
		requestManager.setUserSessionLanguage(request,userLang);
		
		request.setAttribute("userLang", userLang);
		request.setAttribute("user", user);
		
		//TODO Loguear la página en la que estamos	    
	    processController(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected abstract void processController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
