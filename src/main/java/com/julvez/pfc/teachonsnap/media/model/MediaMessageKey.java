package com.julvez.pfc.teachonsnap.media.model;

public enum MediaMessageKey {
	REPOSITORY_FULL_SUBJECT("media.repository.full.subject"),
	REPOSITORY_FULL_MESSAGE("media.repository.full.message"), 
	USER_QUOTA_EXCEEDED_SUBJECT("media.user.quota.exceeded.subject"), 
	USER_QUOTA_EXCEEDED_MESSAGE("media.user.quota.exceeded.message");
		
	
	private final String realName;
 
	private MediaMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
