package com.julvez.pfc.teachonsnap.service.url;

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
