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
	ADMIN_USER_PROFILE("admin.user.profile.breadcrumb"), 
	ADMIN_GROUPS("admin.groups.breadcrumb"), 
	ADMIN_GROUPS_SEARCH_BY_NAME("admin.groups.search.by.name.breadcrumb"), 
	ADMIN_GROUP_PROFILE("admin.group.profile.breadcrumb"), 
	ADMIN_GROUP_FOLLOWS("admin.group.follows.breadcrumb"), 
	ADMIN_GROUP_FOLLOW_AUTHOR("admin.group.follow.author.breadcrumb"),
	ADMIN_GROUP_FOLLOW_TAG("admin.group.follow.tag.breadcrumb"), 
	ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_EMAIL("admin.group.follow.author.search.by.email.breadcrumb"), 
	ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_NAME("admin.group.follow.author.search.by.name.breadcrumb"), 
	ADMIN_GROUP_FOLLOW_TAG_SEARCH("admin.group.follow.tag.search.breadcrumb"), 
	USER_FOLLOWS("user.follows.breadcrumb"), 
	USER_FOLLOW_AUTHOR("user.follow.author.breadcrumb"),
	USER_FOLLOW_AUTHOR_SEARCH_BY_EMAIL("user.follow.author.search.by.email.breadcrumb"), 
	USER_FOLLOW_AUTHOR_SEARCH_BY_NAME("user.follow.author.search.by.name.breadcrumb");
	
		 		
	private final String key;
 
	private PageNameKey(String nameKey) {
		key = nameKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
