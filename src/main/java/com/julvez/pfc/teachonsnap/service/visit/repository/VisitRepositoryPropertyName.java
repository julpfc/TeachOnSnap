package com.julvez.pfc.teachonsnap.service.visit.repository;

public enum VisitRepositoryPropertyName {

	LIMIT_USER_RANKING("visit.repository.limit.userrank"),
	ENABLE_ANON_VISIT_COUNTER("enable.anon.visit.counter");
	
	 		
	private final String realName;
 
	private VisitRepositoryPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
