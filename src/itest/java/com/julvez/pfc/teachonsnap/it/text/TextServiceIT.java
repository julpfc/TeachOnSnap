package com.julvez.pfc.teachonsnap.it.text;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.UserMessageKey;
import com.julvez.pfc.teachonsnap.ut.text.TextServiceTest;

public class TextServiceIT extends TextServiceTest {

	@Override
	public void setUp() {		
		Locale.setDefault(Locale.UK);
		super.setUp();
	}
	
	@Override
	protected TextService getService() {		
		return TextServiceFactory.getService();
	}

	@Test
	public void testGetLocalizedTextLanguageEnumOfQ() {
		Language es = new Language();
		es.setLanguage("es");
		
		Language en = new Language();
		en.setLanguage("en");
		
		Language empty = new Language();
		empty.setLanguage(EMPTY_STRING);		
				
		Assert.assertNull(test.getLocalizedText(null, null));
		Assert.assertNull(test.getLocalizedText(es, null));
						
		Assert.assertEquals("Nueva contraseña", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_SUBJECT));
		Assert.assertEquals("Para obtener tu nueva contraseña y poder acceder a TeachOnSnap, pincha aquí: {0}", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_MESSAGE));

		Assert.assertEquals("New password", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_SUBJECT));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: {0}", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_MESSAGE));
		
		Assert.assertEquals("New password", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_SUBJECT));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: {0}", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_MESSAGE));
	}

	@Test
	public void testGetLocalizedTextLanguageEnumOfQStringArray() {
		Language es = new Language();
		es.setLanguage("es");
		
		Language en = new Language();
		en.setLanguage("en");
		
		Language empty = new Language();
		empty.setLanguage(EMPTY_STRING);	
		
		Assert.assertNull(test.getLocalizedText(null, null, NULL_STRING));
		Assert.assertNull(test.getLocalizedText(es, null, "test"));
				
		Assert.assertEquals("Nueva contraseña", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_SUBJECT, "test"));
		Assert.assertEquals("Nueva contraseña", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_SUBJECT, NULL_STRING));
		Assert.assertEquals("Para obtener tu nueva contraseña y poder acceder a TeachOnSnap, pincha aquí: test", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_MESSAGE, "test"));
		Assert.assertEquals("Para obtener tu nueva contraseña y poder acceder a TeachOnSnap, pincha aquí: null", test.getLocalizedText(es, UserMessageKey.CHANGE_PASSWORD_MESSAGE, NULL_STRING));

		Assert.assertEquals("New password", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_SUBJECT, "test"));
		Assert.assertEquals("New password", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_SUBJECT, NULL_STRING));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: test", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_MESSAGE, "test"));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: null", test.getLocalizedText(en, UserMessageKey.CHANGE_PASSWORD_MESSAGE, NULL_STRING));
		
		Assert.assertEquals("New password", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_SUBJECT, "test"));
		Assert.assertEquals("New password", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_SUBJECT, NULL_STRING));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: test", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_MESSAGE, "test"));
		Assert.assertEquals("Click here in order to get a new password to access TeachOnSnap: null", test.getLocalizedText(empty, UserMessageKey.CHANGE_PASSWORD_MESSAGE, NULL_STRING));
	}
}
