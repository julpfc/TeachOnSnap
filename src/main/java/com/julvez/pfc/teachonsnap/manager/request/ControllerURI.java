package com.julvez.pfc.teachonsnap.manager.request;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
}
