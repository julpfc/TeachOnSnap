package com.julvez.pfc.teachonsnap.notify.mail;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.model.NotifyMessageKey;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the CommentService interface, uses an internal {@link MailManager} 
 * to send mail notifications.
 */
public class NotifyServiceMail implements NotifyService {
	
	/** Mail manager providing mailing capabilities */
	private MailManager mailManager;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Provides the functionality to work with application's URLs. */	
	private URLService urlService;
	
	/** Provides the functionality to work with localized texts. */
	private TextService textService;
	
	/** Provides the functionality to work with different languages to the application */
	private LangService langService;
		
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param mailManager Mail manager providing mailing capabilities
	 * @param stringManager String manager providing string manipulation utilities
	 * @param urlService Provides the functionality to work with application's URLs.
	 * @param textService Provides the functionality to work with localized texts.
	 * @param langService Provides the functionality to work with different languages to the application
	 */
	public NotifyServiceMail(MailManager mailManager,
			StringManager stringManager, URLService urlService,
			TextService textService, LangService langService) {
		
		if(mailManager == null || stringManager == null || urlService == null 
				|| textService == null || langService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.mailManager = mailManager;
		this.stringManager = stringManager;
		this.urlService = urlService;
		this.textService = textService;
		this.langService = langService;
	}

	@Override
	public boolean info(User user, String message) {
		return info(user, null, message);
	}

	@Override
	public boolean info(User user, String subject, String message) {		
		if(user != null){
			String mailSubject = "TeachOnSnap";
			
			//build subject
			if(!stringManager.isEmpty(subject)){
				mailSubject = mailSubject + " - " + subject;
			}
			
			//Format message in HTML template
			String mailMessage = textService.getLocalizedText(langService.getDefaultLanguage(), 
					NotifyMessageKey.HTML_TEMPLATE,	message, urlService.getHost());
						
			return mailManager.sendHTML(user.getEmail(), mailSubject, mailMessage);
		}
		else return false;
	}

	@Override
	public boolean broadcast(List<User> users, String subject, String message) {
		if(users != null){
						
			String emails = null;
			
			//Get adressee list
			for(User user:users){
				if(!stringManager.isEmpty(user.getFirstName())){
					emails = (emails==null?"":(emails + ",")) + user.getEmail();
				}
			}
			
			//if not empty adresees
			if(emails.length()>0){
				String mailSubject = "TeachOnSnap";
			
				//build 
				if(!stringManager.isEmpty(subject)){
					mailSubject = mailSubject + " - " + subject;
				}
				
				//if no HTML tags are used, then enphasis on text messagge
				if(!message.contains("<")){
					message = "<h3>" + message + "</h3>";
				}
				
				//Format message in HTML template
				String mailMessage = textService.getLocalizedText(langService.getDefaultLanguage(), 
						NotifyMessageKey.HTML_BROADCAST_TEMPLATE, message, urlService.getHost());

				return mailManager.broadcastHTML(emails, mailSubject, mailMessage);
			}
			else return false;
		}
		else return false;
	}

}
