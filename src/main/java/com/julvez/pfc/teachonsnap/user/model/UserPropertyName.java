package com.julvez.pfc.teachonsnap.user.model;

public enum UserPropertyName {

	VERIFIED_EMAIL_DOMAINS("user.verified.emails.domains"), 
	MAX_PAGE_RESULTS("admin.user.max.page.results");
	
	 		
	private final String realName;
 
	private UserPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
