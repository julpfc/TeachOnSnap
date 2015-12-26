package com.julvez.pfc.teachonsnap.manager.mail;



public interface MailManager {

	public boolean send(String address, String subject, String body);
	
	public boolean sendHTML(String address, String subject, String body);

	public boolean broadcastHTML(String addresses, String subject, String body);
}
