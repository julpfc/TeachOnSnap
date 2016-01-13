package com.julvez.pfc.teachonsnap.page.model;

/**
 * Enumeration with the keys for localized page's names
 */
public enum PageNameKey {
	
	/** Lesson page */
	LESSON("lesson.lesson.breadcrumb"),
	/** Lesson edit page */
	EDIT_LESSON("lesson.edit.breadcrumb"),
	/** Lesson test page */
	LESSON_TEST("lesson.test.breadcrumb"),
	/** New Lesson test page */
	NEW_LESSON_TEST("lesson.test.new.breadcrumb"),
	/** Lesson test edit page */
	EDIT_LESSON_TEST("lesson.test.edit.breadcrumb"),
	/** Lesson test new question page */
	NEW_QUESTION("lesson.test.question.new.breadcrumb"),
	/** Lesson test edit question page */
	EDIT_QUESTION("lesson.test.question.edit.breadcrumb"),

	/** Administrator: User manager page */	
	ADMIN_USERS("admin.users.breadcrumb"), 
	/** Administrator: User manager search by name results page */
	ADMIN_USERS_SEARCH_BY_NAME("admin.users.search.by.name.breadcrumb"),
	/** Administrator: User manager search by email results page */
	ADMIN_USERS_SEARCH_BY_EMAIL("admin.users.search.by.email.breadcrumb"),
	/** Administrator: User profile page */
	ADMIN_USER_PROFILE("admin.user.profile.breadcrumb"),
	/** Administrator: Group manager page */
	ADMIN_GROUPS("admin.groups.breadcrumb"),
	/** Administrator: Group manager search by name result page */
	ADMIN_GROUPS_SEARCH_BY_NAME("admin.groups.search.by.name.breadcrumb"),
	/** Administrator: Group profile page */
	ADMIN_GROUP_PROFILE("admin.group.profile.breadcrumb"),
	/** Administrator: Group followings page */
	ADMIN_GROUP_FOLLOWS("admin.group.follows.breadcrumb"),
	/** Administrator: Authors followed by group page */
	ADMIN_GROUP_FOLLOW_AUTHOR("admin.group.follow.author.breadcrumb"),
	/** Administrator: Tags followed by group page */
	ADMIN_GROUP_FOLLOW_TAG("admin.group.follow.tag.breadcrumb"),
	/** Administrator: Authors followed by group (filtered by email search) */
	ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_EMAIL("admin.group.follow.author.search.by.email.breadcrumb"),
	/** Administrator: Authors followed by group (filtered by name search) */
	ADMIN_GROUP_FOLLOW_AUTHOR_SEARCH_BY_NAME("admin.group.follow.author.search.by.name.breadcrumb"),
	/** Administrator: Tag search result page */
	ADMIN_GROUP_FOLLOW_TAG_SEARCH("admin.group.follow.tag.search.breadcrumb"),
	/** Administrator: Global last month stats page */
	ADMIN_STATS_MONTH("admin.stats.month.breadcrumb"),
	/** Administrator: Global last year stats page */
	ADMIN_STATS_YEAR("admin.stats.year.breadcrumb"),	
	/** Administrator: Broadcast page */
	ADMIN_BROADCAST_GROUP("admin.broadcast.group.breadcrumb"),
	
	/** User followings page */
	USER_FOLLOWS("user.follows.breadcrumb"),
	/** Authors followed by user */
	USER_FOLLOW_AUTHOR("user.follow.author.breadcrumb"),
	/** Authors followed by user (filtered by email) */
	USER_FOLLOW_AUTHOR_SEARCH_BY_EMAIL("user.follow.author.search.by.email.breadcrumb"),
	/** Authors followed by user (filtered by name) */
	USER_FOLLOW_AUTHOR_SEARCH_BY_NAME("user.follow.author.search.by.name.breadcrumb"),
	
	/** Stats: Lesson tests stats page */
	STATS_LESSON_TEST("stats.lesson.test.breadcrumb"),
	/** Stats: Last month Lesson stats page */
	STATS_LESSON_MONTH("stats.lesson.month.breadcrumb"),
	/** Stats: Last year Lesson stats page */
	STATS_LESSON_YEAR("stats.lesson.year.breadcrumb"),
	/** Stats: Last month author stats page */
	STATS_AUTHOR_MONTH("stats.author.month.breadcrumb"),
	/** Stats: Last year Author stats page */
	STATS_AUTHOR_YEAR("stats.author.year.breadcrumb");
	
	
	/** Message key for the localized text */	 		
	private final String key;
 
	private PageNameKey(String nameKey) {
		key = nameKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
