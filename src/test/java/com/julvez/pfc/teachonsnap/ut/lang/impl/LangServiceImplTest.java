package com.julvez.pfc.teachonsnap.ut.lang.impl;

import static org.mockito.Matchers.anyShort;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.impl.LangServiceImpl;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepository;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.lang.LangServiceTest;

public class LangServiceImplTest extends LangServiceTest {

	@Mock
	private LangRepository langRepo;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected LangService getService() {
		return new LangServiceImpl(langRepo, stringManager);
	}
	
	@Test
	public void testGetLanguageString() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(eq(language))).thenReturn(false);
		
		Language lang = new Language();
		lang.setId(idLanguage);
		lang.setLanguage(language);
		
		when(langRepo.getLanguage(idLanguage)).thenReturn(lang);
		
		when(langRepo.getIdLanguage(language)).thenReturn(idLanguage);
		
		super.testGetLanguageString();
		
		verify(langRepo).getIdLanguage(anyString());
	}

	@Test
	public void testGetLanguageShort() {
		Language lang = new Language();
		lang.setId(idLanguage);
		lang.setLanguage(language);
		
		when(langRepo.getLanguage(idLanguage)).thenReturn(lang);
		
		super.testGetLanguageShort();
		
		verify(langRepo).getLanguage(anyShort());
	}

	@Test
	public void testGetUserSessionLanguage() {
		Language lang = new Language();
		lang.setId(idLanguage);
		lang.setLanguage(language);
		
		when(langRepo.getLanguage(idLanguage)).thenReturn(lang);
		
		when(langRepo.getDefaultIdLanguage()).thenReturn(idLanguage);
		
		super.testGetUserSessionLanguage();
	}

	@Test
	public void testGetDefaultLanguage() {
		Language lang = new Language();
		lang.setId(idLanguage);
		lang.setLanguage(language);
		
		when(langRepo.getLanguage(idLanguage)).thenReturn(lang);
		
		when(langRepo.getDefaultIdLanguage()).thenReturn(idLanguage);
		
		super.testGetDefaultLanguage();
		
		verify(langRepo).getDefaultIdLanguage();
	}

	@Test
	public void testGetAllLanguages() {
		Language lang = new Language();
		lang.setId(idLanguage);
		lang.setLanguage(language);
		
		when(langRepo.getLanguage(idLanguage)).thenReturn(lang);
		
		List<Byte> ids = new ArrayList<Byte>();
		ids.add((byte)idLanguage);
		
		when(langRepo.getAllLanguages()).thenReturn(ids);
		
		super.testGetAllLanguages();
		
		verify(langRepo).getAllLanguages();
	}
}
