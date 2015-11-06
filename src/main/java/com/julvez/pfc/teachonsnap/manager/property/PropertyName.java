package com.julvez.pfc.teachonsnap.manager.property;

public enum PropertyName {
	//Common
	TEACHONSNAP_HOST("teachonsnap.host"),
	ENABLE_ANON_VISIT_COUNTER("enable.anon.visit.counter"),
		
	//Pager
	MAX_PAGE_COMMENTS("pager.max.page.comments"),
	MAX_PAGE_RESULTS("pager.max.page.results");
	
	private final String realName;
 
	private PropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
