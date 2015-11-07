package com.julvez.pfc.teachonsnap.url.model;

public enum URLPropertyName {
	
	TEACHONSNAP_HOST("teachonsnap.host");
	
	private final String realName;
 
	private URLPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
