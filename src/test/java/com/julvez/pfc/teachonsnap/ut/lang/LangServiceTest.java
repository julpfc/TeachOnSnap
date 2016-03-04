package com.julvez.pfc.teachonsnap.ut.lang;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class LangServiceTest extends ServiceTest<LangService> {

	protected short idLanguage = 1;
	protected short invalidIdLanguage = -1;
	protected String language = "en";
	
	@Test
	public void testGetLanguageString() {
		Language lang = test.getLanguage(language);
		assertNotNull(lang);
		assertEquals(language, lang.getLanguage());
		
		assertNull(test.getLanguage(BLANK_STRING));
		assertNull(test.getLanguage(EMPTY_STRING));
		assertNull(test.getLanguage(NULL_STRING));
	}

	@Test
	public void testGetLanguageShort() {
		Language lang = test.getLanguage(idLanguage);
		assertNotNull(lang);
		assertEquals(idLanguage, lang.getId());
		
		assertNull(test.getLanguage(invalidIdLanguage));		
	}

	@Test
	public void testGetUserSessionLanguage() {
		// Language priority
		// paramLang > visitUserLang > visitLang(Anon user) > acceptHeaderLang > defaultLang
		
		Language lang = test.getUserSessionLanguage(NULL_STRING, null, null);
		assertNotNull(lang);
		assertEquals(idLanguage, lang.getId());
		
		lang = test.getUserSessionLanguage(language, null, null);
		assertNotNull(lang);
		assertEquals(language, lang.getLanguage());
		
		Visit visit = mock(Visit.class);
		when(visit.getIdLanguage()).thenReturn(idLanguage);
		
		lang = test.getUserSessionLanguage(language, visit, null);
		assertNotNull(lang);
		assertEquals(idLanguage, lang.getId());
		
		User user = mock(User.class);
		when(user.getIdLanguage()).thenReturn(idLanguage);
		when(visit.getUser()).thenReturn(user);
		
		lang = test.getUserSessionLanguage(language, visit, null);
		assertNotNull(lang);
		assertEquals(idLanguage, lang.getId());
		
		lang = test.getUserSessionLanguage(language, visit, language);
		assertNotNull(lang);
		assertEquals(language, lang.getLanguage());
	}

	@Test
	public void testGetDefaultLanguage() {
		Language lang = test.getDefaultLanguage();
		assertNotNull(lang);
	}

	@Test
	public void testGetAllLanguages() {
		List<Language> langs = test.getAllLanguages();
		assertNotNull(langs);
		
		short i = 1;
		for(Language lang:langs){
			assertEquals(i++, lang.getId());
		}
	}

}
