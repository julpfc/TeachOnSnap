package com.julvez.pfc.teachonsnap.manager.mail.javamail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;

public class MailManagerJavaMail implements MailManager {

	private static String JAVAMAIL_SMTP_HOST = "mail.smtp.host";
	private static String JAVAMAIL_SMTP_PORT = "mail.smtp.port";
	private static String JAVAMAIL_SMTP_AUTH = "mail.smtp.auth";
	private static String JAVAMAIL_SMTP_STARTTLS = "mail.smtp.starttls.enable";
	
	private PropertyManager propertyManager = PropertyManagerFactory.getManager();
	private LogManager logger = LogManagerFactory.getManager();
	
	@Override
	public boolean send(String address, String subject, String body) {
		return send(address, subject, body, false);
	}

	@Override
	public boolean sendHTML(String address, String subject, String body) {
		return send(address, subject, body, true);
	}
	
	private boolean send(String address, String subject, String body, boolean isHTML) {
		
		boolean success = true;

		Properties props = new Properties();
		props.put(JAVAMAIL_SMTP_HOST,propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_HOST));
		props.put(JAVAMAIL_SMTP_PORT,propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_PORT));
		
		boolean auth = propertyManager.getProperty(MailPropertyName.JAVAMAIL_AUTH_USER)!=null;
		props.put(JAVAMAIL_SMTP_AUTH,auth?"true":"false");
		
		props.put(JAVAMAIL_SMTP_STARTTLS, propertyManager.getProperty(MailPropertyName.JAVAMAIL_SMTP_STARTTLS));
		
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
			message.setFrom(new InternetAddress(propertyManager.getProperty(MailPropertyName.JAVAMAIL_SENDER)));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(address));
			message.setSubject(subject);
			if(isHTML){
				message.setContent(body, "text/html; charset=utf-8");
			}
			else{
				message.setText(body);
			}

			logger.startTimer();
			logger.info("MailManager: Enviando mail a "+address + "[Subject=" + subject + "]");
			Transport.send(message);
			logger.infoTime("MailManager: Mail enviado a  "+address + "[Subject=" + subject + "]");
			
		} 
		catch (Throwable t) {
			logger.error(t, "Error enviando mail: " + address + "[Subject=" + subject + "]");			
			success = false;
		}
		return success;
	}

}
