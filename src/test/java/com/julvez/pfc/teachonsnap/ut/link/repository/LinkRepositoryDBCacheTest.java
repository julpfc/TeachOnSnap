package com.julvez.pfc.teachonsnap.ut.link.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.link.model.Link;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryDB;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryDBCache;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public class LinkRepositoryDBCacheTest extends LinkRepositoryTest {

	@Mock
	private LinkRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected LinkRepository getRepository() {		
		return new LinkRepositoryDBCache(repoDB, cache, stringManager);
	}

	@Test
	public void testGetMoreInfoLinkIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		
		super.testGetMoreInfoLinkIDs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());
	}

	@Test
	public void testGetSourceLinkIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		
		super.testGetSourceLinkIDs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());
	}

	@Test
	public void testGetLink() {
		Link link = new Link();	
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLink))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLink))).thenReturn(link);
		
		super.testGetLink();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetLinkID() {
		when(cache.executeImplCached(eq(repoDB), anyString())).thenReturn(invalidIdLink);
		when(cache.executeImplCached(eq(repoDB), eq(url))).thenReturn(idLink);
		super.testGetLinkID();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Test
	public void testCreateLink() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString(), anyString())).thenReturn(invalidIdLink);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(url), eq(url))).thenReturn(idLink);
		super.testCreateLink();
		
		verify(cache, times(4)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString(), anyString());
	}

	@Test
	public void testAddLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(null, null, null, ids);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testAddLessonSources();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testAddLessonMoreInfos() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(null, null, null, ids);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testAddLessonMoreInfos();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testRemoveLessonSources() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(ids, ids, ids, null);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testRemoveLessonSources();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testRemoveLessonMoreInfos() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(ids, ids, ids, null);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testRemoveLessonMoreInfos();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

}
