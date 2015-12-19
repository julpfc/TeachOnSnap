package com.julvez.pfc.teachonsnap.url.model;

public enum SearchType {

	NAME("name"), 
	EMAIL("email");

		 		
	private final String type;
 
	private SearchType(String uri) {
		type = uri;
	}

	@Override
	public String toString() {
		return type;
	}
	
	public boolean equals(String type){
		return this.type.equalsIgnoreCase(type);
	}
}
