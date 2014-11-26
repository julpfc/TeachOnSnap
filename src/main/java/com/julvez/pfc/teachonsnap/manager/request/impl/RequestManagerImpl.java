package com.julvez.pfc.teachonsnap.manager.request.impl;

import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;

public class RequestManagerImpl implements RequestManager {

	StringManager stringManager=StringManagerFactory.getManager();
	
	@Override
	public String getAcceptLanguage(HttpServletRequest request) {
		String language = request.getHeader(HTTP_ACCEPT_LANG);		
		if(!stringManager.isEmpty(language)){
			language = language.substring(0,language.indexOf("-")==-1?0:language.indexOf("-"));
		}
		else language=null;
		return language;
	}

	@Override
	public short getSessionIdLanguage(HttpServletRequest request) {
		short id = -1;
		Short idlang = (Short)request.getSession(true).getAttribute(SESSION_IDLANGUAGE);
		if(idlang!=null) id=idlang.shortValue();
		return id;
	}

	@Override
	public User getSessionUser(HttpServletRequest request) {
		return (User)request.getSession(true).getAttribute(SESSION_USER);
	}

	@Override
	public void setUserSessionLanguage(HttpServletRequest request,
			Language userLang) {
		
		if(userLang!=null){
			short sessionIdLang = getSessionIdLanguage(request);
			
			if(sessionIdLang != userLang.getId()){
				request.getSession(true).setAttribute(SESSION_IDLANGUAGE, userLang.getId());
			}
		}		
	}

	@Override
	public String[] getControllerParams(HttpServletRequest request) {
		String[] params = null;
		String req = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
				
		if(req.contains("/")){
			params = req.split("/");
		}
		else if(!stringManager.isEmpty(req))
			params = new String[]{req};		
		
		return params;
	}

	
	private String getParam(HttpServletRequest request,String paramName) {
		String param = request.getParameter(paramName);
		if(stringManager.isEmpty(param)){
			param = null;
		}
		return param;
	}
	
	@Override
	public String getParamChangeLanguage(HttpServletRequest request) {
		return getParam(request,PARAM_CHANGE_LANGUAGE);		
	}	
	
	@Override
	public String getParamLoginEmail(HttpServletRequest request) {		
		return getParam(request,PARAM_LOGIN_EMAIL);
	}

	@Override
	public String getParamLoginPassword(HttpServletRequest request) {		
		return getParam(request,PARAM_LOGIN_PASSWORD);
	}

	@Override
	public void setUserSession(HttpServletRequest request, User user) {
		if(user!=null){
			request.getSession(true).setAttribute(SESSION_USER, user);			
		}	
	}

}
