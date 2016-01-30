package com.julvez.pfc.teachonsnap.controller.model;

import javax.servlet.http.HttpServletRequest;

/**
 * Enumeration with the request parameters names used by the application.
 * <p>
 * Used to pass values to a controller via HTTP GET or POST.
 * <p>
 * To be used on the {@link HttpServletRequest} parameters.
 * @see HttpServletRequest#getParameter(String)
 */
public enum Parameter {

	//Common
	/** Change to this new navigation language */
	CHANGE_LANGUAGE("changeLang"),
	
	/** Logout */
	LOGOUT("logout"),
	
	/** JSON string */
	JSON("json"),
	
	/** Export data */
	EXPORT("export"),
	
	/** Search query */
	SEARCH_QUERY("searchQuery"),
	
	/** Search type*/
	SEARCH_TYPE("searchType"),
	
	//Login
	/** User's email */	
	LOGIN_EMAIL("email"),
	
	/** User's password */
	LOGIN_PASSWORD("password"),
	
	/** User's email to remind password */
	LOGIN_EMAIL_REMIND("emailRemind"),
	
	/** User's email to register */
	LOGIN_EMAIL_REGISTER("emailRegister"),
	
	//Admin User
	/** Check. Send password email after registration */
	REGISTER_SEND_EMAIL("sendPassword"),
	
	/** Email list to register */
	REGISTER_MULTIPLE_EMAILS("emailList"),
	
	/** Role author */
	USER_ROLE_AUTHOR("author"),
	
	/** Role administrator */
	USER_ROLE_ADMIN("admin"),
	
	/** Block user */
	USER_BLOCK("blockUser"),
	
	/** Block user reason */
	USER_BLOCK_REASON("blockUserReason"),
	
	/** Unblock user */
	USER_UNBLOCK("unblockUser"),
	
	/** Group name */
	USER_GROUP_NAME("groupName"),
	
	/** Remove user from group */
	USER_GROUP_REMOVE_USER("removeUser"),
	
	/** Remove group */
	USER_GROUP_REMOVE("removeGroup"),
	
	//New/Edit Lesson
	/** Lesson's title */
	LESSON_NEW_TITLE("title"),
	
	/** Lesson's language */
	LESSON_NEW_LANGUAGE("lang"),
	
	/** Lesson's uploaded media file selected */
	LESSON_NEW_MEDIA_INDEX("index"),
	
	/** Lesson's optional text */
	LESSON_NEW_TEXT("text"),
	
	/** Lesson's tags */
	LESSON_NEW_TAGS("tags"),
	
	/** Lesson's links to lesson's sources */
	LESSON_NEW_SOURCES("sources"),
	
	/** Lesson's links to further information resources */
	LESSON_NEW_MOREINFOS("moreInfos"),
	
	/** Remove media from lesson */
	LESSON_MEDIA_REMOVE("remove"),
	
	/** Publish lesson*/
	LESSON_PUBLISH("publishLesson"),
	
	//Comments	
	/** Comment's body */
	LESSON_COMMENT("comment"),
	
	/** Comment's id */
	LESSON_COMMENTID("idComment"),
	
	/** Edit comment */
	LESSON_COMMENT_EDIT("editComment"),
	
	/** Ban comment */
	LESSON_COMMENT_BAN("banComment"),
	
	//Tests
	/** Publish test */
	LESSON_TEST_PUBLISH("publishTest"),
	
	/** Remove test */
	LESSON_TEST_DELETE("delTest"),
	
	/** Number of answers per question in the test */
	LESSON_TEST_NUMANSWERS("numAnswers"), 
	
	/** Indicates if the test is multiplechoice */
	LESSON_TEST_MULTIPLECHOICE("multipleChoice"),
	
	/** Remove question */
	QUESTION_DELETE("delQuestion"),
	
	/** Question's id */
	QUESTIONID("idQuestion"),
	
	/** Question's order within the test */
	QUESTION_PRIORITY("questionPriority"), 
	
	//User profile
	/** User's old password */
	OLD_PASSWORD("pwo"),
	
	/** User's new password */
	NEW_PASSWORD("pwn"),
	
	/** User's first name */
	FIRST_NAME("firstname"),
	
	/** User's last name */
	LAST_NAME("lastname"),
	
	/** User's additional information */
	USER_EXTRA_INFO("extraInfo"),
	
	//Upload
	/** Download the specified temporary uploaded file */
	UPLOAD_DOWNLOAD_INDEX("f"),
	
	/** List the temporary uploaded files */
	UPLOAD_LIST("l"),
	
	/** Remove the specified temporary uploaded file */
	UPLOAD_REMOVE_INDEX("r"), 
	
	//Follow
	/** Follow this author */
	FOLLOW_AUTHOR("followAuthor"),
	
	/** Unfollow this author */
	UNFOLLOW_AUTHOR("unfollowAuthor"),
	
	/** Follow this tag */
	FOLLOW_TAG("followTag"),
	
	/** Unfollow this tag */
	UNFOLLOW_TAG("unfollowTag"),
	
	/** Follow this lesson */
	FOLLOW_LESSON("followLesson"),
	
	/** Unfollow this lesson */
	UNFOLLOW_LESSON("unfollowLesson"), 
	
	//Broadcast
	/** Broadcast message */
	BROADCAST_MESSAGE("broadcastMessage"),
	
	/** Broadcast subject */
	BROADCAST_SUBJECT("broadcastSubject");

	
	/** Real parameter name on the URL */	 		
	private final String realName;
 
	private Parameter(String paramName) {
		realName = paramName;
	}

	@Override
	public String toString() {
		return realName;
	}
}
