package com.julvez.pfc.teachonsnap.stats.model;

public enum StatsType {

	MONTH("month"), 
	YEAR("year");

		 		
	private final String type;
 
	private StatsType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}	
	
	public boolean equals(String type){
		return this.type.equalsIgnoreCase(type);
	}
	
}
