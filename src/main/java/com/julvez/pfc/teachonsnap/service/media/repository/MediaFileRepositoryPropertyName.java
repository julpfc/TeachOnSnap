package com.julvez.pfc.teachonsnap.service.media.repository;

public enum MediaFileRepositoryPropertyName {

	MEDIAFILE_DEFAULT_REPOSITORY("repository.media.db.defaultRepositoryID");
	
	 		
	private final String realName;
 
	private MediaFileRepositoryPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
