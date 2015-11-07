package com.julvez.pfc.teachonsnap.stats.model;

public enum StatsPropertyName {

	LIMIT_USER_RANKING("visit.repository.limit.userrank"),
	ENABLE_ANON_VISIT_COUNTER("enable.anon.visit.counter");
	
	 		
	private final String realName;
 
	private StatsPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
