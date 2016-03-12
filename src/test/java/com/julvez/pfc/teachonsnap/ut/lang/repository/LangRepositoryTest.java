package com.julvez.pfc.teachonsnap.ut.lang.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class LangRepositoryTest extends RepositoryTest<LangRepository> {

	protected short idLanguage = 2;
	protected String language = "es";
	protected short invalidIdLanguage = -1;
	
	@Test
	public void testGetLanguage() {
		Language lang = test.getLanguage(idLanguage);
		assertNotNull(lang);
		assertEquals(language, lang.getLanguage());
		
		assertNull(test.getLanguage(invalidIdLanguage));
	}

	@Test
	public void testGetIdLanguage() {
		assertEquals(idLanguage, test.getIdLanguage(language));
		
		assertEquals(invalidIdLanguage, test.getIdLanguage(NULL_STRING));
		assertEquals(invalidIdLanguage, test.getIdLanguage(EMPTY_STRING));
		assertEquals(invalidIdLanguage, test.getIdLanguage(BLANK_STRING));
	}

	@Test
	public void testGetDefaultIdLanguage() {
		assertEquals(1, test.getDefaultIdLanguage());
	}

	@Test
	public void testGetAllLanguages() {
		List<Byte> ids = test.getAllLanguages();
		
		assertNotNull(ids);
		
		byte i=1;
		for(byte b:ids){
			assertEquals(i++, b);
		}		
	}
}
