package com.julvez.pfc.teachonsnap.comment.model;

public enum CommentMessageKey {
	NEW_COMMENT_SUBJECT("comment.new.subject"), 
	NEW_COMMENT_MESSAGE("comment.new.message"), 
	REPLY_COMMENT_SUBJECT("comment.reply.subject"), 
	REPLY_COMMENT_MESSAGE("comment.reply.message");
		
	
	private final String realName;
 
	private CommentMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
