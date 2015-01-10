package com.julvez.pfc.teachonsnap.manager.property;

public enum PropertyName {
	TEACHONSNAP_HOST("teachonsnap.host");
	
	private final String realName;
 
	private PropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
