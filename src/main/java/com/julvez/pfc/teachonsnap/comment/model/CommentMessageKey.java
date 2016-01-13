package com.julvez.pfc.teachonsnap.comment.model;

/**
 * Enumeration with the keys for localized messages related with comments
 */
public enum CommentMessageKey {
	
	/** Subject to notify new comment */
	NEW_COMMENT_SUBJECT("comment.new.subject"), 
	
	/** Message body to notify new comment */
	NEW_COMMENT_MESSAGE("comment.new.message"), 
	
	/** Subject to notify new answer for comment */
	REPLY_COMMENT_SUBJECT("comment.reply.subject"), 
	
	/** Message body to notify new answer for comment */
	REPLY_COMMENT_MESSAGE("comment.reply.message");
		
	/** Real message key on the messages properties file */
	private final String realName;
 
	private CommentMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
