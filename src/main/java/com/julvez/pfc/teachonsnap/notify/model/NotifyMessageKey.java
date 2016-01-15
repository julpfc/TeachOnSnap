package com.julvez.pfc.teachonsnap.notify.model;

/**
 * Enumeration with the keys for localized messages related with notifications
 */
public enum NotifyMessageKey {
	/** HTML default template for the notifications */
	HTML_TEMPLATE("notify.html.template"),
	/** HTML default template for broadcasting */
	HTML_BROADCAST_TEMPLATE("notify.html.broadcast.template");
		
	/** Real message key on the messages properties file */
	private final String realName;
 
	private NotifyMessageKey(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
	
}
