package com.julvez.pfc.teachonsnap.stats.model;

/**
 * Enumeration with the properties names related to stats.
 * <p>
 * To be used on the aplication properties file.
 */
public enum StatsPropertyName {

	/** Max number of users in a ranking. */
	LIMIT_USER_RANKING("visit.repository.limit.userrank"),
	
	/** Enable/Disable counting anonymous visits on stats */
	ENABLE_ANON_VISIT_COUNTER("enable.anon.visit.counter");
	
	/** Real property name on the properties file */
	private final String realName;
 
	private StatsPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
