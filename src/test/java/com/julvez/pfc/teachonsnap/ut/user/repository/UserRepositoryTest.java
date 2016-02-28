package com.julvez.pfc.teachonsnap.ut.user.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class UserRepositoryTest extends RepositoryTest<UserRepository> {

	protected int idUser = 1;
	protected int invalidIdUser = -1;
	protected String email = "name@teachonsnap.com";
	protected String password = "secret";
	protected String firstname = "firstName";
	protected String lastname = "lastName";
	protected String token = "token";
	protected String query = "search";
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;

	@Test
	public void testGetUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
				
		assertNull(test.getUser(invalidIdUser));
	}

	@Test
	public void testGetIdUserFromEmail() {
		assertEquals(idUser, test.getIdUserFromEmail(email));
		
		assertEquals(invalidIdUser, test.getIdUserFromEmail(NULL_STRING));
		assertEquals(invalidIdUser, test.getIdUserFromEmail(EMPTY_STRING));
		assertEquals(invalidIdUser, test.getIdUserFromEmail(BLANK_STRING));
	}

	@Test
	public void testIsValidPassword() {
		assertTrue(test.isValidPassword(idUser, password)>0);
		
		assertFalse(test.isValidPassword(invalidIdUser, NULL_STRING)>0);
		assertFalse(test.isValidPassword(idUser, NULL_STRING)>0);
		assertFalse(test.isValidPassword(idUser, EMPTY_STRING)>0);
		assertFalse(test.isValidPassword(idUser, BLANK_STRING)>0);
	}

	@Test
	public void testSaveUserLanguage() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)1, user.getIdLanguage());
		
		test.saveUserLanguage(invalidIdUser, (short)2);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)1, user.getIdLanguage());
		
		test.saveUserLanguage(idUser, (short)2);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)2, user.getIdLanguage());
	}

	@Test
	public void testSaveFirstName() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getFirstName());
		
		test.saveFirstName(invalidIdUser, NULL_STRING);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getFirstName());
		
		test.saveFirstName(idUser, firstname);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(firstname, user.getFirstName());
	}

	@Test
	public void testSaveLastName() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getLastName());
		
		test.saveLastName(invalidIdUser, NULL_STRING);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getLastName());
		
		test.saveLastName(idUser, lastname);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(lastname, user.getLastName());	
	}

	@Test
	public void testSavePassword() {
		assertFalse(test.isValidPassword(idUser, password)>0);
		
		test.savePassword(invalidIdUser, password);
		
		assertFalse(test.isValidPassword(idUser, password)>0);
		
		test.savePassword(idUser, password);
		
		assertTrue(test.isValidPassword(idUser, password)>0);
	}

	@Test
	public void testSavePasswordTemporaryToken() {
		assertNotEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
		
		test.savePasswordTemporaryToken(invalidIdUser, token);
		
		assertNotEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
		
		test.savePasswordTemporaryToken(idUser, token);
		
		assertEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
	}

	@Test
	public void testGetIdUserFromPasswordTemporaryToken() {
		assertEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
		
		assertEquals(invalidIdUser, test.getIdUserFromPasswordTemporaryToken(NULL_STRING));
		assertEquals(invalidIdUser, test.getIdUserFromPasswordTemporaryToken(EMPTY_STRING));
		assertEquals(invalidIdUser, test.getIdUserFromPasswordTemporaryToken(BLANK_STRING));
	}

	@Test
	public void testDeletePasswordTemporaryToken() {
		assertEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
		
		test.deletePasswordTemporaryToken(invalidIdUser);
		
		assertEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
		
		test.deletePasswordTemporaryToken(idUser);
		
		assertNotEquals(idUser, test.getIdUserFromPasswordTemporaryToken(token));
	}

	@Test
	public void testCreateUser() {
		assertEquals(idUser, test.createUser(email, firstname, lastname, (short)1));
		
		assertEquals(invalidIdUser, test.createUser(NULL_STRING, firstname, lastname, (short)1));
		assertEquals(invalidIdUser, test.createUser(email, NULL_STRING, lastname, (short)1));
	}

	@Test
	public void testGetUsers() {
		List<Short> ids = test.getUsers(FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getUsers(SECOND_RESULT);		
		assertNotNull(ids);
		
		for(short b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getUsers(INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testSearchUsersByEmail() {
		List<Short> ids = test.searchUsersByEmail(query, FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchUsersByEmail(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchUsersByEmail(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchUsersByEmail(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchUsersByEmail(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchUsersByEmail(query, INVALID_RESULT);
		assertNull(ids);	
	}

	@Test
	public void testSearchUsersByName() {
		List<Short> ids = test.searchUsersByName(query, FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchUsersByName(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchUsersByName(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchUsersByName(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchUsersByName(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchUsersByName(query, INVALID_RESULT);
		assertNull(ids);	
	}

	@Test
	public void testSaveAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());
		
		test.saveAuthor(invalidIdUser, NULL_STRING);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());
		
		test.saveAuthor(idUser, NULL_STRING);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());
		
		test.saveAuthor(idUser, firstname + " " + lastname);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAuthor());
		assertEquals(firstname + "-" + lastname, user.getURIName());
	}

	@Test
	public void testSaveAdmin() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAdmin());
		
		test.saveAdmin(invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAdmin());
		
		test.saveAdmin(idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAdmin());
	}

	@Test
	public void testRemoveAdmin() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAdmin());
		
		test.removeAdmin(invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAdmin());
		
		test.removeAdmin(idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAdmin());
	}

	@Test
	public void testRemoveAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAuthor());
		
		test.removeAuthor(invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAuthor());
		
		test.removeAuthor(idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());
	}

	@Test
	public void testGetUserBannedInfo() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		assertNull(test.getUserBannedInfo(idUser));
		
		test.blockUser(idUser, "reason", idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
		
		assertNull(test.getUserBannedInfo(invalidIdUser));
		UserBannedInfo info = test.getUserBannedInfo(idUser);
		assertNotNull(info);
		assertEquals("reason", info.getReason());
	}

	@Test
	public void testBlockUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		
		test.blockUser(invalidIdUser, EMPTY_STRING, idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		
		test.blockUser(idUser, EMPTY_STRING, invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		
		test.blockUser(idUser, "reason", idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
	}

	@Test
	public void testUnblockUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
		
		test.unblockUser(invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
		
		test.unblockUser(idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
	}

	@Test
	public void testGetAuthors() {
		List<Short> ids = test.getAuthors(FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getAuthors(SECOND_RESULT);		
		assertNotNull(ids);
		
		for(short b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getAuthors(INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testSearchAuthorsByEmail() {
		List<Short> ids = test.searchAuthorsByEmail(query, FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchAuthorsByEmail(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchAuthorsByEmail(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchAuthorsByEmail(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchAuthorsByEmail(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchAuthorsByEmail(query, INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testSearchAuthorsByName() {
		List<Short> ids = test.searchAuthorsByName(query, FIRST_RESULT);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.searchAuthorsByName(query, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.searchAuthorsByName(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchAuthorsByName(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.searchAuthorsByName(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.searchAuthorsByName(query, INVALID_RESULT);
		assertNull(ids);
	}

	@Test
	public void testGetAuthorFollowed() {
		Map<String, String> ids = test.getAuthorFollowed(invalidIdUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());

		ids = test.getAuthorFollowed(idUser);
		assertNotNull(ids);
		
		int i=1;
		for(String b:ids.keySet()){
			assertEquals("[" + String.valueOf(i) + "]", b);
			assertEquals(String.valueOf(i), ids.get(b));
			i++;
		}
	}

	@Test
	public void testGetLessonFollowed() {
		Map<String, String> ids = test.getLessonFollowed(invalidIdUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());

		ids = test.getLessonFollowed(idUser);
		assertNotNull(ids);
		
		int i=1;
		for(String b:ids.keySet()){
			assertEquals("[" + String.valueOf(i) + "]", b);
			assertEquals(String.valueOf(i), ids.get(b));
			i++;
		}
	}

	@Test
	public void testUnfollowAuthor() {
		Map<String, String> ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertFalse(test.unfollowAuthor(invalidIdUser, idUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertFalse(test.unfollowAuthor(idUser, invalidIdUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertTrue(test.unfollowAuthor(idUser, idUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
	}

	@Test
	public void testFollowAuthor() {
		Map<String, String> ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertFalse(test.followAuthor(invalidIdUser, idUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertFalse(test.followAuthor(idUser, invalidIdUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertTrue(test.followAuthor(idUser, idUser));
		
		ids = test.getAuthorFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));	
	}

	@Test
	public void testFollowLesson() {
		Map<String, String> ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertFalse(test.followLesson(invalidIdUser, idLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertFalse(test.followLesson(idUser, invalidIdLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertTrue(test.followLesson(idUser, idLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));		
	}

	@Test
	public void testUnfollowLesson() {
		Map<String, String> ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertFalse(test.unfollowLesson(invalidIdUser, idLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertFalse(test.unfollowLesson(idUser, invalidIdLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertTrue(test.unfollowLesson(idUser, idLesson));
		
		ids = test.getLessonFollowed(idUser);		
		assertNotNull(ids);
		assertEquals(0, ids.size());
	}

	@Test
	public void testGetAuthorFollowers() {
		List<Short> ids = test.getAuthorFollowers(invalidIdUser);		
		assertNull(ids);

		ids = test.getAuthorFollowers(idUser);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetLessonFollowers() {
		List<Short> ids = test.getLessonFollowers(invalidIdUser);		
		assertNull(ids);

		ids = test.getLessonFollowers(idUser);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetTagFollowers() {
		List<Short> ids = test.getTagFollowers(invalidIdUser);		
		assertNull(ids);

		ids = test.getTagFollowers(idUser);		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testSaveExtraInfo() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getExtraInfo());
		
		test.saveExtraInfo(invalidIdUser, NULL_STRING);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getExtraInfo());
		
		test.saveExtraInfo(idUser, lastname);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(lastname, user.getExtraInfo());	
	}

	@Test
	public void testRemoveExtraInfo() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertNotNull(user.getExtraInfo());
		
		test.removeExtraInfo(invalidIdUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertNotNull(user.getExtraInfo());
		
		test.removeExtraInfo(idUser);
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertNull(user.getExtraInfo());
	}

	@Test
	public void testGetAdmins() {
		List<Short> ids = test.getAdmins();
		
		assertNotNull(ids);
		
		short i=1;
		for(short b:ids){
			assertEquals(i++, b);
		}
	}
}
