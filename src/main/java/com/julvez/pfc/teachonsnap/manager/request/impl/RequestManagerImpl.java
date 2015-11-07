package com.julvez.pfc.teachonsnap.manager.request.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.impl.domain.Header;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class RequestManagerImpl implements RequestManager {

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public String[] splitParamsFromControllerURI(HttpServletRequest request) {
		String[] params = null;
		String req = request.getRequestURI().replaceFirst(request.getServletPath()+"/", "");
				
		if(req.contains("/")){
			params = req.split("/");
		}
		else if(!stringManager.isEmpty(req))
			params = new String[]{req};		
		
		return params;
	}

	@Override
	public String getParameter(HttpServletRequest request, Enum<?> parameter) {
		String param = request.getParameter(parameter.toString());
		if(stringManager.isEmpty(param)){
			param = null;
		}
		return param;
	}
	
	@Override
	public boolean getBooleanParameter(HttpServletRequest request, Enum<?> parameter) {
		String param = getParameter(request, parameter);
		return stringManager.isTrue(param);
	}


	@Override
	public int getNumericParameter(HttpServletRequest request, Enum<?> parameter) {
		int numericParam = -1;
		String param = getParameter(request, parameter);
		if(stringManager.isNumeric(param)){
			numericParam = Integer.parseInt(param);			
		}
		return numericParam;
	}

	@Override
	public List<String> getParameterList(HttpServletRequest request, Enum<?> parameter) {
		List<String> list = null;
		
		if(request.getParameterMap() != null){
			String[] parameters = request.getParameterMap().get(parameter.toString());
			
			if(parameters != null && parameters.length>0){
				list = new ArrayList<String>();
				for(String param:parameters){
					if(!stringManager.isEmpty(param)){
						list.add(param);
					}
				}
			}
		}
		
		return list;
	}

	
	@Override
	public String getPartFilename(Part part) {
		String filename = null;
	        for (String cd : part.getHeader(Header.CONTENT_DISPOSITION.toString()).split(";")) {
	            if (cd.trim().startsWith("filename")) {
	                filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	                filename = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
	            }
	        }
	        return filename;
	    }




	@Override
	public String getRequestLanguage(HttpServletRequest request) {
		String lang = null;
		Locale locale = request.getLocale();
		if(locale!=null && !stringManager.isEmpty(locale.getLanguage())){
			lang = locale.getLanguage();
		}
		return lang;
	}

	@Override
	public String getIP(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		if(stringManager.isEmpty(ip)){
			ip = null;
		}
		
		return ip;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute, Class<T> returnClass) {
		return (T)request.getSession(true).getAttribute(sessionAttribute.toString());
	}

	@Override
	public String getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute) {
		return getSessionAttribute(request, sessionAttribute, String.class);
	}

	@Override
	public void setSessionAttribute(HttpServletRequest request,	Enum<?> sessionAttribute, Object attribute) {
		if(sessionAttribute != null && attribute != null){
			request.getSession(true).setAttribute(sessionAttribute.toString(), attribute);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(HttpServletRequest request, Enum<?> attribute, Class<T> returnClass) {
		//TODO verificar si lo que llega es null si no peta
		return (T)request.getAttribute(attribute.toString());
	}

	@Override
	public void setAttribute(HttpServletRequest request, Enum<?> attribute,	Object attrib) {
		if(attribute != null && attrib != null){
			request.setAttribute(attribute.toString(), attrib);
		}
	}

}
