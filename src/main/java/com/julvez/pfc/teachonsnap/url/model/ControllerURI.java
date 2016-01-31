package com.julvez.pfc.teachonsnap.url.model;

/**
 * Enumeration with the relative URIs of each Controller in the application.
 */
public enum ControllerURI {

	/**	Home URI */
	HOME("/"),
	
	/**	Login URI */
	LOGIN("/login/"),
	
	/**	Error URI */
	ERROR("/error/"),
	
	/**	Change password URI */
	CHANGE_PASSWORD("/changepw/"),
	
	/**	Last lessons URI */
	LESSONS_BY_LAST("/last/"),
	
	/**	User's lesson drafts URI */
	LESSON_DRAFTS_BY_USER("/drafts/"), 
	
	/**	Lesson URI */
	LESSON("/lesson/"),
	
	/**	Lesson preview URI */
	LESSON_PREVIEW("/preview/"), 
	
	/**	Create a new lesson URI */
	LESSON_NEW("/lesson/new/"),
	
	/**	Edit a lesson URI */
	LESSON_EDIT("/lesson/edit/"),
	
	/**	Lesson's comments URI */
	LESSON_COMMENT("/lesson/comment/"), 
	
	/**	Create new lesson test URI */
	LESSON_TEST_NEW("/test/new/"),
	
	/**	Lesson test URI */
	LESSON_TEST("/test/"),
	
	/**	Edit a lesson test URI */
	LESSON_TEST_EDIT("/test/edit/"),
	
	/**	Edit a question from a lesson test URI */
	LESSON_TEST_QUESTION("/test/question/"),
	
	/**	Author's lessons URI */
	AUTHOR("/author/"),
	
	/**	Lessons cointaining a tag URI */
	TAG("/tag/"),
	
	/**	Upload media files URI */
	UPLOAD("/upload/"),
	
	/**	Administration: User manager URI */
	ADMIN_USER_MANAGER("/admin/users/"), 
	
	/**	Administration: User profile URI */
	ADMIN_USER_PROFILE("/admin/user/"),
	
	/**	Administration: Group manager URI */
	ADMIN_GROUP_MANAGER("/admin/groups/"),
	
	/**	Administration: Group profile URI */
	ADMIN_GROUP_PROFILE("/admin/group/"), 
	
	/**	Administration: Group followings URI */
	ADMIN_GROUP_FOLLOWS("/admin/group/follow/"), 
	
	/**	Administration: Authors followed by group URI */
	ADMIN_GROUP_FOLLOW_AUTHOR("/admin/group/follow/author/"),
	
	/**	Administration: Tags followed by group URI */
	ADMIN_GROUP_FOLLOW_TAG("/admin/group/follow/tag/"), 
	
	/**	Administration: Global Month Stats URI */
	ADMIN_STATS_MONTH("/admin/stats/month/"),
	
	/**	Administration: Global year stats URI */
	ADMIN_STATS_YEAR("/admin/stats/year/"),
	
	/**	Administration: Broadcast URI */
	ADMIN_BROADCAST("/admin/broadcast/"),
	
	/**	User followings URI */
	USER_FOLLOWS("/follow/"),
	
	/**	Authors followed by user URI */
	USER_FOLLOW_AUTHOR("/follow/author/"),
	
	/** User profile URI */
	USER_PROFILE("/profile/"),
	
	/** Lesson test Stats URI */
	STATS_TEST("/stats/test/"),
	
	/** Lesson test Stats for Lesson's Author URI */
	STATS_AUTHOR_LESSON_TEST("/stats/author/lesson/test/"),
	
	/** Lesson test for lesson Stats URI */
	STATS_LESSON_TEST("/stats/lesson/test/"),
	
	/** Lesson Stats by month URI */
	STATS_LESSON_MONTH("/stats/lesson/month/"),
	
	/** Lesson Stats by year URI */
	STATS_LESSON_YEAR("/stats/lesson/year/"),
	
	/** Author Stats by month URI */
	STATS_AUTHOR_MONTH("/stats/author/month/"),
	
	/** Author Stats by year URI */
	STATS_AUTHOR_YEAR("/stats/author/year/"),
	
	/** Lesson Stats by month for Author URI */
	STATS_AUTHOR_LESSON_MONTH("/stats/author/lesson/month/"),
	
	/** Lesson Stats by year for author URI */
	STATS_AUTHOR_LESSON_YEAR("/stats/author/lesson/year/"),
	
	/** Administrator: Lesson test Stats for Lesson's Author */
	STATS_ADMIN_AUTHOR_LESSON_TEST("/stats/admin/author/lesson/test/"),
	
	/** Administrator: Lesson test for lesson Stats URI */
	STATS_ADMIN_LESSON_TEST("/stats/admin/lesson/test/"),
	
	/** Administrator: Lesson Stats by month URI */
	STATS_ADMIN_LESSON_MONTH("/stats/admin/lesson/month/"),
	
	/** Administrator: Lesson Stats by year URI */
	STATS_ADMIN_LESSON_YEAR("/stats/admin/lesson/year/"),
	
	/** Administrator: Author Stats by month URI */
	STATS_ADMIN_AUTHOR_MONTH("/stats/admin/author/month/"),
	
	/** Administrator: Author Stats by year URI */
	STATS_ADMIN_AUTHOR_YEAR("/stats/admin/author/year/"),
	
	/** Administrator: Lesson Stats by month for Author URI */
	STATS_ADMIN_AUTHOR_LESSON_MONTH("/stats/admin/author/lesson/month/"),
	
	/** Administrator: Lesson Stats by year for author URI */
	STATS_ADMIN_AUTHOR_LESSON_YEAR("/stats/admin/author/lesson/year/"),
	
	/** Static resources/media URI */
	RESOURCES_MEDIA("/resources/media/");

	/** Relative URI to the controller */
	private final String URI;
 
	private ControllerURI(String uri) {
		URI = uri;
	}

	@Override
	public String toString() {
		return URI;
	}
		
	/**
	 * Extracts the relative path from the servlet path and matches it.  
	 * @param servletPath
	 * @return ControllerURI that matches the relative path extracted from the servlet path.
	 */
	public static ControllerURI getURIFromPath(String servletPath){
		ControllerURI uri = null;
		//if the path is not empty 
		if(servletPath != null){
			//adds final '/'
			if(servletPath.length()>1){
				servletPath = servletPath + "/";
			}
			//matches against the enum values
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
