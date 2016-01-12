package com.julvez.pfc.teachonsnap.comment.model;

/**
 * Enumeration with the properties names related to comments.
 * <p>
 * To be used on the aplication properties file.
 */
public enum CommentPropertyName {
	
	/**	Pagination limit for comments. Max number of comments in a page. */
	MAX_PAGE_COMMENTS("pager.max.page.comments");
	
	private final String realName;
 
	private CommentPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
