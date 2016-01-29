package com.julvez.pfc.teachonsnap.manager.request.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Implementation of the RequestManager, uses an internal {@link LogManager} 
 * to log the errors and {@link StringManager} to manipulate strings.
 */
public class RequestManagerImpl implements RequestManager {

	/** HTTP content-disposition header */
	private static final String HTTP_HEADER_CONTENT_DISPOSITION = "content-disposition";
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
		
	/**
	 * Constructor requires all parameters not to be null
	 * @param stringManager String manager providing string manipulation utilities
	 * @param logger Log manager providing logging capabilities
	 */
	public RequestManagerImpl(StringManager stringManager, LogManager logger) {
		if(stringManager == null || logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.stringManager = stringManager;
		this.logger = logger;
	}
	
	@Override
	public String[] splitParamsFromControllerURI(HttpServletRequest request) {
		String[] params = null;
		
		//get URI and remove controller mapping
		String req = request.getRequestURI().replaceFirst(request.getServletContext().getContextPath(),"").replaceFirst(request.getServletPath()+"/", "");
		
		//split params if present
		if(req.contains("/")){
			params = req.split("/");
		}
		else if(!stringManager.isEmpty(req))
			params = new String[]{req};		
		
		return params;
	}

	@Override
	public String getParameter(HttpServletRequest request, Enum<?> parameter) {
		//get parameter from request
		String param = request.getParameter(parameter.toString());
		//check if empty
		if(stringManager.isEmpty(param)){
			param = null;
		}
		return param;
	}

	@Override
	public String getBlankParameter(HttpServletRequest request, Enum<?> parameter) {
		//get parameter from request
		return request.getParameter(parameter.toString());
	}

	
	@Override
	public boolean getBooleanParameter(HttpServletRequest request, Enum<?> parameter) {
		//get parameter from request
		String param = getParameter(request, parameter);
		
		//check if it's true
		return stringManager.isTrue(param);
	}


	@Override
	public int getNumericParameter(HttpServletRequest request, Enum<?> parameter) {
		int numericParam = -1;
		//get parameter from request
		String param = getParameter(request, parameter);
		
		//check if it's numeric and parse
		if(stringManager.isNumeric(param)){
			numericParam = Integer.parseInt(param);			
		}
		return numericParam;
	}

	@Override
	public List<String> getParameterList(HttpServletRequest request, Enum<?> parameter) {
		List<String> list = null;
		
		if(request.getParameterMap() != null){
			//get parameter map from request
			String[] parameters = request.getParameterMap().get(parameter.toString());
			
			if(parameters != null && parameters.length>0){
				list = new ArrayList<String>();
				//get list of values
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
			//Get part file name
	        for (String cd : part.getHeader(HTTP_HEADER_CONTENT_DISPOSITION).split(";")) {
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
		//get locale
		Locale locale = request.getLocale();
		
		//get language
		if(locale!=null && !stringManager.isEmpty(locale.getLanguage())){
			lang = locale.getLanguage();
		}
		return lang;
	}

	@Override
	public String getIP(HttpServletRequest request) {
		//get IP from request
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
		if(request != null && attribute!= null && returnClass != null){
			return (T)request.getAttribute(attribute.toString());
		}
		else return null;
	}

	@Override
	public void setAttribute(HttpServletRequest request, Enum<?> attribute,	Object attrib) {
		if(attribute != null && attribute != null && attrib != null){
			request.setAttribute(attribute.toString(), attrib);
		}
	}

	@Override
	public void setFileMetadataHeaders(HttpServletResponse response, String contentType, String fileName) {
		//set file metadata headers
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		response.setHeader(HTTP_HEADER_CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"");		
	}

	@Override
	public void downloadFile(HttpServletResponse response, String contentType, String fileName, InputStream input) {
		if(contentType != null && fileName != null && input!=null && response!=null){
		
			try {       
				//set file metadata headers
				setFileMetadataHeaders(response, contentType, fileName);
	
				//get output stream
	            OutputStream output = response.getOutputStream();
	            byte[] buffer = new byte[1024*10];
	
	            //write file
	            for (int length = 0; (length = input.read(buffer)) > 0;) {
	                output.write(buffer, 0, length);
	            }
	
	            //close streams
	            output.close();
	            input.close();
			}
			catch (Throwable t) {
				logger.error(t, "Error downloading file: " + fileName );    				
			}
		}			
	}

	@Override
	public String getSessionID(HttpServletRequest request) {
		return request.getSession(true).getId().substring(0,10);
	}

}
