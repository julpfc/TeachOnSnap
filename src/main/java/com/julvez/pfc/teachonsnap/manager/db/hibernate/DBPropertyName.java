package com.julvez.pfc.teachonsnap.manager.db.hibernate;

/**
 * Enumeration with the properties names related to DB & Hibernate.
 * <p>
 * To be used on the aplication properties file.
 */
public enum DBPropertyName {

	/** Hibernate DB password */
	HIBERNATE_DB_PASSWORD("hibernate.connection.password");
	
	/** Real property name on the properties file */ 		
	private final String realName;
 
	private DBPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
