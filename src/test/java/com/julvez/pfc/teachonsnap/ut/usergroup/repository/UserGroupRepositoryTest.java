package com.julvez.pfc.teachonsnap.ut.usergroup.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class UserGroupRepositoryTest extends RepositoryTest<UserGroupRepository> {

	protected short idUserGroup = 1;
	protected short invalidIdUserGroup = -1;
	protected String name = "name";

	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected String query = "search";
	
	private int idTag = 1;
	private int invalidIdTag = -1;
	
	
	@Test
	public void testGetGroups() {
		List<Short> ids = test.getGroups(FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getGroups(SECOND_RESULT);		
		assertNotNull(ids);
		
		for(short b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getGroups(INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testSearchGroupsByName() {
		List<Short> ids = test.searchGroupsByName(query, FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchGroupsByName(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchGroupsByName(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchGroupsByName(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchGroupsByName(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchGroupsByName(query, INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testGetGroup() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
				
		assertNull(test.getGroup(invalidIdUserGroup));
	}

	@Test
	public void testGetGroupMembers() {
		List<Short> ids = test.getGroupMembers(invalidIdUserGroup);		
		assertNull(ids);

		ids = test.getGroupMembers(idUserGroup);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testCreateGroup() {
		assertEquals(idUserGroup, test.createGroup(name));
		
		assertEquals(invalidIdUserGroup, test.createGroup(NULL_STRING));		
	}

	@Test
	public void testAddUser() {
		List<Short> ids = test.getGroupMembers(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.addUser(invalidIdUserGroup, idUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.addUser(idUserGroup, invalidIdUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNull(ids);
		
		assertTrue(test.addUser(idUserGroup, idUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
	}

	@Test
	public void testSaveGroupName() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		assertEquals(EMPTY_STRING, group.getGroupName());
		
		assertFalse(test.saveGroupName(invalidIdUserGroup, NULL_STRING));
		
		group = test.getGroup(idUserGroup);
		assertNotNull(group);
		assertEquals(EMPTY_STRING, group.getGroupName());
		
		assertTrue(test.saveGroupName(idUserGroup, name));
		
		group = test.getGroup(idUserGroup);
		assertNotNull(group);
		assertEquals(name, group.getGroupName());
	}

	@Test
	public void testRemoveUser() {
		List<Short> ids = test.getGroupMembers(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
		
		assertFalse(test.removeUser(invalidIdUserGroup, idUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));

		assertFalse(test.removeUser(idUserGroup, invalidIdUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
		
		assertTrue(test.removeUser(idUserGroup, idUser));
		
		ids = test.getGroupMembers(idUserGroup);		
		assertNull(ids);
	}

	@Test
	public void testRemoveGroup() {
		UserGroup group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		assertFalse(test.removeGroup(invalidIdUserGroup));
		
		group = test.getGroup(idUserGroup);
		assertNotNull(group);
		
		assertTrue(test.removeGroup(idUserGroup));
		
		group = test.getGroup(idUserGroup);
		assertNull(group);
	}

	@Test
	public void testGetGroupsFromUser() {
		List<Short> ids = test.getGroupsFromUser(invalidIdUser);		
		assertNull(ids);

		ids = test.getGroupsFromUser(idUser);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetAuthorFollowings() {
		List<Short> ids = test.getAuthorFollowings(invalidIdUserGroup);		
		assertNull(ids);

		ids = test.getAuthorFollowings(idUserGroup);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testFollowAuthor() {
		List<Short> ids = test.getAuthorFollowings(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.followAuthor(invalidIdUserGroup, idUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.followAuthor(idUserGroup, invalidIdUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNull(ids);
		
		assertTrue(test.followAuthor(idUserGroup, idUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
	}

	@Test
	public void testUnfollowAuthor() {
		List<Short> ids = test.getAuthorFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
		
		assertFalse(test.unfollowAuthor(invalidIdUserGroup, idUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
		
		assertFalse(test.unfollowAuthor(idUserGroup, invalidIdUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals((short)idUser, (short)ids.get(0));
		
		assertTrue(test.unfollowAuthor(idUserGroup, idUser));
		
		ids = test.getAuthorFollowings(idUserGroup);		
		assertNull(ids);
	}

	@Test
	public void testGetTagFollowings() {
		List<Integer> ids = test.getTagFollowings(invalidIdUserGroup);		
		assertNull(ids);

		ids = test.getTagFollowings(idUserGroup);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testFollowTag() {
		List<Integer> ids = test.getTagFollowings(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.followTag(invalidIdUserGroup, idTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNull(ids);
		
		assertFalse(test.followTag(idUserGroup, invalidIdTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNull(ids);
		
		assertTrue(test.followTag(idUserGroup, idTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
	}

	@Test
	public void testUnfollowTag() {
		List<Integer> ids = test.getTagFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		assertFalse(test.unfollowTag(invalidIdUserGroup, idTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		assertFalse(test.unfollowTag(idUserGroup, invalidIdTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNotNull(ids);
		assertEquals(idTag, (int)ids.get(0));
		
		assertTrue(test.unfollowTag(idUserGroup, idTag));
		
		ids = test.getTagFollowings(idUserGroup);		
		assertNull(ids);
	}
}
