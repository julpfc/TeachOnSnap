package com.julvez.pfc.teachonsnap.lesson.model;

public enum LessonMessageKey {
	NEW_LESSON_SUBJECT("lesson.new.subject"), 
	NEW_LESSON_MESSAGE("lesson.new.message"), 
	MODIFIED_LESSON_SUBJECT("lesson.mod.subject"), 
	MODIFIED_LESSON_MESSAGE("lesson.mod.message"), 
	PUBLISHED_LESSON_SUBJECT("lesson.pub.subject"), 
	PUBLISHED_LESSON_MESSAGE("lesson.pub.message");
		
	
	private final String realName;
 
	private LessonMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
