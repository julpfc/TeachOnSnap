package com.julvez.pfc.teachonsnap.manager.mail.javamail;

public enum MailPropertyName {

	JAVAMAIL_SMTP_HOST("manager.mail.javamail.smtp.host"),
	JAVAMAIL_SMTP_PORT("manager.mail.javamail.smtp.port"),
	JAVAMAIL_SMTP_STARTTLS("manager.mail.javamail.smtp.starttls"),
	JAVAMAIL_AUTH_USER("manager.mail.javamail.auth.user"),
	JAVAMAIL_AUTH_PASS("manager.mail.javamail.auth.pass"),
	JAVAMAIL_SENDER("manager.mail.javamail.sender");
	
	
	 		
	private final String realName;
 
	private MailPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
