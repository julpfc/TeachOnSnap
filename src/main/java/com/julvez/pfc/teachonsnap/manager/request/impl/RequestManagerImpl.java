package com.julvez.pfc.teachonsnap.manager.request.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.error.ErrorType;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.upload.FileMetadata;
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
		request.getSession(true).setAttribute(SESSION_USER, user);
	}

	@Override
	public void setErrorSession(HttpServletRequest request, ErrorType error) {
		if(error!=null){
			request.getSession(true).setAttribute(SESSION_ERROR, error);			
		}			
	}

	@Override
	public ErrorType getErrorSession(HttpServletRequest request) {
		Object obj = request.getSession(true).getAttribute(SESSION_ERROR);
		if(obj!=null)
			return (ErrorType)obj;
		else return ErrorType.ERR_NONE;
	}

	@Override
	public void setLastPage(HttpServletRequest request) {
		request.getSession(true).setAttribute(SESSION_LAST_PAGE, request.getRequestURI());		
	}

	@Override
	public String getLastPage(HttpServletRequest request) {
		return (String) request.getSession(true).getAttribute(SESSION_LAST_PAGE);				
	}

	@Override
	public boolean getParamLogout(HttpServletRequest request) {
		String param = getParam(request,PARAM_LOGOUT);
		return stringManager.isTrue(param);
	}

	@Override
	public List<FileMetadata> getUploadFiles(HttpServletRequest request) {
		List<FileMetadata> files = new LinkedList<FileMetadata>();
		
		
	    Collection<Part> parts;
		try {
			parts = request.getParts();
			
			FileMetadata temp = null;
			
			for(Part part:parts){  
				if(part.getContentType() != null){
                
					temp = new FileMetadata();
					temp.setFileName(getFilename(part));
					temp.setFileSize(part.getSize()/1024 +" Kb");
					temp.setFileType(part.getContentType());
					temp.setContent(part.getInputStream());
					
					System.out.println("Upload.Part: "+temp);
					files.add(temp);
				}
			}
		 
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ServletException e) {
			
			e.printStackTrace();
		}
	 
		return files;
	}
	
	private String getFilename(Part part) {
		String filename = null;
	        for (String cd : part.getHeader("content-disposition").split(";")) {
	            if (cd.trim().startsWith("filename")) {
	                filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	                filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	            }
	        }
	        return filename;
	    }
	

}
