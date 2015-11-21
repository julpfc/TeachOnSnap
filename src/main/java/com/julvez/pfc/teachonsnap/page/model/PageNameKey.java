package com.julvez.pfc.teachonsnap.page.model;

public enum PageNameKey {
	
	LESSON("lesson.lesson.breadcrumb"), 
	EDIT_LESSON("lesson.edit.breadcrumb"), 
	NEW_LESSON_TEST("lesson.test.new.breadcrumb"), 
	EDIT_LESSON_TEST("lesson.test.edit.breadcrumb"), 
	NEW_QUESTION("lesson.test.question.new.breadcrumb"), 
	EDIT_QUESTION("lesson.test.question.edit.breadcrumb"), 
	ADMIN_USERS("admin.users.breadcrumb"), 
	ADMIN_USERS_SEARCH_BY_NAME("admin.users.search.by.name.breadcrumb"),
	ADMIN_USERS_SEARCH_BY_EMAIL("admin.users.search.by.email.breadcrumb"), 
	ADMIN_USER_PROFILE("admin.user.profile.breadcrumb");
	
		 		
	private final String key;
 
	private PageNameKey(String nameKey) {
		key = nameKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
