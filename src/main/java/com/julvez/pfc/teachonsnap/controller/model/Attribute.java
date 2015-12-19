package com.julvez.pfc.teachonsnap.controller.model;

public enum Attribute {
	
	//Common
	LANGUAGE_USERLANGUAGE("userLang"),
	LIST_LANGUAGES("languages"),
	USER("user"),
	STRING_HOST("host"),
	STRING_LOGINERROR("loginError"),
	STRING_ERRORMESSAGEKEY("errorMessageKey"), 
	STRING_ERRORTYPE("errorType"),
	LIST_PAGE_STACK("pageStack"),
	
	//Upload
	INT_MAX_UPLOAD_FILE_SIZE("maxUploadFileSize"),
	LIST_STRING_MEDIATYPE("acceptedFileTypes"),

	//Lesson
	LESSON("lesson"),
	LIST_MEDIAFILE_LESSONFILES("medias"),
	LIST_TAG("tags"),
	LIST_LINK_MOREINFO("moreInfoLinks"),
	LIST_LINK_SOURCES("sourceLinks"),
	LIST_COMMENTS("comments"),
	
	//Lesson test
	USERLESSONTEST_ANSWERS("userTest"),
	LESSONTEST_QUESTIONS("test"),
	QUESTION("question"),
	USERTESTRANK("userTestRank"),
	LIST_USERTESTRANKS("testRanks"),

	//Cloud tags
	LIST_CLOUDTAG_AUTHOR("authorCloudTags"),
	LIST_CLOUDTAG_TAG_USE("tagUseCloudTags"),
	LIST_CLOUDTAG_TAG_SEARCH("tagSearchCloudTags"),
	LIST_CLOUDTAG_LESSON("lessonCloudTags"),
	
	//Pager
	LIST_LESSON("lessons"),
	STRING_NEXTPAGE("nextPage"),
	STRING_PREVPAGE("prevPage"),
	STRING_SEARCHTYPE("searchType"),
	STRING_SEARCHKEYWORD("searchKeyword"),
	STRING_BACKPAGE("backPage"),
	
	//Admin
	LIST_USER("users"), 
	USER_PROFILE("profile"),
	LIST_GROUP("groups"),
	USERGROUP("group"),
	
	//Error
	INT_ERROR_STATUS_CODE("javax.servlet.error.status_code"), 
	THROWABLE_ERROR_EXCEPTION("javax.servlet.error.exception"),
	
	//Follow
	LIST_AUTHOR_FOLLOWED("authors"), 
	LIST_TAG_FOLLOWED("followTags"), 
	
	//Stats
	STATSTEST("statsTest"), 
	LIST_STATS_DATA("stats"), 
	LIST_STATS_EXTRA("statsExtra"),
	STRING_STATS_TYPE("statsType"), 
	STRING_STATS_CSV("statsCSV"), 
	STRING_STATS_EXTRA_CSV("statsExtraCSV"), 
	LIST_STATS_EXTRA_2("statsExtra2"),
	STRING_STATS_EXTRA_2_CSV("statsExtra2CSV"), 
	STRING_STATS_ADMIN("statsAdmin");

	
	private final String realName;
 
	private Attribute(String attributeName) {
		realName = attributeName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
