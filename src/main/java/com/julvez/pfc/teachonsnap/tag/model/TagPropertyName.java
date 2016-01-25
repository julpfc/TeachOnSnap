package com.julvez.pfc.teachonsnap.tag.model;

/**
 * Enumeration with the properties names related to tags.
 * <p>
 * To be used on the aplication properties file.
 */
public enum TagPropertyName {

	/** Max number of tags in a cloud of tags. */
	LIMIT_CLOUDTAG("tag.repository.limit.cloudtag");
	
	/** Real property name on the properties file */ 		
	private final String realName;
 
	private TagPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
