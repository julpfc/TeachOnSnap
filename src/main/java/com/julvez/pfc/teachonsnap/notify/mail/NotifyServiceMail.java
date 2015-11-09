package com.julvez.pfc.teachonsnap.notify.mail;

import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.user.model.User;

public class NotifyServiceMail implements NotifyService {
	
	private MailManager mailManager = MailManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	public boolean info(User user, String message) {
		return info(user, null, message);
	}

	@Override
	public boolean info(User user, String subject, String message) {
		return info(user, subject, message, null);
	}

	@Override
	public boolean info(User user, String subject, String message, String optionalURL) {
		// TODO sacar de algún lado
		String mailSubject = "Teach On Snap";
		
		if(!stringManager.isEmpty(subject)){
			mailSubject = mailSubject + " - " + subject;
		}
		
		//TODO Meter HTML plantilla bonica 
		String mailMessage = message;
		
		//TODO COnvertir la URL en absoluta si no viene(URL service), comprobar si null, añadir al mail
					
		return mailManager.send(user.getEmail(), mailSubject, mailMessage);
	}

}
