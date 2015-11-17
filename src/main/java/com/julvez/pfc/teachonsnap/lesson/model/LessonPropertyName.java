package com.julvez.pfc.teachonsnap.lesson.model;

public enum LessonPropertyName {
	
		
	
	MAX_PAGE_RESULTS("pager.max.page.results");
	
	private final String realName;
 
	private LessonPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}