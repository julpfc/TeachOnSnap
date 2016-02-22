package com.julvez.pfc.teachonsnap.ut.manager.text;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class TextManagerTest extends ManagerTest<TextManager> {

	@Override
	public void setUp() {		
		super.setUp();
		Locale.setDefault(Locale.UK);
	}

	@Test
	public void testGetLocalizedTextLocaleStringString() {
		Locale localeES = new Locale("es");
		Locale localeEN = new Locale("en");
		Locale localeEMPTY = new Locale(EMPTY_STRING);
		String bundle = "test";
		 
		Assert.assertNull(test.getLocalizedText(null, NULL_STRING, NULL_STRING));
		Assert.assertNull(test.getLocalizedText(localeES, NULL_STRING, bundle));
		Assert.assertNull(test.getLocalizedText(localeES, "test", NULL_STRING));
		Assert.assertNull(test.getLocalizedText(localeES, "test", EMPTY_STRING));
		
		Assert.assertEquals("prueba", test.getLocalizedText(localeES, "test", bundle));
		Assert.assertEquals("prueba {0}", test.getLocalizedText(localeES, "testParam", bundle));

		Assert.assertEquals("test", test.getLocalizedText(localeEN, "test", bundle));
		Assert.assertEquals("test {0}", test.getLocalizedText(localeEN, "testParam", bundle));
		
		Assert.assertEquals("test", test.getLocalizedText(localeEMPTY, "test", bundle));
		Assert.assertEquals("test {0}", test.getLocalizedText(localeEMPTY, "testParam", bundle));
	}

	@Test
	public void testGetLocalizedTextLocaleStringStringStringArray() {
		Locale localeES = new Locale("es");
		Locale localeEN = new Locale("en");
		Locale localeEMPTY = new Locale(EMPTY_STRING);
		String bundle = "test";
		
		Assert.assertNull(test.getLocalizedText(null, NULL_STRING, NULL_STRING, NULL_STRING));
		Assert.assertNull(test.getLocalizedText(localeES, NULL_STRING, bundle, "test"));
		Assert.assertNull(test.getLocalizedText(localeES, "test", NULL_STRING, "test"));
		Assert.assertNull(test.getLocalizedText(localeES, "test", EMPTY_STRING, "test"));
		
		Assert.assertEquals("prueba", test.getLocalizedText(localeES, "test", bundle, "test"));
		Assert.assertEquals("prueba", test.getLocalizedText(localeES, "test", bundle, NULL_STRING));
		Assert.assertEquals("prueba test", test.getLocalizedText(localeES, "testParam", bundle, "test"));
		Assert.assertEquals("prueba null", test.getLocalizedText(localeES, "testParam", bundle, NULL_STRING));

		Assert.assertEquals("test", test.getLocalizedText(localeEN, "test", bundle, "test"));
		Assert.assertEquals("test", test.getLocalizedText(localeEN, "test", bundle, NULL_STRING));
		Assert.assertEquals("test test", test.getLocalizedText(localeEN, "testParam", bundle, "test"));
		Assert.assertEquals("test null", test.getLocalizedText(localeEN, "testParam", bundle, NULL_STRING));
		
		Assert.assertEquals("test", test.getLocalizedText(localeEMPTY, "test", bundle, "test"));
		Assert.assertEquals("test", test.getLocalizedText(localeEMPTY, "test", bundle, NULL_STRING));
		Assert.assertEquals("test test", test.getLocalizedText(localeEMPTY, "testParam", bundle, "test"));
		Assert.assertEquals("test null", test.getLocalizedText(localeEMPTY, "testParam", bundle, NULL_STRING));
	}

}
