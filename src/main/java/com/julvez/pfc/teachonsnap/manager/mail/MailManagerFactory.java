package com.julvez.pfc.teachonsnap.manager.mail;

import com.julvez.pfc.teachonsnap.manager.mail.javamail.MailManagerJavaMail;

public class MailManagerFactory {

private static MailManager manager;
	
	public static MailManager getManager(){
		if(manager==null){
			manager = new MailManagerJavaMail();
		}
		return manager;
	}
}
