package com.julvez.pfc.teachonsnap.model.error;

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
	
	//Lesson
	COMMENT_UNBLOCKED("comment.unblocked"),
	COMMENT_BLOCKED("comment.blocked"),
	COMMENT_SAVED("comment.saved"),
	COMMENT_CREATED("comment.created");
		 		
	private final String key;
 
	private ErrorMessageKey(String messageKey) {
		key = messageKey;
	}

	@Override
	public String toString() {
		return key;
	}
}
