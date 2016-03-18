package com.julvez.pfc.teachonsnap.ut.usergroup.impl;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyShort;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.impl.UserGroupServiceImpl;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.ut.usergroup.UserGroupServiceTest;

public class UserGroupServiceImplTest extends UserGroupServiceTest {

	@Mock
	private UserGroupRepository groupRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private TagService tagService;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected UserGroupService getService() {
		return new UserGroupServiceImpl(groupRepository, userService, tagService, stringManager);
	}

	@Test
	public void testSearchGroupsByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(groupRepository.searchGroupsByName(eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(groupRepository.searchGroupsByName(eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn((short)1, (short)2, (short)3, (short)4);
		
		when(groupRepository.getGroup(anyShort())).thenReturn(group);	
		
		super.testSearchGroupsByName();
		verify(groupRepository, times(5)).searchGroupsByName(anyString(), anyInt());		
	}

	@Test
	public void testGetGroups() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(groupRepository.getGroups(FIRST_RESULT)).thenReturn(ids);
		when(groupRepository.getGroups(SECOND_RESULT)).thenReturn(ids2);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn((short)1, (short)2, (short)3, (short)4);
		
		when(groupRepository.getGroup(anyShort())).thenReturn(group);	
		
		super.testGetGroups();
		verify(groupRepository, times(2)).getGroups(anyInt());		
	}

	@Test
	public void testGetGroup() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);		
		super.testGetGroup();
		verify(groupRepository).getGroup(anyShort());
	}

	@Test
	public void testCreateGroup() {
		when(stringManager.isEmpty(groupName)).thenReturn(false);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.createGroup(groupName)).thenReturn(idUserGroup);
		super.testCreateGroup();
		verify(groupRepository, times(2)).createGroup(anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddUserByMailList() {
		User user = new User();		
		when(userService.getUserFromEmail(email)).thenReturn(user);

		when(groupRepository.addUser(idUserGroup, idUser)).thenReturn(true);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		
		List<User> users = new ArrayList<User>();
		user.setId(idUser);
		users.add(user);		
		
		List<User> emptyUsers = new ArrayList<User>();
		
		when(group.getUsers()).thenReturn(emptyUsers, emptyUsers, users);
		
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		super.testAddUserByMailList();
		verify(groupRepository).addUser(anyShort(), anyInt());	
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testAddUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);

		when(groupRepository.addUser(idUserGroup, idUser)).thenReturn(true);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setId(idUser);
		users.add(user);		
		List<User> emptyUsers = new ArrayList<User>();
		when(group.getUsers()).thenReturn(emptyUsers, users);
		
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		super.testAddUser();
		verify(groupRepository).addUser(anyShort(), anyInt());
	}

	@Test
	public void testSaveGroupName() {
		when(stringManager.isEmpty(groupName)).thenReturn(false);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(group.getGroupName()).thenReturn(EMPTY_STRING, groupName);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);		
		
		when(groupRepository.saveGroupName(idUserGroup, groupName)).thenReturn(true);
		super.testSaveGroupName();
		verify(groupRepository).saveGroupName(anyShort(), anyString());
	}

	@Test
	public void testRemoveUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setId(idUser);
		users.add(user);		
		when(group.getUsers()).thenReturn(users);
		
		when(groupRepository.removeUser(idUserGroup, idUser)).thenReturn(true);
		
		super.testRemoveUser();
		
		verify(groupRepository).removeUser(anyShort(), anyInt());
	}

	@Test
	public void testRemoveGroup() {
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group, group, (UserGroup)null);

		when(groupRepository.removeGroup(idUserGroup)).thenReturn(true);
		super.testRemoveGroup();
		verify(groupRepository).removeGroup(anyShort());
	}

	@Test
	public void testGetGroupsFromUser() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(groupRepository.getGroupsFromUser(idUser)).thenReturn(ids);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup, (short)2);
		when(groupRepository.getGroup(anyShort())).thenReturn(group);
		
		super.testGetGroupsFromUser();
		
		verify(groupRepository).getGroupsFromUser(anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowAuthor() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(userService.getUser(anyInt())).thenReturn(user);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) idUser);		
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getAuthorFollowings(idUserGroup)).thenReturn(new ArrayList<Short>(), new ArrayList<Short>(), ids);
		
		when(groupRepository.followAuthor(idUserGroup, idUser)).thenReturn(true);
		super.testFollowAuthor();
		verify(groupRepository).followAuthor(anyShort(), anyInt());
	}

	@Test
	public void testGetAuthorFollowings() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,2);
		when(userService.getUser(anyInt())).thenReturn(user);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getAuthorFollowings(idUserGroup)).thenReturn(ids);
		
		super.testGetAuthorFollowings();
		
		verify(groupRepository).getAuthorFollowings(anyShort());		
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowAuthor() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(userService.getUser(anyInt())).thenReturn(user);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) idUser);		
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getAuthorFollowings(idUserGroup)).thenReturn(ids, new ArrayList<Short>());
		
		when(groupRepository.unfollowAuthor(idUserGroup, idUser)).thenReturn(true);
		super.testUnfollowAuthor();
		verify(groupRepository).unfollowAuthor(anyShort(), anyInt());
	}

	@Test
	public void testGetTagFollowings() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(1,2);
		when(tagService.getTag(anyInt())).thenReturn(tag);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getTagFollowings(idUserGroup)).thenReturn(ids);
		
		super.testGetTagFollowings();
		
		verify(groupRepository).getTagFollowings(anyShort());	
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowTag() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);
		when(tagService.getTag(anyInt())).thenReturn(tag);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idTag);		
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getTagFollowings(idUserGroup)).thenReturn(new ArrayList<Integer>(), new ArrayList<Integer>(), ids);
		
		when(groupRepository.followTag(idUserGroup, idTag)).thenReturn(true);
		super.testFollowTag();
		verify(groupRepository).followTag(anyShort(), anyInt());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUnfollowTag() {
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);
		when(tagService.getTag(anyInt())).thenReturn(tag);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(idTag);		
		
		UserGroup group = mock(UserGroup.class);
		when(group.getId()).thenReturn(idUserGroup);
		when(groupRepository.getGroup(idUserGroup)).thenReturn(group);
		
		when(groupRepository.getTagFollowings(idUserGroup)).thenReturn(ids, new ArrayList<Integer>());
		
		when(groupRepository.unfollowTag(idUserGroup, idTag)).thenReturn(true);
		super.testUnfollowTag();
		verify(groupRepository).unfollowTag(anyShort(), anyInt());
	}
}
