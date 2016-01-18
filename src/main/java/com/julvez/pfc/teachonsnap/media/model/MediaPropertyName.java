package com.julvez.pfc.teachonsnap.media.model;

/**
 * Enumeration with the properties names related to media files.
 * <p>
 * To be used on the aplication properties file.
 */
public enum MediaPropertyName {

	/** Default repository id */
	DEFAULT_REPOSITORY("media.repository.defaultRepositoryID"),
	
	/** Max repository size */
	MAX_REPOSITORY_SIZE("media.repository.global.max.size.bytes"),
	
	/** Max general user's repository size */
	MAX_USER_REPOSITORY_SIZE("media.repository.per.user.max.size.bytes"),
	
	/** Max user's repository size exception*/ 
	MAX_USER_REPOSITORY_SIZE_EXCEPTION("media.repository.per.user.max.size.bytes.iduser."),
	
	/** Max media file size allowed */
	MEDIAFILE_MAX_SIZE("media.file.max.size.bytes");
	
	/** Real property name on the properties file */ 
	private final String realName;
 
	private MediaPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
