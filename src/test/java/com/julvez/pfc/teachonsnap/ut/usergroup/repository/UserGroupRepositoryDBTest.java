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

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepositoryDB;

public class UserGroupRepositoryDBTest extends UserGroupRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected UserGroupRepository getRepository() {
		return new UserGroupRepositoryDB(dbm, properties);
	}
	
	@Test
	public void testGetGroups() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS"), eq(Short.class), eq(INVALID_RESULT), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS"), eq(Short.class), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS"), eq(Short.class), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetGroups();
		
		verify(dbm, times(3)).getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS"), eq(Short.class),anyInt(),anyInt());
		verify(properties, times(3)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSearchGroupsByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_SEARCH_GROUPIDS_BY_NAME"), eq(Short.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_SEARCH_GROUPIDS_BY_NAME"), eq(Short.class), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_SEARCH_GROUPIDS_BY_NAME"), eq(Short.class), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchGroupsByName();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_USERGROUP_SEARCH_GROUPIDS_BY_NAME"), eq(Short.class), anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetGroup() {
		UserGroup group = new UserGroup();	
		
		when(dbm.getQueryResultUnique(eq("SQL_USERGROUP_GET_GROUP"), eq(UserGroup.class), eq(invalidIdUserGroup))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_USERGROUP_GET_GROUP"), eq(UserGroup.class), eq(idUserGroup))).thenReturn(group);
		
		super.testGetGroup();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_USERGROUP_GET_GROUP"), eq(UserGroup.class), anyShort());
	}

	@Test
	public void testGetGroupMembers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUP_MEMBERIDS"), eq(Short.class), eq(invalidIdUserGroup))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUP_MEMBERIDS"), eq(Short.class), eq(idUserGroup))).thenReturn(ids);

		super.testGetGroupMembers();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USERGROUP_GET_GROUP_MEMBERIDS"), eq(Short.class), anyShort());
	}

	@Test
	public void testCreateGroup() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_USERGROUP_CREATE_GROUP"), anyString())).thenReturn((long)invalidIdUserGroup);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_USERGROUP_CREATE_GROUP"), eq(name))).thenReturn((long)idUserGroup);
		
		super.testCreateGroup();
		
		verify(dbm, times(2)).insertQueryAndGetLastInserID(eq("SQL_USERGROUP_CREATE_GROUP"), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUP_MEMBERIDS"), eq(Short.class), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_ADD_USER"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testAddUser();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_ADD_USER"), anyShort(), anyInt());
	}

	@Test
	public void testSaveGroupName() {
		UserGroup group = new UserGroup();
		group.setGroupName(EMPTY_STRING);
		
		UserGroup groupMod = new UserGroup();
		groupMod.setGroupName(name);		
		
		when(dbm.getQueryResultUnique(eq("SQL_USERGROUP_GET_GROUP"), eq(UserGroup.class), eq(idUserGroup)))
				.thenReturn(group, group, groupMod);	
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_SAVE_GROUPNAME"), anyString(), anyShort())).thenReturn(-1, 1);
		
		super.testSaveGroupName();
		verify(dbm, times(2)).updateQuery(eq("SQL_USERGROUP_SAVE_GROUPNAME"), anyString(), anyShort());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUP_MEMBERIDS"), eq(Short.class), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_REMOVE_USER"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testRemoveUser();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_REMOVE_USER"), anyShort(), anyInt());
	}

	@Test
	public void testRemoveGroup() {
		UserGroup group = new UserGroup();
		
		when(dbm.getQueryResultUnique(eq("SQL_USERGROUP_GET_GROUP"), eq(UserGroup.class), eq(idUserGroup)))
		.thenReturn(group, group, null);
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_USERS"), anyShort())).thenReturn(-1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_USERS"), eq(idUserGroup))).thenReturn(1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_AUTHOR_FOLLOWINGS"), anyShort())).thenReturn(-1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_AUTHOR_FOLLOWINGS"), eq(idUserGroup))).thenReturn(1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_TAG_FOLLOWINGS"), anyShort())).thenReturn(-1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_ALL_TAG_FOLLOWINGS"), eq(idUserGroup))).thenReturn(1);

		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_GROUP"), eq(idUserGroup))).thenReturn(1);
		
		super.testRemoveGroup();
		
		verify(dbm).updateQuery_NoCommit(anyObject(), eq("SQL_USERGROUP_DELETE_GROUP"), anyShort());
	}

	@Test
	public void testGetGroupsFromUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS_FROM_USER"), eq(Short.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS_FROM_USER"), eq(Short.class), eq(idUser))).thenReturn(ids);

		super.testGetGroupsFromUser();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USERGROUP_GET_GROUPIDS_FROM_USER"), eq(Short.class),anyInt());
	}

	@Test
	public void testGetAuthorFollowings() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(invalidIdUserGroup))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUserGroup))).thenReturn(ids);

		super.testGetAuthorFollowings();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_AUTHORIDS"), eq(Short.class), anyShort());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_ADD_FOLLOW_AUTHOR"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testFollowAuthor();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_ADD_FOLLOW_AUTHOR"), anyShort(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_REMOVE_FOLLOW_AUTHOR"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testUnfollowAuthor();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_REMOVE_FOLLOW_AUTHOR"), anyShort(), anyInt());
	}

	@Test
	public void testGetTagFollowings() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_TAGIDS"), eq(Integer.class), eq(invalidIdUserGroup))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_TAGIDS"), eq(Integer.class), eq(idUserGroup))).thenReturn(ids);

		super.testGetTagFollowings();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_TAGIDS"), eq(Integer.class), anyShort());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_TAGIDS"), eq(Integer.class), eq(idUserGroup)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_ADD_FOLLOW_TAG"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testFollowTag();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_ADD_FOLLOW_TAG"), anyShort(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowTag() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(dbm.getQueryResultList(eq("SQL_USERGROUP_GET_FOLLOW_TAGIDS"), eq(Integer.class), eq(idUserGroup)))
			.thenReturn(ids, ids, ids, null);
		
		when(dbm.updateQuery(eq("SQL_USERGROUP_REMOVE_FOLLOW_TAG"), anyShort(), anyInt())).thenReturn(-1, -1, 1);

		super.testUnfollowTag();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USERGROUP_REMOVE_FOLLOW_TAG"), anyShort(), anyInt());
	}
}
