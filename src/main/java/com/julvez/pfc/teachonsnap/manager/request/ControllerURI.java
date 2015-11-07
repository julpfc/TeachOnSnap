package com.julvez.pfc.teachonsnap.manager.request;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/"), 
	LESSONS_BY_LAST("/last/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
}
