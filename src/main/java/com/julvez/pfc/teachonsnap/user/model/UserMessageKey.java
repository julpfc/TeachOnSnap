package com.julvez.pfc.teachonsnap.user.model;

/**
 * Enumeration with the keys for localized messages related with users
 */
public enum UserMessageKey {
	
	/** Subject to notify password change */
	CHANGE_PASSWORD_SUBJECT("user.changepassword.subject"),
	
	/** Message body to notify password change */
	CHANGE_PASSWORD_MESSAGE("user.changepassword.message");
	
	/** Real message key on the messages properties file */
	private final String realName;
 
	private UserMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
