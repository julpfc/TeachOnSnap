package com.julvez.pfc.teachonsnap.user.model;

/**
 * Enumeration with the properties names related to users.
 * <p>
 * To be used on the aplication properties file.
 */
public enum UserPropertyName {
	
	/** List of mail server domains which have identity verification */
	VERIFIED_EMAIL_DOMAINS("user.verified.emails.domains"),
	
	/**	Pagination limit for users. Max number of users in a page. */
	MAX_PAGE_RESULTS("admin.user.max.page.results");
	
	/** Real property name on the properties file */	 		
	private final String realName;
 
	private UserPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
