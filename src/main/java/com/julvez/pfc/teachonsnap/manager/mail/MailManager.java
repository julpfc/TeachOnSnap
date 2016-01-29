package com.julvez.pfc.teachonsnap.manager.mail;

/** Mail manager providing mailing capabilities */
public interface MailManager {

	/**
	 * Sends a text-plain email.
	 * @param address whom the mail is sent
	 * @param subject mail's subject
	 * @param body mail's text-plain body
	 * @return true if email is successfully sent.
	 */
	public boolean send(String address, String subject, String body);
	
	/**
	 * Sends a HTML email. 
	 * @param address whom the mail is sent
	 * @param subject mail's subject
	 * @param body mail's HTML body
	 * @return true if email is successfully sent.
	 */
	public boolean sendHTML(String address, String subject, String body);

	/**
	 * Sends a broadcst to a comma-separated list of emails.
	 * @param addresses broadcast destinations
	 * @param subject mail's subject
	 * @param body mail's body in HTML
	 * @return true if broadcast is successfully sent.
	 */
	public boolean broadcastHTML(String addresses, String subject, String body);
}
