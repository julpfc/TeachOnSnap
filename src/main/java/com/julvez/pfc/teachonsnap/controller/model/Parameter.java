package com.julvez.pfc.teachonsnap.controller.model;

public enum Parameter {

	//Common
	CHANGE_LANGUAGE("changeLang"),
	LOGOUT("logout"),
	JSON("json"),
	EXPORT("export"),
	
	//Login
	LOGIN_EMAIL("email"),
	LOGIN_PASSWORD("password"),
	LOGIN_EMAIL_REMIND("emailRemind"),
	
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
	LESSON_TEST_PUBLISH("publishTest"), 
	LESSON_TEST_DELETE("delTest"), 
	LESSON_TEST_NUMANSWERS("numAnswers"), 
	LESSON_TEST_MULTIPLECHOICE("multipleChoice"),
	QUESTIONID_DELETE("delQuestion"), 
	QUESTIONID("idQuestion"), 
	QUESTION_PRIORITY("questionPriority"), 
	
	//Preferences
	OLD_PASSWORD("pwo"), 
	NEW_PASSWORD("pwn"), 
	FIRST_NAME("firstname"), 
	LAST_NAME("lastname");

		 		
	private final String realName;
 
	private Parameter(String paramName) {
		realName = paramName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
