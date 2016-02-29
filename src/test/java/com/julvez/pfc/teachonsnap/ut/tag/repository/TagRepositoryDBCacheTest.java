package com.julvez.pfc.teachonsnap.ut.tag.repository;

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

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryDB;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryDBCache;

public class TagRepositoryDBCacheTest extends TagRepositoryTest {

	@Mock
	private TagRepositoryDB repoDB;	
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected TagRepository getRepository() {		
		return new TagRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetLessonIDsFromTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testGetLessonIDsFromTag();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());
	}

	@Test
	public void testGetLessonTagIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		
		super.testGetLessonTagIDs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());
	}

	@Test
	public void testGetTag() {
		Tag tag = new Tag();	
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdTag))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idTag))).thenReturn(tag);
		
		super.testGetTag();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetTagUseCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,0});
		ids.add(new Object[]{2,0});
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetTagUseCloudTags();
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetAuthorCloudTags() {
		List<Object[]> ids = new ArrayList<Object[]>();
		ids.add(new Object[]{1,0});
		ids.add(new Object[]{2,0});
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetAuthorCloudTags();
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testAddLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(null, null, null, ids);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testAddLessonTags();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testGetTagID() {
		when(cache.executeImplCached(eq(repoDB), anyString())).thenReturn(invalidIdTag);
		when(cache.executeImplCached(eq(repoDB), eq(query))).thenReturn(idTag);
		super.testGetTagID();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Test
	public void testCreateTag() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString())).thenReturn(invalidIdTag);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(query))).thenReturn(idTag);
		
		super.testCreateTag();
		
		verify(cache, times(4)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString());
	}

	@Test
	public void testRemoveLessonTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson)))
		.thenReturn(ids, ids, ids, null);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testRemoveLessonTags();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSearchTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchTag();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());
	}

	@Test
	public void testGetTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(cache.executeImplCached(eq(repoDB), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetTags();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB),anyInt());
	}

	@Test
	public void testGetTagSearchCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetTagSearchCloudTags();
		verify(cache).executeImplCached(eq(repoDB));
	}

	@Test
	public void testGetLessonViewCloudTags() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetLessonViewCloudTags();
		verify(cache).executeImplCached(eq(repoDB));
	}
}
