package com.julvez.pfc.teachonsnap.notify.model;

public enum NotifyMessageKey {
	HTML_TEMPLATE("notify.html.template");
		
	
	private final String realName;
 
	private NotifyMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
