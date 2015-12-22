package com.julvez.pfc.teachonsnap.notify.mail;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.model.NotifyMessageKey;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class NotifyServiceMail implements NotifyService {
	
	private MailManager mailManager = MailManagerFactory.getManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	private URLService urlService = URLServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();

	@Override
	public boolean info(User user, String message) {
		return info(user, null, message);
	}

	@Override
	public boolean info(User user, String subject, String message) {		
		String mailSubject = "TeachOnSnap";
		
		if(!stringManager.isEmpty(subject)){
			mailSubject = mailSubject + " - " + subject;
		}
		
		//Meter HTML plantilla
		String mailMessage = textService.getLocalizedText(langService.getDefaultLanguage(), 
				NotifyMessageKey.HTML_TEMPLATE,	message, urlService.getHost());
					
		return mailManager.sendHTML(user.getEmail(), mailSubject, mailMessage);
	}

	

}
