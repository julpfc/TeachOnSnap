package com.julvez.pfc.teachonsnap.stats.model;

/**
 * Enumeration with the type os stats available, used as selector.
 */
public enum StatsType {
	
	/** View last month stats*/
	MONTH("month"), 
	
	/** View last year stats*/
	YEAR("year");

	/** Type value for selectors*/	 		
	private final String type;
 
	private StatsType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}	
	
	/**
	 * Checks if the type matches this object's
	 * @param type to be matched
	 * @return true if this object has the same type.
	 */
	public boolean equals(String type){
		return this.type.equalsIgnoreCase(type);
	}
	
}
