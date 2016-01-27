package com.julvez.pfc.teachonsnap.lesson.model;

/**
 * Enumeration with the keys for localized messages related with lessons.
 */
public enum LessonMessageKey {
	/** Subject to notify a new lesson */
	NEW_LESSON_SUBJECT("lesson.new.subject"), 
	
	/** Message body to notify a new lesson */
	NEW_LESSON_MESSAGE("lesson.new.message"),
	
	/** Subject to notify a modified lesson */
	MODIFIED_LESSON_SUBJECT("lesson.mod.subject"),
	
	/** Message body to notify a modified lesson */
	MODIFIED_LESSON_MESSAGE("lesson.mod.message"),
	
	/** Subject to notify a published lesson */
	PUBLISHED_LESSON_SUBJECT("lesson.pub.subject"),
	
	/** Message body to notify a published lesson */
	PUBLISHED_LESSON_MESSAGE("lesson.pub.message");
		
	/** Real message key on the messages properties file */
	private final String realName;
 
	private LessonMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
