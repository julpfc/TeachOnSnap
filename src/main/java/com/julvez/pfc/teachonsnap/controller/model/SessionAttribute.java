package com.julvez.pfc.teachonsnap.controller.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Enumeration with the session atributes names used by the application.
 * <p>
 * Used to pass objects bewteen controllers within an HTTP session.
 * <p>
 * To be used on the {@link HttpSession} attributes.
 * @see HttpServletRequest#getSession()
 * @see HttpSession#setAttribute(String, Object)
 */
public enum SessionAttribute {
	
	/** Navigation language's id */
	IDLANGUAGE("idLanguage"),
	
	/** Visit describing the current session on the application */
	VISIT("visit"),
	
	/** Last error reference */
	ERROR("error"),
	
	/** Last page visited */
	LAST_PAGE("lastPage");

	/** Real attribute name on the HTTP session */		 		
	private final String realName;
 
	private SessionAttribute(String attributeName) {
		realName = attributeName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
