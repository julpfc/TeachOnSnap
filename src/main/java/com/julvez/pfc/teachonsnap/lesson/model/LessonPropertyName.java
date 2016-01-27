package com.julvez.pfc.teachonsnap.lesson.model;

/**
 * Enumeration with the properties names related to lessons.
 * <p>
 * To be used on the aplication properties file.
 */
public enum LessonPropertyName {
	
	/**	Pagination limit for lessons. Max number of lessons in a page. */
	MAX_PAGE_RESULTS("pager.max.page.results");
	
	/** Real property name on the properties file */
	private final String realName;
 
	private LessonPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
