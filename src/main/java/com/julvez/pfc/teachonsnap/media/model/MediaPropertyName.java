package com.julvez.pfc.teachonsnap.media.model;

public enum MediaPropertyName {

	MEDIAFILE_DEFAULT_REPOSITORY("media.repository.defaultRepositoryID"),
	MEDIAFILE_MAX_SIZE("media.file.max.size.bytes");
	
	 		
	private final String realName;
 
	private MediaPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
