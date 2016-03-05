package com.julvez.pfc.teachonsnap.ut.usergroup;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class UserGroupServiceTest extends ServiceTest<UserGroupService> {

	protected String groupName = "name";
	
	protected int idTag = 1;
	
	protected int idUser = 1;
	
	protected String email = "name@teachonsnap.com";

	protected String query = "search";

	protected short idUserGroup = 1;
	protected short invalidIdUserGroup = -1;
	
	@Test
	public void testGetGroups() {
		List<UserGroup> groups = test.getGroups(FIRST_RESULT);		
		assertNotNull(groups);
		
		short i=1;
		for(UserGroup group:groups){
			assertEquals(i++, group.getId());
		}		
		
		groups = test.getGroups(SECOND_RESULT);		
		assertNotNull(groups);
		
		for(UserGroup group:groups){
			assertEquals(i++, group.getId());
		}	
		
		groups = test.getGroups(INVALID_RESULT);
		assertNotNull(groups);
		assertEquals(0, groups.size());
	}

	@Test
	public void testSearchGroupsByName() {
		List<UserGroup> groups = test.searchGroupsByName(query, FIRST_RESULT);		
		assertNotNull(groups);
		
		short i=1;
		for(UserGroup group:groups){
			assertEquals(i++, group.getId());
		}		
		
		groups = test.searchGroupsByName(query, SECOND_RESULT);		
		assertNotNull(groups);
		
		for(UserGroup group:groups){
			assertEquals(i++, group.getId());
		}	
		
		groups = test.searchGroupsByName(NULL_STRING, FIRST_RESULT);
		assertNotNull(groups);
		assertEquals(0, groups.size());
		groups = test.searchGroupsByName(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(groups);
		assertEquals(0, groups.size());
		groups = test.searchGroupsByName(BLANK_STRING, FIRST_RESULT);
		assertNotNull(groups);
		assertEquals(0, groups.size());
		
		groups = test.searchGroupsByName(query, INVALID_RESULT);
		assertNotNull(groups);
		assertEquals(0, groups.size());
	}

	@Test
	public void testGetGroup() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
				
		assertNull(test.getGroup(invalidIdUserGroup));
	}

	@Test
	public void testCreateGroup() {
		assertNotNull(test.createGroup(groupName));
		
		assertNull(test.createGroup(NULL_STRING));
	}

	@Test
	public void testAddUserByMailList() {
		List<String> emails = new ArrayList<String>();
		emails.add(email);
		
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<User> users = group.getUsers();		
		assertNull(users);
		
		assertNull(test.addUserByMailList(null, null));
		
		group = test.addUserByMailList(group, emails);
		assertNotNull(group);
		
		users = group.getUsers();		
		assertNotNull(users);
		assertEquals(idUser, users.get(0).getId());
	}

	@Test
	public void testAddUser() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<User> users = group.getUsers();		
		assertNull(users);
		
		assertNull(test.addUser(null, null));
		assertNull(test.addUser(group, null));
		
		group = test.addUser(group, user);
		assertNotNull(group);
		
		users = group.getUsers();		
		assertNotNull(users);
		assertEquals(idUser, users.get(0).getId());
	}

	@Test
	public void testSaveGroupName() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		assertEquals(EMPTY_STRING, group.getGroupName());
		
		assertNull(test.saveGroupName(null, NULL_STRING));
		
		group = test.saveGroupName(group, groupName);		
		assertNotNull(group);
		assertEquals(groupName, group.getGroupName());
	}

	@Test
	public void testRemoveUser() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<User> users = group.getUsers();		
		assertNotNull(users);
		
		User user = users.get(0);
		assertNotNull(user);		
				
		assertNull(test.removeUser(null, user));
		assertNull(test.removeUser(group, null));
		
		group = test.removeUser(group, user);
		assertNotNull(group);
		assertEquals(0, group.getUsers().size());
	}

	@Test
	public void testRemoveGroup() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		assertFalse(test.removeGroup(null));
		
		group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		assertTrue(test.removeGroup(group));
		
		group = test.getGroup(idUserGroup);
		assertNull(group);
	}
	
	@Test
	public void testGetGroupsFromUser() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		
		List<UserGroup> groups = test.getGroupsFromUser(null);		
		assertNull(groups);

		groups = test.getGroupsFromUser(user);		
		assertNotNull(groups);
		
		short i=1;
		for(UserGroup group:groups){
			assertEquals(i++, group.getId());
		}	
	}

	@Test
	public void testGetAuthorFollowings() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<User> users = test.getAuthorFollowings(null);		
		assertNotNull(users);
		assertEquals(0, users.size());

		users = test.getAuthorFollowings(group);		
		assertNotNull(users);
		
		int i=1;
		for(User u:users){
			assertEquals(i++, u.getId());
		}
	}

	@Test
	public void testFollowAuthor() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAuthor()).thenReturn(true);
		
		List<User> users = test.getAuthorFollowings(group);		
		assertNotNull(users);
		assertEquals(0, users.size());
		
		assertNull(test.followAuthor(null, user));
		assertNull(test.followAuthor(group, null));
		
		group = test.followAuthor(group, user);
		
		users = test.getAuthorFollowings(group);		
		assertNotNull(users);
		assertEquals(idUser, users.get(0).getId());
	}

	@Test
	public void testUnfollowAuthor() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<User> users = test.getAuthorFollowings(group);		
		assertNotNull(users);
		
		User user = users.get(0);
		assertNotNull(user);	
		
		assertNull(test.unfollowAuthor(null, user));
		assertNull(test.unfollowAuthor(group, null));
		
		group = test.unfollowAuthor(group, user);
		
		users = test.getAuthorFollowings(group);		
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testGetTagFollowings() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<Tag> tags = test.getTagFollowings(null);		
		assertNotNull(tags);
		assertEquals(0, tags.size());

		tags = test.getTagFollowings(group);		
		assertNotNull(tags);
		
		int i=1;
		for(Tag u:tags){
			assertEquals(i++, u.getId());
		}
	}

	@Test
	public void testFollowTag() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		Tag tag = mock(Tag.class);
		when(tag.getId()).thenReturn(idTag);
		
		List<Tag> tags = test.getTagFollowings(group);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
		
		assertNull(test.followTag(null, tag));
		assertNull(test.followTag(group, null));
		
		group = test.followTag(group, tag);
		
		tags = test.getTagFollowings(group);		
		assertNotNull(tags);
		assertEquals(idTag, tags.get(0).getId());
	}

	@Test
	public void testUnfollowTag() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		List<Tag> tags = test.getTagFollowings(group);		
		assertNotNull(tags);
		
		Tag tag = tags.get(0);
		assertNotNull(tag);	
		
		assertNull(test.unfollowTag(null, tag));
		assertNull(test.unfollowTag(group, null));
		
		group = test.unfollowTag(group, tag);
		
		tags = test.getTagFollowings(group);		
		assertNotNull(tags);
		assertEquals(0, tags.size());
	}
}