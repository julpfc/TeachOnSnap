package com.julvez.pfc.teachonsnap.repository.visit.db;

public enum VisitRepositoryPropertyName {

	LIMIT_USER_RANKING("repository.visit.db.limit.userrank");
	
	 		
	private final String realName;
 
	private VisitRepositoryPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
