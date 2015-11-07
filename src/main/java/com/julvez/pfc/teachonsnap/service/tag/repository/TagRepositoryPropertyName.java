package com.julvez.pfc.teachonsnap.service.tag.repository;

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
