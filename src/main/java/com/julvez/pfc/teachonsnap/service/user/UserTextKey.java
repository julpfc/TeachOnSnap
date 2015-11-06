package com.julvez.pfc.teachonsnap.service.user;

public enum UserTextKey {
	CHANGE_PASSWORD_SUBJECT("user.changepassword.subject"),
	CHANGE_PASSWORD_SUBJECT_MESSAGE("user.changepassword.message");
	
	private final String realName;
 
	private UserTextKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
