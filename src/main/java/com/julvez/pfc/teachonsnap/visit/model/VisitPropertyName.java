package com.julvez.pfc.teachonsnap.visit.model;

public enum VisitPropertyName {

	LIMIT_USER_RANKING("visit.repository.limit.userrank"),
	ENABLE_ANON_VISIT_COUNTER("enable.anon.visit.counter");
	
	 		
	private final String realName;
 
	private VisitPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
