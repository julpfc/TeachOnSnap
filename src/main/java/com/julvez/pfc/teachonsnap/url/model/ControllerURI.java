package com.julvez.pfc.teachonsnap.url.model;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/"), 
	LESSONS_BY_LAST("/last/"),
	LESSON_DRAFTS_BY_USER("/drafts/"), 
	LESSON("/lesson/"), 
	LESSON_PREVIEW("/preview/"), 
	ADMIN_USER_MANAGER("/admin/users/"), 
	ADMIN_USER_PROFILE("/admin/user/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
}
