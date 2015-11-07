package com.julvez.pfc.teachonsnap.media.model;

public enum MediaPropertyName {

	MEDIAFILE_DEFAULT_REPOSITORY("media.repository.defaultRepositoryID");
	
	 		
	private final String realName;
 
	private MediaPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
