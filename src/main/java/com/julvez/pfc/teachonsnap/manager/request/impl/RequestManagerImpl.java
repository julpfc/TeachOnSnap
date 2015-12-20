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
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

public class RequestManagerImpl implements RequestManager {

	private static final String HTTP_HEADER_CONTENT_DISPOSITION = "content-disposition";
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	
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
	public String getBlankParameter(HttpServletRequest request, Enum<?> parameter) {
		return request.getParameter(parameter.toString());
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

	@Override
	public void setFileMetadataHeaders(HttpServletResponse response, String contentType, String fileName) {
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		response.setHeader(HTTP_HEADER_CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"");		
	}

	@Override
	public void downloadFile(HttpServletResponse response, String contentType, String fileName, InputStream input) {
		if(contentType != null && fileName != null && input!=null && response!=null){
		
			try {        
				setFileMetadataHeaders(response, contentType, fileName);
	
	            OutputStream output = response.getOutputStream();
	            byte[] buffer = new byte[1024*10];
	
	            for (int length = 0; (length = input.read(buffer)) > 0;) {
	                output.write(buffer, 0, length);
	            }
	
	            output.close();
	            input.close();
			}
			catch (Throwable t) {
				logger.error(t, "Error descargando fichero CSV");    				
			}
		}			
	}

}
