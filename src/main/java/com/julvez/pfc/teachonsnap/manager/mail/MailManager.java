package com.julvez.pfc.teachonsnap.manager.mail;


public interface MailManager {

	//Ver cómo prpagar errores de envío o direcciones, etc
	public void send(String address, String subject, String body);
}
