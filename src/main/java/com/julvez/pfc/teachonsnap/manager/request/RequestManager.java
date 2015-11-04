package com.julvez.pfc.teachonsnap.manager.request;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.julvez.pfc.teachonsnap.model.error.ErrorBean;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
import com.julvez.pfc.teachonsnap.model.visit.Visit;


public interface RequestManager {

	public Visit getSessionVisit(HttpServletRequest request);
	public ErrorBean getErrorSession(HttpServletRequest request);
	public String getLastPage(HttpServletRequest request);

	public void setVisitSession(HttpServletRequest request, Visit visit);
	public void setErrorSession(HttpServletRequest request, ErrorBean error);
	public void setLastPage(HttpServletRequest request);
	public void setAttributeErrorBean(HttpServletRequest request, ErrorBean error);

	public String getRequestLanguage(HttpServletRequest request);
	public String getIP(HttpServletRequest request);

	public String[] getControllerParams(HttpServletRequest request);

	public String getParamChangeLanguage(HttpServletRequest request);

	public boolean getParamLogout(HttpServletRequest request);
	public String getParamLoginEmail(HttpServletRequest request);
	public String getParamLoginPassword(HttpServletRequest request);
	public String getParamLoginEmailRemind(HttpServletRequest request);
	
	public String getParamJSON(HttpServletRequest request);
	public String getParamExport(HttpServletRequest request);

	public Lesson getParamNewLesson(Map<String, String[]> parameterMap);
	public List<String> getParamNewTags(Map<String, String[]> parameterMap);
	public List<String> getParamNewSources(Map<String, String[]> parameterMap);
	public List<String> getParamNewMoreInfos(Map<String, String[]> parameterMap);

	public String getParamComment(HttpServletRequest request);
	public int getParamCommentID(HttpServletRequest request);
	public boolean getParamEditComment(HttpServletRequest request);
	public String getParamBanComment(HttpServletRequest request);

	public String getParamPublishLessonTest(HttpServletRequest request);
	public String getParamDeleteTest(HttpServletRequest request);
	public int getParamDeleteQuestionID(HttpServletRequest request);	
	public int getParamQuestionPriority(HttpServletRequest request);
	public int getParamQuestionID(HttpServletRequest request);
	public String getParamMultiplechoiceTest(HttpServletRequest request);
	public int getParamNumAnswersTest(HttpServletRequest request);

	public String getParamfirstName(HttpServletRequest request);
	public String getParamlastName(HttpServletRequest request);
	public String getParamOldPassword(HttpServletRequest request);
	public String getParamNewPassword(HttpServletRequest request);

	public int getAttributeErrorStatusCode(HttpServletRequest request);
	public Throwable getAttributeErrorException(HttpServletRequest request);
	
	public List<FileMetadata> getUploadFiles(HttpServletRequest request);
	public FileMetadata getSubmittedFile(HttpServletRequest request);
	
}
