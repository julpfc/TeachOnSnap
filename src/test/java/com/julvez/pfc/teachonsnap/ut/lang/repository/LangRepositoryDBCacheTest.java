package com.julvez.pfc.teachonsnap.ut.lang.repository;

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
import com.julvez.pfc.teachonsnap.lang.repository.LangRepositoryDBCache;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;

public class LangRepositoryDBCacheTest extends LangRepositoryTest {

	@Mock
	private CacheManager cache;
	
	@Mock
	private LangRepositoryDB repoDB;
	
	@Override
	protected LangRepository getRepository() {
		return new LangRepositoryDBCache(repoDB, cache);
	}

	@Override
	public void testGetLanguage() {
		Language lang = new Language();
		lang.setLanguage("es");
		
		when(cache.executeImplCached(eq(repoDB), eq((short)-1))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLanguage))).thenReturn(lang);
		
		super.testGetLanguage();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyShort());
	}

	@Override
	public void testGetIdLanguage() {
		when(cache.executeImplCached(eq(repoDB), eq("es"))).thenReturn((short)2);
	
		super.testGetIdLanguage();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Override
	public void testGetDefaultIdLanguage() {
		when(cache.executeImplCached(eq(repoDB))).thenReturn((short)1);
		
		super.testGetDefaultIdLanguage();
		
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Override
	public void testGetAllLanguages() {
		List<Byte> ids = new ArrayList<Byte>();
		ids.add((byte)1);
		ids.add((byte)2);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetAllLanguages();
		verify(cache).executeImplCached(eq(repoDB));
	}
}
