package com.julvez.pfc.teachonsnap.ut.text;

import org.junit.Assert;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class TextServiceTest extends ServiceTest<TextService> {

	public enum TestMessageKey{
		TEST("test"),
		TEST_PARAM("testParam");
		
		private final String realName;
	 
		private TestMessageKey(String property) {
			realName = property;
		}

		@Override
		public String toString() {
			return realName;
		}
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
						
		Assert.assertEquals("prueba", test.getLocalizedText(es, TestMessageKey.TEST));
		Assert.assertEquals("prueba {0}", test.getLocalizedText(es, TestMessageKey.TEST_PARAM));

		Assert.assertEquals("test", test.getLocalizedText(en, TestMessageKey.TEST));
		Assert.assertEquals("test {0}", test.getLocalizedText(en, TestMessageKey.TEST_PARAM));
		
		Assert.assertEquals("test", test.getLocalizedText(empty, TestMessageKey.TEST));
		Assert.assertEquals("test {0}", test.getLocalizedText(empty, TestMessageKey.TEST_PARAM));
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
				
		Assert.assertEquals("prueba", test.getLocalizedText(es, TestMessageKey.TEST, "test"));
		Assert.assertEquals("prueba", test.getLocalizedText(es, TestMessageKey.TEST, NULL_STRING));
		Assert.assertEquals("prueba test", test.getLocalizedText(es, TestMessageKey.TEST_PARAM, "test"));
		Assert.assertEquals("prueba null", test.getLocalizedText(es, TestMessageKey.TEST_PARAM, NULL_STRING));

		Assert.assertEquals("test", test.getLocalizedText(en, TestMessageKey.TEST, "test"));
		Assert.assertEquals("test", test.getLocalizedText(en, TestMessageKey.TEST, NULL_STRING));
		Assert.assertEquals("test test", test.getLocalizedText(en, TestMessageKey.TEST_PARAM, "test"));
		Assert.assertEquals("test null", test.getLocalizedText(en, TestMessageKey.TEST_PARAM, NULL_STRING));
		
		Assert.assertEquals("test", test.getLocalizedText(empty, TestMessageKey.TEST, "test"));
		Assert.assertEquals("test", test.getLocalizedText(empty, TestMessageKey.TEST, NULL_STRING));
		Assert.assertEquals("test test", test.getLocalizedText(empty, TestMessageKey.TEST_PARAM, "test"));
		Assert.assertEquals("test null", test.getLocalizedText(empty, TestMessageKey.TEST_PARAM, NULL_STRING));
	}
	
}
