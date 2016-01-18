package com.julvez.pfc.teachonsnap.media.model;

/**
 * Enumeration with the keys for localized messages related with media files
 */

public enum MediaMessageKey {
	/** Subject for the repository full message */
	REPOSITORY_FULL_SUBJECT("media.repository.full.subject"),
	/** Body for the repository full message */
	REPOSITORY_FULL_MESSAGE("media.repository.full.message"),
	/** Subject for the user's quota exceeded message */
	USER_QUOTA_EXCEEDED_SUBJECT("media.user.quota.exceeded.subject"),
	/** Body for the user's quota exceeded message */
	USER_QUOTA_EXCEEDED_MESSAGE("media.user.quota.exceeded.message");
		
	/** Real message key on the messages properties file */
	private final String realName;
 
	private MediaMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
