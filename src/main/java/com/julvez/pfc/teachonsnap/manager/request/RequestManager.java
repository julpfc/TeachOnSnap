package com.julvez.pfc.teachonsnap.manager.request;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.user.User;


public interface RequestManager {

	String getAcceptLanguage(HttpServletRequest request);

	short getSessionIdLanguage(HttpServletRequest request);
	User getSessionUser(HttpServletRequest request);
	ErrorType getErrorSession(HttpServletRequest request);
	String getLastPage(HttpServletRequest request);

	void setUserSessionLanguage(HttpServletRequest request, Language userLang);
	void setUserSession(HttpServletRequest request, User user);
	void setErrorSession(HttpServletRequest request, ErrorType error);
	void setLastPage(HttpServletRequest request);

	String getParamChangeLanguage(HttpServletRequest request);
	boolean getParamLogout(HttpServletRequest request);
	String getParamLoginEmail(HttpServletRequest request);
	String getParamLoginPassword(HttpServletRequest request);
	Lesson getParamNewLesson(Map<String, String[]> parameterMap);
	List<String> getParamNewTags(Map<String, String[]> parameterMap);
	List<String> getParamNewSources(Map<String, String[]> parameterMap);
	List<String> getParamNewMoreInfos(Map<String, String[]> parameterMap);

	String[] getControllerParams(HttpServletRequest request);
	
	List<FileMetadata> getUploadFiles(HttpServletRequest request);
	FileMetadata getSubmittedFile(HttpServletRequest request);

	
}
