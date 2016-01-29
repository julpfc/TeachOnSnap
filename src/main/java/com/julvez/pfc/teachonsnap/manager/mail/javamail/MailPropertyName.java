package com.julvez.pfc.teachonsnap.manager.mail.javamail;

/**
 * Enumeration with the properties names related to Java Mail.
 * <p>
 * To be used on the aplication properties file.
 */
public enum MailPropertyName {

	/** SMTP host */
	JAVAMAIL_SMTP_HOST("manager.mail.javamail.smtp.host"),
	
	/** SMTP host */
	JAVAMAIL_SMTP_PORT("manager.mail.javamail.smtp.port"),
	
	/** SMTP enable TLS */
	JAVAMAIL_SMTP_STARTTLS("manager.mail.javamail.smtp.starttls"),
	
	/** Authentication user */
	JAVAMAIL_AUTH_USER("manager.mail.javamail.auth.user"),
	
	/** Authentication password */
	JAVAMAIL_AUTH_PASS("manager.mail.javamail.auth.pass"),
	
	/** Mails sender */
	JAVAMAIL_SENDER("manager.mail.javamail.sender");
	
	/** Real property name on the properties file */ 		
	private final String realName;
 
	private MailPropertyName(String property) {
		realName = property;
	}

	@Override
	public String toString() {
		return realName;
	}
}
