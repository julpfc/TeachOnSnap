package com.julvez.pfc.teachonsnap.ut.manager.cache.map;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.map.CacheManagerMap;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.ut.manager.cache.CacheManagerTest;

public class CacheManagerMapTest extends CacheManagerTest {

	@Mock
	private StringManager stringManager;
	
	@Mock
	private LogManager logger;
	
	@Override
	protected CacheManager getManager() {
		return new CacheManagerMap(stringManager, logger);
	}

	
	@Test
	public void testExecuteImplCached() {
		Mockito.when(stringManager.getKey(Mockito.anyObject())).thenReturn("[1]");
		super.testExecuteImplCached();
	}

	@Test
	public void testUpdateImplCached() {
		Mockito.when(stringManager.getKey(Mockito.anyObject())).thenReturn("[1]");
		super.testUpdateImplCached();
	}

	@Test
	public void testClearCache() {
		Mockito.when(stringManager.getKey(Mockito.anyObject())).thenReturn("[1]");
		super.testClearCache();
		Mockito.verify(logger, Mockito.atLeastOnce()).debug(Mockito.anyString());
	}

	@Test
	public void testIncCacheValue() {
		Mockito.when(stringManager.getKey(Mockito.anyObject())).thenReturn("[1]");
		Mockito.when(stringManager.isEmpty(Mockito.anyString())).thenReturn(false);
		Mockito.when(stringManager.isEmpty(BLANK_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(EMPTY_STRING)).thenReturn(true);
		Mockito.when(stringManager.isEmpty(NULL_STRING)).thenReturn(true);
		
		super.testIncCacheValue();		
		
		Mockito.verify(logger, Mockito.atLeastOnce()).debug(Mockito.anyString());
	}
}
