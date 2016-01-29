package com.julvez.pfc.teachonsnap.manager.mail.javamail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;

/**
 * Implementation of the StringManager, uses internal {@link LogManager} 
 * to log the errors and {@link PropertyManager} to access application's properties.
 */
public class MailManagerJavaMail implements MailManager {

	/** SMTP host property key */
	private static String JAVAMAIL_SMTP_HOST = "mail.smtp.host";
	
	/** SMTP port property key */
	private static String JAVAMAIL_SMTP_PORT = "mail.smtp.port";
	
	/** SMTP authentication property key */
	private static String JAVAMAIL_SMTP_AUTH = "mail.smtp.auth";
	
	/** SMTP enable TLS property key */
	private static String JAVAMAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
	
	/** Property manager providing access to properties files */
	private PropertyManager propertyManager;
	
	/** Log manager providing logging capabilities */
	private LogManager logger;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param propertyManager Property manager providing access to properties files
	 * @param logger Log manager providing logging capabilities
	 */
	public MailManagerJavaMail(PropertyManager propertyManager, LogManager logger) {
		if(propertyManager == null || logger == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.propertyManager = propertyManager;
		this.logger = logger;
	}
	
	
	@Override
	public boolean send(String address, String subject, String body) {
		return send(address, subject, body, false, false);
	}

	@Override
	public boolean sendHTML(String address, String subject, String body) {
		return send(address, subject, body, true, false);
	}
	
	@Override
	public boolean broadcastHTML(String addresses, String subject, String body) {
		return send(addresses, subject, body, true, true);
	}
	
	/**
	 * Sends an email to one or several destinations in text-plain or HTML.
	 * @param addresses Destinations. Can specify several in comma-separated format.
	 * @param subject Mail's subject
	 * @param body Mail's body (text-plain or HTML)
	 * @param isHTML indicates if the body is HTML
	 * @param isBroadcast indicates if it's a broadcast to several destinations.
	 * @return true if is sent successfully
	 */
	private boolean send(String addresses, String subject, String body, boolean isHTML, boolean isBroadcast) {
		
		boolean success = true;

		//Setup JavaMail properties
		Properties props = new Properties();
		props.put(JAVAMAIL_SMTP_HOST,propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_HOST));
		props.put(JAVAMAIL_SMTP_PORT,propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_PORT));
		
		boolean auth = propertyManager.getProperty(MailPropertyName.JAVAMAIL_AUTH_USER)!=null;
		props.put(JAVAMAIL_SMTP_AUTH,auth?"true":"false");
		
		props.put(JAVAMAIL_SMTP_STARTTLS, propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_STARTTLS));
		
		//Setup JavaMail session
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
						propertyManager.getProperty(MailPropertyName.JAVAMAIL_AUTH_USER), 
						propertyManager.getProperty(MailPropertyName.JAVAMAIL_AUTH_PASS)
					);
				}
			}
		);

		try {			
			MimeMessage message = new MimeMessage(session);
			
			//Set sender
			message.setFrom(new InternetAddress(propertyManager.getProperty(MailPropertyName.JAVAMAIL_SENDER)));
			
			//Set destination
			if(isBroadcast){
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(propertyManager.getProperty(MailPropertyName.JAVAMAIL_SENDER)));
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(addresses));
			}
			else{
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(addresses));
			}
			
			//Set subject
			message.setSubject(subject);
			
			//Set body
			if(isHTML){
				message.setContent(body, "text/html; charset=utf-8");
			}
			else{
				message.setText(body);
			}

			//Send mail (benchmark)
			logger.startTimer();
			logger.info("MailManager: Sending mail to "+addresses + "[Subject=" + subject + "]");
			Transport.send(message);
			logger.infoTime("MailManager: Mail sent to "+addresses + "[Subject=" + subject + "]");
			
		} 
		catch (Throwable t) {
			logger.error(t, "Error sending mail: " + addresses + "[Subject=" + subject + "]");			
			success = false;
		}
		return success;
	}

}
