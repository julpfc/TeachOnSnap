package com.julvez.pfc.teachonsnap.model.page;

public enum PageNameKey {
	
	LESSON("lesson.lesson.breadcrumb"), 
	EDIT_LESSON("lesson.edit.breadcrumb"), 
	NEW_LESSON_TEST("lesson.test.new.breadcrumb"), 
	EDIT_LESSON_TEST("lesson.test.edit.breadcrumb"), 
	NEW_QUESTION("lesson.test.question.new.breadcrumb"), 
	EDIT_QUESTION("lesson.test.question.edit.breadcrumb");
		 		
	private final String key;
 
	private PageNameKey(String nameKey) {
		key = nameKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
