package com.julvez.pfc.teachonsnap.manager.request;

import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface RequestManager {

	public static final String HTTP_ACCEPT_LANG 		= "accept-language";
	
	public static final String SESSION_IDLANGUAGE 		= "idLanguage";
	public static final String SESSION_USER				= "user";
	
	public static final String PARAM_CHANGE_LANGUAGE 	= "changeLang";
	
	
	String getAcceptLanguage(HttpServletRequest request);

	short getSessionIdLanguage(HttpServletRequest request);

	String getParamChangeLanguage(HttpServletRequest request);

	User getSessionUser(HttpServletRequest request);

	void setUserSessionLanguage(HttpServletRequest request, Language userLang);

	
}
