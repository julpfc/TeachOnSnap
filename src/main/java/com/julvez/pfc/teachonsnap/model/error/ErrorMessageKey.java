package com.julvez.pfc.teachonsnap.model.error;

public enum ErrorMessageKey {
	
	//Common
	NONE(null),
	SAVE_NOCHANGES("save.nochanges"), 
	SAVE_ERROR("save.error"),
	
	//Test
	QUESTION_SAVED("question.saved"), 
	QUESTION_CREATED("question.created"), 
	
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
