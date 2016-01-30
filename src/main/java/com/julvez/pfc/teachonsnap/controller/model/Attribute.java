package com.julvez.pfc.teachonsnap.controller.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Enumeration with the request atributes names used by the application.
 * <p>
 * Used to pass objects bewteen controller and view within an HTTP request.
 * <p>
 * To be used on the {@link HttpServletRequest} attributes.
 * @see HttpServletRequest#setAttribute(String, Object)
 */
public enum Attribute {
	
	//Common
	/** User language */
	LANGUAGE_USERLANGUAGE("userLang"),
		
	/** List of available languages supported by the application */
	LIST_LANGUAGES("languages"),
	
	/** Logged-in user */
	USER("user"),
	
	/** Application's host server */
	STRING_HOST("host"),
	
	/** Login error flag */
	STRING_LOGINERROR("loginError"),
	
	/** Last error message key */
	STRING_ERRORMESSAGEKEY("errorMessageKey"),
	
	/** Last error type */
	STRING_ERRORTYPE("errorType"),
	
	/** List of pages deep browsed */
	LIST_PAGE_STACK("pageStack"),
	
	//Upload
	/** Max upload file size allowed (bytes)*/
	LONG_MAX_UPLOAD_FILE_SIZE("maxUploadFileSize"),
	
	/** List of accepetd file types. Supported by the application. */
	LIST_STRING_MEDIATYPE("acceptedFileTypes"),

	//Lesson
	/** Current lesson */
	LESSON("lesson"),
	
	/** List of media files for current lesson */
	LIST_MEDIAFILE_LESSONFILES("medias"),
	
	/** List of tags for current lesson */
	LIST_TAG("tags"),
	
	/** List of links to further information resources for current lesson */
	LIST_LINK_MOREINFO("moreInfoLinks"),
	
	/** List of links to lesson's sources for current lesson */
	LIST_LINK_SOURCES("sourceLinks"),
	
	/** List of comments for current lesson */
	LIST_COMMENTS("comments"),
	
	//Lesson test
	/** Self evaluation test for current lesson */
	LESSONTEST_QUESTIONS("test"),

	/** Current question */
	QUESTION("question"),
	
	/** User's answers for current test */
	USERLESSONTEST_ANSWERS("userTest"),
	
	/** User best result for current test */
	USERTESTRANK("userTestRank"),
	
	/** List of best users' results for current test */
	LIST_USERTESTRANKS("testRanks"),

	//Cloud tags
	/** List of authors for the cloud tag */
	LIST_CLOUDTAG_AUTHOR("authorCloudTags"),
	
	/** List of most tagged tags for the cloud tag */
	LIST_CLOUDTAG_TAG_USE("tagUseCloudTags"),
	
	/** List of most searched tags for the cloud tag */
	LIST_CLOUDTAG_TAG_SEARCH("tagSearchCloudTags"),
	
	/** List of most visited lessons for the cloud tag */
	LIST_CLOUDTAG_LESSON("lessonCloudTags"),
	
	//Pager
	/** List of lessons */
	LIST_LESSON("lessons"),
	
	/** Next page link */
	STRING_NEXTPAGE("nextPage"),
	
	/** Previous page link */
	STRING_PREVPAGE("prevPage"),
	
	/** Search type */
	STRING_SEARCHTYPE("searchType"),
	
	/** Search keyword */
	STRING_SEARCHKEYWORD("searchKeyword"),
	
	/** Back page link */
	STRING_BACKPAGE("backPage"),
	
	//Admin
	/** List of users */
	LIST_USER("users"),
	
	/** User profile */
	USER_PROFILE("profile"),
	
	/** List of users groups*/
	LIST_GROUP("groups"),
	
	/** Users Group */
	USERGROUP("group"),
	
	//Error
	/** Servlet Status code error*/
	INT_ERROR_STATUS_CODE("javax.servlet.error.status_code"),
	
	/** Servlet Error Exception */
	THROWABLE_ERROR_EXCEPTION("javax.servlet.error.exception"),
	
	/** Last status code */
	STRING_ERROR_STATUS_CODE("statusCode"),
	
	/** Last exception name */
	STRING_ERROR_EXCEPTION_NAME("exceptionName"),
	
	//Follow
	/** List of followed authors */
	LIST_AUTHOR_FOLLOWED("authors"),
	
	/** List of followed tags */
	LIST_TAG_FOLLOWED("followTags"), 
	
	//Stats
	/** Self evaluation test stats */
	STATSTEST("statsTest"), 
	
	/** List of key-values statistics */
	LIST_STATS_DATA("stats"), 
	
	/** Extra list of key-values statistics */
	LIST_STATS_EXTRA("statsExtra"),

	/** Even more extra list of key-values statistics */
	LIST_STATS_EXTRA_2("statsExtra2"),
	
	/** Type of stats */
	STRING_STATS_TYPE("statsType"),
	
	/** Global adminsitration stats */
	STRING_STATS_ADMIN("statsAdmin");

	
	/** Real attribute name on the HTTP request */
	private final String realName;
 
	private Attribute(String attributeName) {
		realName = attributeName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
