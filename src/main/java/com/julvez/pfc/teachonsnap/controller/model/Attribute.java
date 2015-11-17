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
	LIST_TAG_LESSONTAGS("tags"),
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
	LIST_CLOUDTAG("cloudTags"),
	
	//Pager
	LIST_LESSON("lessons"),
	STRING_NEXTPAGE("nextPage"),
	STRING_PREVPAGE("prevPage"),
	STRING_SEARCHTYPE("searchType"),
	STRING_SEARCHKEYWORD("searchKeyword"),
	
	
	//Error
	INT_ERROR_STATUS_CODE("javax.servlet.error.status_code"), 
	THROWABLE_ERROR_EXCEPTION("javax.servlet.error.exception");

	
	private final String realName;
 
	private Attribute(String attributeName) {
		realName = attributeName;
	}

	@Override
	public String toString() {
		return realName;
	}
}