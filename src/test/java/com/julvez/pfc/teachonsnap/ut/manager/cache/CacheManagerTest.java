package com.julvez.pfc.teachonsnap.ut.manager.cache;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryDB;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class CacheManagerTest extends ManagerTest<CacheManager> {

	@Mock
	StatsRepositoryDB repoDB;
	
	private Object getTagViewsCount(Object... params){
		return test.executeImplCached(repoDB, params);
	}
	
	private Object saveTag(String[] cacheKeys, String[] cacheNames, Object... params){
		return test.updateImplCached(repoDB, cacheKeys, cacheNames, params);
	}
	
	@Test
	public void testExecuteImplCached() {
		int idTag = 1;
		
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(10);
		
		Assert.assertNull(test.executeImplCached(null, idTag));
		Assert.assertNull(test.executeImplCached(repoDB, (Object[])null));
		
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(11);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));		
		
		Mockito.verify(repoDB).getTagViewsCount(Mockito.anyInt());		
	}

	@Test
	public void testUpdateImplCached() {
		int idTag = 1;
		int idVisit = 2;
		
		Assert.assertNull(test.updateImplCached(null, null, null, idVisit, idTag));
		Assert.assertNull(test.updateImplCached(repoDB, null, null, (Object[])null));
		
		Mockito.when(repoDB.saveTag(idVisit, idTag)).thenReturn(true);
		Assert.assertTrue((boolean)saveTag(null, null, idVisit, idTag));
		
		Mockito.when(repoDB.saveTag(idVisit, idTag)).thenReturn(false);
		Assert.assertFalse((boolean)saveTag(null, null, idVisit, idTag));
						
		Mockito.verify(repoDB, Mockito.atLeast(2)).saveTag(Mockito.anyInt(), Mockito.anyInt());
		
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(10);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(11);
		Assert.assertFalse((boolean)saveTag(new String[]{"[1]"}, new String[]{"getTagViewsCount"}, idVisit, idTag));
		
		Assert.assertEquals(11, (int)getTagViewsCount(idTag));
	}

	@Test
	public void testClearCache() {
		int idTag = 1;
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(10);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));		
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(11);
		test.clearCache(NULL_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));		
		test.clearCache(EMPTY_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.clearCache(BLANK_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.clearCache("getTagViewsCount");
		Assert.assertEquals(11, (int)getTagViewsCount(idTag));
	}

	@Test
	public void testIncCacheValue() {
		int idTag = 1;
		Mockito.when(repoDB.getTagViewsCount(idTag)).thenReturn(10);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));		
		test.incCacheValue(NULL_STRING, NULL_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));		
		test.incCacheValue(EMPTY_STRING, EMPTY_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.incCacheValue(BLANK_STRING, BLANK_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.incCacheValue("getTagViewsCount", BLANK_STRING);
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.incCacheValue(BLANK_STRING, "[1]");
		Assert.assertEquals(10, (int)getTagViewsCount(idTag));
		test.incCacheValue("getTagViewsCount","[1]");
		Assert.assertEquals(11, (int)getTagViewsCount(idTag));
	}

}
