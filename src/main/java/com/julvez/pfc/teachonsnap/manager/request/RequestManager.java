package com.julvez.pfc.teachonsnap.manager.request;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/** Provides {@link HttpServletRequest} and {@link HttpServletResponse} access/manipulation utilities */
public interface RequestManager {

	/**
	 * Retrieves a session attribute from request and casts it to a specific class instance.
	 * @param request Request
	 * @param sessionAttribute Session key to get the attribute. Enumeration.
	 * @param returnClass Class to cast the object
	 * @return the session attribute if present, null otherwise
	 */
	public <T> T getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute, Class<T> returnClass);

	/**
	 * Retrieves a string session attribute from request.
	 * @param request Request
	 * @param sessionAttribute Session key to get the attribute. Enumeration.
	 * @return the session attribute if present, null otherwise
	 */
	public String getSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute);
	
	/**
	 * Stores an object at request's HTTP session
	 * @param request Request
	 * @param sessionAttribute Session key to get the attribute. Enumeration.
	 * @param attribute Object to store
	 */
	public void setSessionAttribute(HttpServletRequest request, Enum<?> sessionAttribute, Object attribute);
	
	/**
	 * Extracts relative Controller's URI and splits the '/'-separated params
	 * @param request Request
	 * @return String array with splitted params, null if error
	 */
	public String[] splitParamsFromControllerURI(HttpServletRequest request);
		
	/**
	 * Extracts accept language from request
	 * @param request Request
	 * @return accept language if present, null otherwise
	 */
	public String getRequestLanguage(HttpServletRequest request);
	
	/**
	 * Extracts Internet Protocol (IP) address from request 
	 * @param request Request
	 * @return IP address if present, null otherwise
	 */
	public String getIP(HttpServletRequest request);
	
	/**
	 * Returns the value for the specified request's parameter
	 * @param request Request
	 * @param parameter Parameter name to get the value. Enumeration.
	 * @return Parameter's value if present and not empty, null otherwise
	 */
	public String getParameter(HttpServletRequest request, Enum<?> parameter);
	
	/**
	 * Returns the boolean value for the specified request's parameter
	 * @param request Request
	 * @param parameter Parameter name to get the value. Enumeration.
	 * @return true if the parameter's value is "true" (in any case) or
	 * a positive integer (>0).
	 */
	public boolean getBooleanParameter(HttpServletRequest request, Enum<?> parameter);
	
	/**
	 * Returns the numeric value for the specified request's parameter
	 * @param request Request
	 * @param parameter Parameter name to get the value. Enumeration.
	 * @return Parameter's numeric value if present, -1 otherwise
	 */
	public int getNumericParameter(HttpServletRequest request, Enum<?> parameter);
	
	/**
	 * Returns a list of values for the specified request's parameter
	 * @param request Request
	 * @param parameter Parameter name to get the value. Enumeration.
	 * @return Parameter's list of values if present, null otherwise
	 */
	public List<String> getParameterList(HttpServletRequest request, Enum<?> parameter);
	
	/**
	 * Returns the value for the specified request's parameter even if it's blank.
	 * @param request Request
	 * @param parameter Parameter name to get the value. Enumeration.
	 * @return Parameter's value if present, null otherwise
	 */
	public String getBlankParameter(HttpServletRequest request, Enum<?> parameter);
	
	/**
	 * Retrieves a request attribute and casts it to a specific class instance.
	 * @param request Request
	 * @param attribute Key to get the attribute. Enumeration.
	 * @param returnClass Class to cast the object
	 * @return the request attribute if present, null otherwise
	 */
	public <T> T getAttribute(HttpServletRequest request, Enum<?> attribute, Class<T> returnClass);
	
	/**
	 * Stores an object at request's attributes
	 * @param request Request
	 * @param attribute Key to get the attribute. Enumeration.
	 * @param attrib Object to store
	 */
	public void setAttribute(HttpServletRequest request, Enum<?> attribute, Object attrib);
	
	/**
	 * Returns the Part file name
	 * @param part Part from a multipart form POST
	 * @return Part's file name, null if error
	 */
	public String getPartFilename(Part part);
	
	/**
	 * Sets the file metadata headers on the response
	 * @param response Response
	 * @param contentType File's content-type
	 * @param fileName File's name
	 */
	public void setFileMetadataHeaders(HttpServletResponse response, String contentType, String fileName);
	
	/**
	 * Downloads the specified stream into the response (user's device)
	 * @param response Response
	 * @param contentType File's content-type
	 * @param fileName File's name
	 * @param input File's stream
	 */
	public void downloadFile(HttpServletResponse response, String contentType, String fileName, InputStream input);
	
	/**
	 * Returns the request's HTTP session ID
	 * @param request Request
	 * @return request's HTTP session ID
	 */
	public String getSessionID(HttpServletRequest request);
}
