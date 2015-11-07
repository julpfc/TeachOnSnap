package com.julvez.pfc.teachonsnap.tag.model;

public enum TagPropertyName {

	LIMIT_CLOUDTAG("tag.repository.limit.cloudtag");
	
	 		
	private final String realName;
 
	private TagPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
