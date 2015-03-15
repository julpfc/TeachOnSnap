package com.julvez.pfc.teachonsnap.manager.request;

public enum Parameter {

	//Common
	CHANGE_LANGUAGE("changeLang"),
	LOGOUT("logout"),
	
	//Login
	LOGIN_EMAIL("email"),
	LOGIN_PASSWORD("password"),
	
	//New/Edit Lesson
	LESSON_NEW_TITLE("title"),
	LESSON_NEW_LANGUAGE("lang"),
	LESSON_NEW_VIDEO_INDEX("index_video"),
	LESSON_NEW_AUDIO_INDEX("index_audio"),
	LESSON_NEW_FILE_ATTACH("attach"),
	LESSON_NEW_TEXT("text"),
	LESSON_NEW_TAGS("tags"),
	LESSON_NEW_SOURCES("sources"),
	LESSON_NEW_MOREINFOS("moreInfos"), 
	
	//Comments	
	LESSON_COMMENT("comment"), 
	LESSON_COMMENTID("idComment"), 
	LESSON_COMMENT_EDIT("editComment"), 
	LESSON_COMMENT_BAN("banComment"),
	
	//Tests
	LESSON_TEST_PUBLISH("publishTest");

		 		
	private final String realName;
 
	private Parameter(String paramName) {
		realName = paramName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
