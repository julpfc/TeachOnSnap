package com.julvez.pfc.teachonsnap.url.model;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/"), 
	LESSONS_BY_LAST("/last/"),
	LESSON_DRAFTS_BY_USER("/drafts/"), 
	LESSON("/lesson/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
}
