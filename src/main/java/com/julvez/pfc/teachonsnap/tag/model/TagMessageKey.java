package com.julvez.pfc.teachonsnap.tag.model;

/**
 * Enumeration with the keys for localized messages related with tags
 */
public enum TagMessageKey {
	/** Subject to notify a lesson was tagged with a followed tag */
	LESSON_TAGGED_SUBJECT("lesson.tagged.subject"), 
	
	/** Message body to notify a lesson was tagged with a followed tag */
	LESSON_TAGGED_MESSAGE("lesson.tagged.message");
		
	/** Real message key on the messages properties file */
	private final String realName;
 
	private TagMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
