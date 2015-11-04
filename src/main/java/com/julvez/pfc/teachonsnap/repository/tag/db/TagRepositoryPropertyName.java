package com.julvez.pfc.teachonsnap.repository.tag.db;

public enum TagRepositoryPropertyName {

	LIMIT_CLOUDTAG("repository.tag.db.limit.cloudtag");
	
	 		
	private final String realName;
 
	private TagRepositoryPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
