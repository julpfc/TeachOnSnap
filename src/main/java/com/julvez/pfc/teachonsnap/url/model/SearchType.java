package com.julvez.pfc.teachonsnap.url.model;


/**
 * Enumeration with types of search available. It can be specified in an URL as a param.
 */
public enum SearchType {

	/** Search by name */
	NAME("name"),
	
	/** Search by email */
	EMAIL("email");

	/** Type value */
	private final String type;
 
	private SearchType(String uri) {
		type = uri;
	}

	@Override
	public String toString() {
		return type;
	}
	
	/**
	 * @param type
	 * @return true if type matches a SearchType valid value.
	 */
	public boolean equals(String type){
		return this.type.equalsIgnoreCase(type);
	}
}
