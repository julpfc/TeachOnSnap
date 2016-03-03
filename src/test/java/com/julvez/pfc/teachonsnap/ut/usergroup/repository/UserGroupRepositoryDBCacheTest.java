package com.julvez.pfc.teachonsnap.ut.usergroup.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyShort;
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
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepositoryDB;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepositoryDBCache;

public class UserGroupRepositoryDBCacheTest extends UserGroupRepositoryTest {

	@Mock
	private UserGroupRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected UserGroupRepository getRepository() {
		return new UserGroupRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetGroups() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetGroups();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testSearchGroupsByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchGroupsByName();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());	
	}

	@Test
	public void testGetGroup() {
		UserGroup group = new UserGroup();
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUserGroup))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup))).thenReturn(group);
		
		super.testGetGroup();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());
	}

	@Test
	public void testGetGroupMembers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUserGroup))).thenReturn(null);
		
		super.testGetGroupMembers();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());	
	}

	@Test
	public void testCreateGroup() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString())).thenReturn(invalidIdUserGroup);
		when(cache.updateImplCached(eq(repoDB), eq((String[])null), eq((String[])null), eq(name))).thenReturn(idUserGroup);
		
		super.testCreateGroup();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString());
	}

	@Test
	public void testAddUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testAddUser();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}

	@Test
	public void testSaveGroupName() {
		UserGroup group = new UserGroup();
		group.setGroupName(EMPTY_STRING);
		
		UserGroup groupMod = new UserGroup();
		groupMod.setGroupName(name);	
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup))).thenReturn(group, group, groupMod);
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyString()))
		.thenReturn(false, true);
		
		super.testSaveGroupName();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyString());
	}

	@Test
	public void testRemoveUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testRemoveUser();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}

	@Test
	public void testRemoveGroup() {
		UserGroup group = new UserGroup();
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
		.thenReturn(group, group, null);
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort()))
			.thenReturn(false);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(idUserGroup)))
			.thenReturn(true);
				
		super.testRemoveGroup();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort());
	}

	@Test
	public void testGetGroupsFromUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetGroupsFromUser();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetAuthorFollowings() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUserGroup))).thenReturn(null);
		
		super.testGetAuthorFollowings();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());	
	}

	@Test
	public void testFollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testFollowAuthor();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}

	@Test
	public void testUnfollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testUnfollowAuthor();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}

	@Test
	public void testGetTagFollowings() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUserGroup))).thenReturn(null);
		
		super.testGetTagFollowings();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());	
	}

	@Test
	public void testFollowTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testFollowTag();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}

	@Test
	public void testUnfollowTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
	
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt()))
			.thenReturn(false, false, true);
	
		super.testUnfollowTag();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyShort(), anyInt());
	}
}
