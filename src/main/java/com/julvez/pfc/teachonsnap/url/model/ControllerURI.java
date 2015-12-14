package com.julvez.pfc.teachonsnap.url.model;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/"), 
	LESSONS_BY_LAST("/last/"),
	LESSON_DRAFTS_BY_USER("/drafts/"), 
	LESSON("/lesson/"), 
	LESSON_PREVIEW("/preview/"), 
	ADMIN_USER_MANAGER("/admin/users/"), 
	ADMIN_USER_PROFILE("/admin/user/"), 
	ADMIN_GROUP_MANAGER("/admin/groups/"),
	ADMIN_GROUP_PROFILE("/admin/group/"), 
	ADMIN_GROUP_FOLLOWS("/admin/group/follow/"), 
	ADMIN_GROUP_FOLLOW_AUTHOR("/admin/group/follow/author/"),
	ADMIN_GROUP_FOLLOW_TAG("/admin/group/follow/tag/"), 
	USER_FOLLOWS("/follow/"),
	USER_FOLLOW_AUTHOR("/follow/author/"), 
	STATS_LESSON_TEST("/stats/test/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
}
