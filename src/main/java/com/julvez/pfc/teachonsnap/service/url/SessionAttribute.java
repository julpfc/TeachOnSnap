package com.julvez.pfc.teachonsnap.service.url;

public enum SessionAttribute {
	
	IDLANGUAGE("idLanguage"),
	VISIT("visit"),
	ERROR("error"),
	LAST_PAGE("lastPage");

		 		
	private final String realName;
 
	private SessionAttribute(String attributeName) {
		realName = attributeName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
