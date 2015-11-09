package com.julvez.pfc.teachonsnap.user.model;

public enum UserMessageKey {
	CHANGE_PASSWORD_SUBJECT("user.changepassword.subject"),
	CHANGE_PASSWORD_MESSAGE("user.changepassword.message");
	
	private final String realName;
 
	private UserMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
