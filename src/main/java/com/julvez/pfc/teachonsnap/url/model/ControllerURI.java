package com.julvez.pfc.teachonsnap.url.model;

public enum ControllerURI {

	CHANGE_PASSWORD("/changepw/"), 
	LESSONS_BY_LAST("/last/"),
	LESSON_DRAFTS_BY_USER("/drafts/"), 
	LESSON("/lesson/"), 
	LESSON_PREVIEW("/preview/"), 
	LESSON_EDIT("/lesson/edit/"),
	ADMIN_USER_MANAGER("/admin/users/"), 
	ADMIN_USER_PROFILE("/admin/user/"), 
	ADMIN_GROUP_MANAGER("/admin/groups/"),
	ADMIN_GROUP_PROFILE("/admin/group/"), 
	ADMIN_GROUP_FOLLOWS("/admin/group/follow/"), 
	ADMIN_GROUP_FOLLOW_AUTHOR("/admin/group/follow/author/"),
	ADMIN_GROUP_FOLLOW_TAG("/admin/group/follow/tag/"), 
	USER_FOLLOWS("/follow/"),
	USER_FOLLOW_AUTHOR("/follow/author/"), 
	STATS_TEST("/stats/test/"),
	STATS_AUTHOR_LESSON_TEST("/stats/author/lesson/test/"),
	STATS_LESSON_TEST("/stats/lesson/test/"),
	STATS_LESSON_MONTH("/stats/lesson/month/"),
	STATS_LESSON_YEAR("/stats/lesson/year/"),
	STATS_AUTHOR_MONTH("/stats/author/month/"),
	STATS_AUTHOR_YEAR("/stats/author/year/"), 
	STATS_AUTHOR_LESSON_MONTH("/stats/author/lesson/month/"), 
	STATS_AUTHOR_LESSON_YEAR("/stats/author/lesson/year/");

		 		
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
	
	
	public static ControllerURI getURIFromPath(String servletPath){
		ControllerURI uri = null;
		
		if(servletPath != null && servletPath.length()>1){			
			servletPath = servletPath + "/";			
			for(ControllerURI aux:ControllerURI.values()){
				if(aux.URI.equalsIgnoreCase(servletPath)){
					uri = aux;
					break;
				}
			}			
		}		
		return uri;		
	}
}
