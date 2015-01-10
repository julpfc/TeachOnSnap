package com.julvez.pfc.teachonsnap.manager.request;

public enum SessionAttribute {
	
	IDLANGUAGE("idLanguage"),
	USER("user"),
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
