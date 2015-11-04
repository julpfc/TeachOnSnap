package com.julvez.pfc.teachonsnap.manager.mail;


public interface MailManager {

	//TODO Ver cómo prpagar errores de envío o direcciones, etc
	public boolean send(String address, String subject, String body);
}
