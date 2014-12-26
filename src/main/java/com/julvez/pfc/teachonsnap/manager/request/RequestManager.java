package com.julvez.pfc.teachonsnap.manager.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;


public interface RequestManager {

	public static final String HTTP_ACCEPT_LANG 		= "accept-language";
	
	public static final String SESSION_IDLANGUAGE 		= "idLanguage";
	public static final String SESSION_USER				= "user";
	public static final String SESSION_ERROR			= "error";
	public static final String SESSION_LAST_PAGE		= "lastPage";
	
	public static final String PARAM_CHANGE_LANGUAGE 	= "changeLang";
	public static final String PARAM_LOGIN_EMAIL 		= "email";
	public static final String PARAM_LOGIN_PASSWORD		= "password";
	public static final String PARAM_LOGOUT				= "logout";
	
	
	String getAcceptLanguage(HttpServletRequest request);

	short getSessionIdLanguage(HttpServletRequest request);

	String getParamChangeLanguage(HttpServletRequest request);

	User getSessionUser(HttpServletRequest request);

	void setUserSessionLanguage(HttpServletRequest request, Language userLang);
	
	String[] getControllerParams(HttpServletRequest request);

	String getParamLoginEmail(HttpServletRequest request);

	String getParamLoginPassword(HttpServletRequest request);

	void setUserSession(HttpServletRequest request, User user);

	void setErrorSession(HttpServletRequest request, ErrorType error);
	
	ErrorType getErrorSession(HttpServletRequest request);

	void setLastPage(HttpServletRequest request);
	String getLastPage(HttpServletRequest request);

	boolean getParamLogout(HttpServletRequest request);
	
	List<FileMetadata> getUploadFiles(HttpServletRequest request);
	
}
