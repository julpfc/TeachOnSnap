package com.julvez.pfc.teachonsnap.ut.manager.property;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.manager.mail.javamail.MailPropertyName;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.url.model.URLPropertyName;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class PropertyManagerTest extends ManagerTest<PropertyManager> {

	@Test
	public void testGetPropertyEnumOfQ() {
		Assert.assertNull(test.getProperty(null));		
		Assert.assertEquals("localhost", test.getProperty(URLPropertyName.TEACHONSNAP_HOST));
		Assert.assertNull(test.getProperty(LessonPropertyName.MAX_PAGE_RESULTS));
	}

	@Test
	public void testGetPropertyEnumOfQString() {
		Assert.assertNull(test.getProperty(null, NULL_STRING));
		Assert.assertNull(test.getProperty(null, BLANK_STRING));
		
		Assert.assertEquals("101", test.getProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "1"));
		
		Assert.assertNull(test.getProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "2"));
		
		Assert.assertNull(test.getProperty(LessonPropertyName.MAX_PAGE_RESULTS, NULL_STRING));
		Assert.assertNull(test.getProperty(LessonPropertyName.MAX_PAGE_RESULTS, BLANK_STRING));
	}

	@Test
	public void testGetNumericPropertyEnumOfQ() {
		Assert.assertEquals(-1, test.getNumericProperty(null));		
		Assert.assertEquals(100, test.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE));
		Assert.assertEquals(-1, test.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS));
	}

	@Test
	public void testGetNumericPropertyEnumOfQString() {
		Assert.assertEquals(-1, test.getNumericProperty(null, NULL_STRING));
		Assert.assertEquals(-1, test.getNumericProperty(null, BLANK_STRING));
		
		Assert.assertEquals(101, test.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "1"));
		
		Assert.assertEquals(-1, test.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "2"));
		
		Assert.assertEquals(-1, test.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS, NULL_STRING));
		Assert.assertEquals(-1, test.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS, BLANK_STRING));
	}

	@Test
	public void testGetBooleanPropertyEnumOfQ() {
		Assert.assertFalse(test.getBooleanProperty(null));		
		Assert.assertTrue(test.getBooleanProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE));
		Assert.assertFalse(test.getBooleanProperty(LessonPropertyName.MAX_PAGE_RESULTS));
	}

	@Test
	public void testGetBooleanPropertyEnumOfQString() {
		Assert.assertFalse(test.getBooleanProperty(null, NULL_STRING));
		Assert.assertFalse(test.getBooleanProperty(null, BLANK_STRING));
		
		Assert.assertTrue(test.getBooleanProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "1"));
		
		Assert.assertFalse(test.getBooleanProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "2"));
		
		Assert.assertFalse(test.getBooleanProperty(LessonPropertyName.MAX_PAGE_RESULTS, NULL_STRING));
		Assert.assertFalse(test.getBooleanProperty(LessonPropertyName.MAX_PAGE_RESULTS, BLANK_STRING));
	}

	@Test
	public void testGetListPropertyEnumOfQ() {
		List<String> mails = new ArrayList<String>();
		mails.add("uc3m.es");
		mails.add("it.uc3m.es");
		mails.add("gmail.com");
		
		Assert.assertNull(test.getListProperty(null));		
		
		List<String> list = test.getListProperty(UserPropertyName.VERIFIED_EMAIL_DOMAINS);
		Assert.assertNotNull(list);
		
		int i = 0;
		for(String s:list){
			Assert.assertEquals(mails.get(i++), s);
		}
		
		Assert.assertNull(test.getListProperty(LessonPropertyName.MAX_PAGE_RESULTS));
	}

	@Test
	public void testGetListPropertyEnumOfQString() {
		Assert.assertNull(test.getListProperty(null));		
		
		List<String> list = test.getListProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE_EXCEPTION, "1");
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("101", list.get(0));
		
		
		Assert.assertNull(test.getListProperty(LessonPropertyName.MAX_PAGE_RESULTS, NULL_STRING));
		Assert.assertNull(test.getListProperty(LessonPropertyName.MAX_PAGE_RESULTS, EMPTY_STRING));
	}

	@Test
	public void testGetPasswordProperty() {
		Assert.assertNull(test.getPasswordProperty(null));		
		Assert.assertEquals("secretmail", test.getPasswordProperty(MailPropertyName.JAVAMAIL_AUTH_PASS));
		Assert.assertNull(test.getPasswordProperty(LessonPropertyName.MAX_PAGE_RESULTS));
	}
}
