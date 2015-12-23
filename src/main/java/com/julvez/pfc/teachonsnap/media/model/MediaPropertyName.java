package com.julvez.pfc.teachonsnap.media.model;

public enum MediaPropertyName {

	DEFAULT_REPOSITORY("media.repository.defaultRepositoryID"),
	MAX_REPOSITORY_SIZE("media.repository.global.max.size.bytes"),
	MAX_USER_REPOSITORY_SIZE("media.repository.per.user.max.size.bytes"),
	MAX_USER_REPOSITORY_SIZE_EXCEPTION("media.repository.per.user.max.size.bytes.iduser."),
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
