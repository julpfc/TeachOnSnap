package com.julvez.pfc.teachonsnap.service.comment;

public enum CommentPropertyName {
	
		
	//Pager
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
