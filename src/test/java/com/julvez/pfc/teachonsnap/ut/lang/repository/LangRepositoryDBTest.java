package com.julvez.pfc.teachonsnap.ut.lang.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyShort;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepository;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepositoryDB;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;

public class LangRepositoryDBTest extends LangRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Override
	protected LangRepository getRepository() {
		return new LangRepositoryDB(dbm);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void testGetLanguage() {
		Language lang = new Language();
		lang.setLanguage("es");
		
		when(dbm.getQueryResultUnique(eq("SQL_LANG_GET_LANGUAGE"), eq(Language.class), eq(-1))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LANG_GET_LANGUAGE"), eq(Language.class), eq(idLanguage))).thenReturn(lang);
		super.testGetLanguage();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LANG_GET_LANGUAGE"), any(Class.class), anyShort());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void testGetIdLanguage() {
		when(dbm.getQueryResultUnique(eq("SQL_LANG_GET_IDLANGUAGE_FROM_LANGUAGE"), any(Class.class), eq("es"))).thenReturn((short)2);
			
		super.testGetIdLanguage();
		
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_LANG_GET_IDLANGUAGE_FROM_LANGUAGE"), any(Class.class), anyString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void testGetDefaultIdLanguage() {
		when(dbm.getQueryResultUnique(eq("SQL_LANG_GET_DEFAULT_IDLANGUAGE"), any(Class.class))).thenReturn((short)1);
		super.testGetDefaultIdLanguage();
		verify(dbm).getQueryResultUnique(eq("SQL_LANG_GET_DEFAULT_IDLANGUAGE"), any(Class.class));
	}

	@Override
	public void testGetAllLanguages() {
		List<Byte> ids = new ArrayList<Byte>();
		ids.add((byte)1);
		ids.add((byte)2);
		
		when(dbm.getQueryResultList(eq("SQL_LANG_GET_ALL_LANGUAGES"), eq(Byte.class))).thenReturn(ids);
		super.testGetAllLanguages();
		verify(dbm).getQueryResultList(eq("SQL_LANG_GET_ALL_LANGUAGES"), eq(Byte.class));
	}
}
