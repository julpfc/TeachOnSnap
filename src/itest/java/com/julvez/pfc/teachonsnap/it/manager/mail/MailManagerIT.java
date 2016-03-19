package com.julvez.pfc.teachonsnap.it.manager.mail;

import static org.junit.Assert.*;

import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.mail.MailManagerTest;

public class MailManagerIT extends MailManagerTest {

	private String emailTest = "teachonsnap+itest@gmail.com";
	
	String subject = "TeachOnSnap: " + this.getClass().getName();
			
	private String body = "This is an integration test for MailManager. "
							+ "You should receive 3 mails on: " + emailTest;
	
	@Override
	protected MailManager getManager() {
		return MailManagerFactory.getManager();
	}

	@Override
	public void testSend() {
		String testSubject = subject + ".send()";
		String testBody = body + ". This body should be text-plain.";
		
		assertFalse(test.send(NULL_STRING, testSubject, testBody));
		assertFalse(test.send(EMPTY_STRING, testSubject, testBody));
		assertFalse(test.send(BLANK_STRING, testSubject, testBody));
		
		assertTrue(test.send(emailTest, testSubject, testBody));
	}

	@Override
	public void testSendHTML() {
		String testSubject = subject + ".sendHTML()";
		String testBody = body + ". <h1>This body should be in HTML.</h1>";
		
		assertFalse(test.sendHTML(NULL_STRING, testSubject, testBody));
		assertFalse(test.sendHTML(EMPTY_STRING, testSubject, testBody));
		assertFalse(test.sendHTML(BLANK_STRING, testSubject, testBody));
		
		assertTrue(test.sendHTML(emailTest, testSubject, testBody));
	}

	@Override
	public void testBroadcastHTML() {
		String testSubject = subject + ".broadcastHTML()";		
		String testBody = body + ". The adresee should be on BCC. <h1>This body should be in HTML.</h1>";
		
		assertFalse(test.broadcastHTML(NULL_STRING, testSubject, testBody));
		assertFalse(test.broadcastHTML(EMPTY_STRING, testSubject, testBody));
		assertFalse(test.broadcastHTML(BLANK_STRING, testSubject, testBody));
		
		assertTrue(test.broadcastHTML(emailTest, testSubject, testBody));
	}
}
