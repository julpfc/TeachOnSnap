package com.julvez.pfc.teachonsnap.tag.model;

public enum TagMessageKey {
	LESSON_TAGGED_SUBJECT("lesson.tagged.subject"), 
	LESSON_TAGGED_MESSAGE("lesson.tagged.message");
		
	
	private final String realName;
 
	private TagMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
