package com.julvez.pfc.teachonsnap.url.model;

/**
 * Enumeration with the properties names related to URLs.
 * <p>
 * To be used on the aplication properties file.
 */

public enum URLPropertyName {
		
	/**	Application host, may include port */
	TEACHONSNAP_HOST("teachonsnap.host"),
	
	/**	Application protocol (http, https, ...) */
	TEACHONSNAP_PROTOCOL("teachonsnap.protocol");
	
	private final String realName;
 
	private URLPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
