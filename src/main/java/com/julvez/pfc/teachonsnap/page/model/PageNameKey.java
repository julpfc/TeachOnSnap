package com.julvez.pfc.teachonsnap.page.model;

public enum PageNameKey {
	
	LESSON("lesson.lesson.breadcrumb"), 
	EDIT_LESSON("lesson.edit.breadcrumb"), 
	LESSON_TEST("lesson.test.breadcrumb"),
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
	ADMIN_STATS_MONTH("admin.stats.month.breadcrumb"),
	ADMIN_STATS_YEAR("admin.stats.year.breadcrumb"),
	USER_FOLLOWS("user.follows.breadcrumb"), 
	USER_FOLLOW_AUTHOR("user.follow.author.breadcrumb"),
	USER_FOLLOW_AUTHOR_SEARCH_BY_EMAIL("user.follow.author.search.by.email.breadcrumb"), 
	USER_FOLLOW_AUTHOR_SEARCH_BY_NAME("user.follow.author.search.by.name.breadcrumb"), 
	STATS_LESSON_TEST("stats.lesson.test.breadcrumb"), 
	STATS_LESSON_MONTH("stats.lesson.month.breadcrumb"), 
	STATS_LESSON_YEAR("stats.lesson.year.breadcrumb"), 
	STATS_AUTHOR_MONTH("stats.author.month.breadcrumb"), 
	STATS_AUTHOR_YEAR("stats.author.year.breadcrumb"), 
	ADMIN_BROADCAST_GROUP("admin.broadcast.group.breadcrumb");
	
		 		
	private final String key;
 
	private PageNameKey(String nameKey) {
		key = nameKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
