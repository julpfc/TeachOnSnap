package com.julvez.pfc.teachonsnap.controller.pager;

public enum PagerPropertyName {

	MAX_PAGE_COMMENTS("pager.max.page.comments"),
	MAX_PAGE_RESULTS("pager.max.page.results");
	
	 		
	private final String realName;
 
	private PagerPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
