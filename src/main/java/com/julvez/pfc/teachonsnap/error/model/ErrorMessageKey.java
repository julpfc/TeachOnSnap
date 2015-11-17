package com.julvez.pfc.teachonsnap.error.model;

public enum ErrorMessageKey {
	
	//Common
	NONE(null),
	SAVE_NOCHANGES("save.nochanges"), 
	SAVE_ERROR("save.error"), 
	REMOVE_ERROR("remove.error"),
	
	//Test
	INVALID_INPUT_ERROR_TEST("invalid.input.error.test"),
	INVALID_INPUT_ERROR_TEST_PUBLISH("invalid.input.error.test.publish"),
	SAVE_DUPLICATE_ERROR_TEST("save.duplicate.error.test"),
	TEST_PUBLISHED("test.published"), 
	TEST_UNPUBLISHED("test.unpublished"), 
	TEST_REMOVED("test.removed"),
	TEST_CREATED("test.created"),
	QUESTION_SAVED("question.saved"), 
	QUESTION_CREATED("question.created"), 
	QUESTION_REMOVED("question.removed"), 
	USERTEST_SAVED("usertest.saved"), 
	
	//Lesson
	SAVE_DUPLICATE_ERROR_LESSON("save.duplicate.error.lesson"),
	LESSON_CREATED("lesson.created"),
	LESSON_CREATED_WITH_MEDIA_ERROR("lesson.created.with.media.error"),
	LESSON_SAVED("lesson.saved"),
	LESSON_PUBLISHED("lesson.published"),
	LESSON_UNPUBLISHED("lesson.unpublished"),
	COMMENT_UNBLOCKED("comment.unblocked"),
	COMMENT_BLOCKED("comment.blocked"),
	COMMENT_SAVED("comment.saved"),
	COMMENT_CREATED("comment.created"),
	
	//Preferences
	USERNAME_SAVED("username.saved"), 
	WRONG_CURRENT_PASSWORD("wrong.current.password.error.prefs"), 
	PASSWORD_CHANGED("password.changed"), 
	
	//Login password remind
	PASSWORD_REMIND_SENT("password.remind.sent"), 
	PASSWORD_REMIND_SEND_ERROR("password.remind.send.error"), 
	PASSWORD_REMIND_EMAIL_ERROR("password.remind.email.error");
		 		
	private final String key;
 
	private ErrorMessageKey(String messageKey) {
		key = messageKey;
	}

	@Override
	public String toString() {
		return key;
	}
}