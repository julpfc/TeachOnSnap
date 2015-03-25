package com.julvez.pfc.teachonsnap.manager.request;

public enum Attribute {
	
	//Common
	LANGUAGE_USERLANGUAGE("userLang"),
	USER("user"),
	STRING_HOST("host"),
	STRING_LOGINERROR("loginError"),
	STRING_ERRORMESSAGEKEY("errorMessageKey"), 
	STRING_ERRORTYPE("errorType"),
	LIST_PAGE_STACK("pageStack"),

	//Lesson
	LESSON("lesson"),
	LIST_MEDIAFILE_LESSONFILES("medias"),
	LIST_TAG_LESSONTAGS("tags"),
	LIST_LESSON_LINKEDLESSONS("linkedLessons"),
	LIST_LINK_MOREINFO("moreInfoLinks"),
	LIST_LINK_SOURCES("sourceLinks"),
	LIST_COMMENTS("comments"),
	
	//Lesson test
	USERLESSONTEST_ANSWERS("userTest"),
	LESSONTEST_QUESTIONS("test"),
	QUESTION("question"),

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
