package com.julvez.pfc.teachonsnap.controller.model;

public enum ErrorMessageKey {
	
	//Common
	/**	No error, none message key */
	NONE(null),
	
	/**	Not modified, no changes to save */
	SAVE_NOCHANGES("save.nochanges"), 
	
	/**	Error in a saving op */
	SAVE_ERROR("save.error"), 
	
	/**	Error in a removing op */
	REMOVE_ERROR("remove.error"),
	
	/**	Error sending mail */
	MAIL_SEND_ERROR("mail.send.error"),
	
	/**	User is banned, the action cannot be performed */
	USER_BANNED("user.banned"),
	
	//Test	
	/**	Input values are not valid for a lesson test */
	INVALID_INPUT_ERROR_TEST("invalid.input.error.test"),
	
	/**	Test is in an invalid state to publish */
	INVALID_INPUT_ERROR_TEST_PUBLISH("invalid.input.error.test.publish"),
	
	/**	There is already a test for the same lesson */
	SAVE_DUPLICATE_ERROR_TEST("save.duplicate.error.test"),
	
	/**	Test published without errors */
	TEST_PUBLISHED("test.published"), 
	
	/**	Test unpublished without errors */
	TEST_UNPUBLISHED("test.unpublished"), 
	
	/**	Test removed without errors */
	TEST_REMOVED("test.removed"),
	
	/**	Test created without errors */
	TEST_CREATED("test.created"),
	
	/** Question saved without errors */
	QUESTION_SAVED("question.saved"), 
	
	/**	Question created without errors */
	QUESTION_CREATED("question.created"), 
	
	/**	Question removed without errors */
	QUESTION_REMOVED("question.removed"), 
	
	/**	User test results saved without errors */
	USERTEST_SAVED("usertest.saved"), 
	
	//Lesson
	/**	There is already a lesson with the same title */
	SAVE_DUPLICATE_ERROR_LESSON("save.duplicate.error.lesson"),
	
	/**	Lesson created without errors */
	LESSON_CREATED("lesson.created"),
	
	/**	Lesson created, but there was an error managing the media files */
	LESSON_CREATED_WITH_MEDIA_ERROR("lesson.created.with.media.error"),
	
	/**	Lesson saved witouth errors */
	LESSON_SAVED("lesson.saved"),
	
	/**	Lesson published without errors */
	LESSON_PUBLISHED("lesson.published"),
	
	/**	Lesson unpublished without errors */
	LESSON_UNPUBLISHED("lesson.unpublished"),
	
	/**	Comment unblocked without errors */
	COMMENT_UNBLOCKED("comment.unblocked"),
	
	/**	Comment blocked without errors */
	COMMENT_BLOCKED("comment.blocked"),
	
	/**	Comment saved without errors */
	COMMENT_SAVED("comment.saved"),
	
	/**	Comment created without errors */
	COMMENT_CREATED("comment.created"),
	
	//User profile
	/**	Username saved without errors */
	USERNAME_SAVED("username.saved"), 
	
	/**	Wrong current password input error when trying to change it */
	WRONG_CURRENT_PASSWORD("wrong.current.password.error.prefs"), 
	
	/**	User password changed without errors*/
	PASSWORD_CHANGED("password.changed"), 
	
	//Password remind
	/**	Password remind email was sent without errors */
	PASSWORD_REMIND_SENT("password.remind.sent"), 
	
	/**	There was a problem sending the password remind email */
	PASSWORD_REMIND_EMAIL_ERROR("password.remind.email.error"), 

	//Register
	/**	There is already a user registered with that email error */
	REGISTER_EMAIL_DUPLICATE("register.email.duplicate"), 
	
	/**	Registration email sent without errors */
	REGISTER_SENT("register.sent"), 
	
	/**	The specified email address is not on the apllication mail host's whitelist */
	REGISTER_UNVERIFIED_MAIL("register.unverified.mail"), 
	
	/**	User saved without errors */
	USER_SAVED("user.saved"), 
	
	/**	Users created without errors */
	USERS_CREATED("users.created"),
	
	//Admin
	/**	There is already a group with the same name */
	SAVE_DUPLICATE_ERROR_GROUP("save.duplicate.error.group"), 
	
	/**	Group saved without errors */
	GROUP_SAVED("group.saved"),
	
	/**	Users added to group without errors */
	GROUP_USERS_ADDED("group.users.added"), 
	
	/**	Group deleted without errors */
	GROUP_DELETED("group.deleted"), 
	
	//Broadcast
	/**	Broadcast sent without errors */
	BROADCAST_SENT("broadcast.sent"), 
	
	/**	There was a problem sending a broadcast message */
	BROADCAST_ERROR("broadcast.error");
	
	/** Message key on the messages properties file */
	private final String key;
 
	private ErrorMessageKey(String messageKey) {
		key = messageKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
