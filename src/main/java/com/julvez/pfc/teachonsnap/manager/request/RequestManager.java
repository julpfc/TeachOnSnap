package com.julvez.pfc.teachonsnap.manager.request;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public interface RequestManager {

	public <T> T getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute, Class<T> returnClass);
	public String getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute);
	
	public void setSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute, Object attribute);
	
	
	public String[] splitParamsFromControllerURI(HttpServletRequest request);
		
	public String getRequestLanguage(HttpServletRequest request);
	public String getIP(HttpServletRequest request);
	
	public String getParameter(HttpServletRequest request, Enum<?> parameter);
	
	public boolean getBooleanParameter(HttpServletRequest request, Enum<?> parameter);
	public int getNumericParameter(HttpServletRequest request, Enum<?> parameter);
	public List<String> getParameterList(HttpServletRequest request, Enum<?> parameter);
	public String getBlankParameter(HttpServletRequest request, Enum<?> parameter);
	
	public <T> T getAttribute(HttpServletRequest request, Enum<?> attribute, Class<T> returnClass);
	public void setAttribute(HttpServletRequest request, Enum<?> attribute, Object attrib);
	
	public String getPartFilename(Part part);
	
	public void setFileMetadataHeaders(HttpServletResponse response, String contentType, String fileName);
	
	public void downloadFile(HttpServletResponse response, String contentType, String fileName, InputStream input);	
	
}
