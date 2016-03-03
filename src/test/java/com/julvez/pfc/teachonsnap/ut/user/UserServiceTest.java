package com.julvez.pfc.teachonsnap.ut.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class UserServiceTest extends ServiceTest<UserService> {

	protected int idUser = 1;
	protected int invalidIdUser = -1;
	protected String email = "name@teachonsnap.com";
	protected String unverifiedEmail = "mail@unverified.com";
	protected String password = "secret";
	protected String firstname = "firstName";
	protected String lastname = "lastName";
	protected String token = "token";
	protected String query = "search";
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;	
	
	protected int idTag = 1;
	protected int invalidIdTag = -1;
	
	@Test
	public void testGetUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
				
		assertNull(test.getUser(invalidIdUser));
	}

	@Test
	public void testGetUserFromEmail() {
		assertNotNull(test.getUserFromEmail(email));
		
		assertNull(test.getUserFromEmail(NULL_STRING));
		assertNull(test.getUserFromEmail(EMPTY_STRING));
		assertNull(test.getUserFromEmail(BLANK_STRING));
	}

	@Test
	public void testValidatePassword() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		
		assertTrue(test.validatePassword(user, password));
		
		assertFalse(test.validatePassword(null, password));
		assertFalse(test.validatePassword(user, NULL_STRING));
		assertFalse(test.validatePassword(user, EMPTY_STRING));
		assertFalse(test.validatePassword(user, BLANK_STRING));
	}

	@Test
	public void testSaveUserLanguage() {
		Language lang = mock(Language.class);
		when(lang.getId()).thenReturn((short)2);
		
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)1, user.getIdLanguage());
		
		assertNull(test.saveUserLanguage(null, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)1, user.getIdLanguage());
		
		assertNotNull(test.saveUserLanguage(user, lang));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals((short)2, user.getIdLanguage());
	}

	@Test
	public void testSaveFirstName() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getFirstName());
		
		assertNull(test.saveFirstName(null, NULL_STRING));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getFirstName());
		
		assertNotNull(test.saveFirstName(user, firstname));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(firstname, user.getFirstName());
	}

	@Test
	public void testSaveLastName() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getLastName());
		
		assertNull(test.saveLastName(null, NULL_STRING));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getLastName());
		
		assertNotNull(test.saveLastName(user, lastname));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(lastname, user.getLastName());	
	}

	@Test
	public void testSavePassword() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		
		assertFalse(test.validatePassword(user, password));
		
		test.savePassword(null, password);
		
		assertFalse(test.validatePassword(user, password));
		
		test.savePassword(user, password);
		
		assertTrue(test.validatePassword(user, password));
	}

	@Test
	public void testSendPasswordRemind() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		
		assertFalse(test.sendPasswordRemind(null));
		assertTrue(test.sendPasswordRemind(user));		
	}

	@Test
	public void testSavePasswordTemporaryToken() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		
		String newToken = test.savePasswordTemporaryToken(null);
		
		assertNotEquals(user, test.getUserFromPasswordTemporaryToken(newToken));
		
		newToken = test.savePasswordTemporaryToken(user);
		
		assertSame(user, test.getUserFromPasswordTemporaryToken(newToken));
	}

	@Test
	public void testGetUserFromPasswordTemporaryToken() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertSame(user, test.getUserFromPasswordTemporaryToken(token));
		
		assertNull(test.getUserFromPasswordTemporaryToken(NULL_STRING));
		assertNull(test.getUserFromPasswordTemporaryToken(EMPTY_STRING));
		assertNull(test.getUserFromPasswordTemporaryToken(BLANK_STRING));
	}

	@Test
	public void testDeletePasswordTemporaryToken() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertSame(user, test.getUserFromPasswordTemporaryToken(token));
		
		test.deletePasswordTemporaryToken(null);
		
		assertSame(user, test.getUserFromPasswordTemporaryToken(token));
		
		test.deletePasswordTemporaryToken(user);
		
		assertNull(test.getUserFromPasswordTemporaryToken(token));
	}

	@Test
	public void testIsAllowedForLesson() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAdmin()).thenReturn(true, true, false, false, false);
		when(user.isAuthor()).thenReturn(true, true, false);
		
		Lesson lesson = mock(Lesson.class);
		when(lesson.getIdUser()).thenReturn(idUser);
		
		Lesson otherLesson = mock(Lesson.class);
		when(otherLesson.getIdUser()).thenReturn(invalidIdUser);
				
		assertTrue(test.isAllowedForLesson(user, lesson)); //admin
		assertTrue(test.isAllowedForLesson(user, otherLesson)); //admin
		assertTrue(test.isAllowedForLesson(user, lesson)); //author
		assertFalse(test.isAllowedForLesson(user, otherLesson)); //author
		assertFalse(test.isAllowedForLesson(user, lesson)); 
		
		assertFalse(test.isAllowedForLesson(null, null));
	}

	@Test
	public void testSendRegister() {
		Language language = new Language();
		language.setId((short)1);
		
		User user = test.getUserFromEmail(email);
		assertNotNull(user);
		
		assertFalse(test.sendRegister(NULL_STRING, NULL_STRING, NULL_STRING, null));
		assertFalse(test.sendRegister(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, language));
		assertFalse(test.sendRegister(BLANK_STRING, EMPTY_STRING, EMPTY_STRING, language));
		assertFalse(test.sendRegister(email, EMPTY_STRING, EMPTY_STRING, language));
		
		assertFalse(test.sendRegister(unverifiedEmail, EMPTY_STRING, EMPTY_STRING, language));
		
		user = test.getUserFromEmail(email);
		assertNull(user);
		
		assertTrue(test.sendRegister(email, EMPTY_STRING, EMPTY_STRING, language));		
	}

	@Test
	public void testCreateUser() {
		Language language = new Language();
		language.setId((short)1);
		
		assertNull(test.createUser(NULL_STRING, NULL_STRING, NULL_STRING, null));
		assertNull(test.createUser(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, language));
		assertNull(test.createUser(BLANK_STRING, EMPTY_STRING, EMPTY_STRING, language));
		assertNotNull(test.createUser(email, EMPTY_STRING, EMPTY_STRING, language));
	}

	@Test
	public void testVerifyEmailDomain() {
		assertTrue(test.verifyEmailDomain(email));
		assertFalse(test.verifyEmailDomain(unverifiedEmail));
	}

	@Test
	public void testGetUsers() {
		List<User> users = test.getUsers(FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.getUsers(SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}
		
		users = test.getUsers(INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());		
	}

	@Test
	public void testSearchUsersByEmail() {
		List<User> users = test.searchUsersByEmail(query, FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchUsersByEmail(query, SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchUsersByEmail(NULL_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchUsersByEmail(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchUsersByEmail(BLANK_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		
		users = test.searchUsersByEmail(query, INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testSearchUsersByName() {
		List<User> users = test.searchUsersByName(query, FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchUsersByName(query, SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchUsersByName(NULL_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchUsersByName(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchUsersByName(BLANK_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		
		users = test.searchUsersByName(query, INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testSaveAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());
		
		assertNull(test.saveAuthor(null));
		
		assertNotNull(test.saveAuthor(user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAuthor());		
	}

	@Test
	public void testSaveAdmin() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAdmin());
		
		assertNull(test.saveAdmin(null));
		
		assertNotNull(test.saveAdmin(user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAdmin());
	}

	@Test
	public void testRemoveAdmin() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAdmin());
		
		assertNull(test.removeAdmin(null));
		
		assertNotNull(test.removeAdmin(user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAdmin());
	}

	@Test
	public void testRemoveAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isAuthor());
		
		assertNull(test.removeAuthor(null));
		
		assertNotNull(test.removeAuthor(user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isAuthor());	
	}

	@Test
	public void testBlockUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		
		assertNull(test.blockUser(null, EMPTY_STRING, user));
		
		assertNull(test.blockUser(user, EMPTY_STRING, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
		
		assertNotNull(test.blockUser(user, "reason", user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
	}

	@Test
	public void testUnblockUser() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
		
		assertNull(test.unblockUser(null, user));
		
		assertNull(test.unblockUser(user, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertTrue(user.isBanned());
		
		assertNotNull(test.unblockUser(user, user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertFalse(user.isBanned());
	}

	@Test
	public void testGetAuthors() {
		List<User> users = test.getAuthors(FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.getAuthors(SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}
		
		users = test.getAuthors(INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testSearchAuthorsByEmail() {
		List<User> users = test.searchAuthorsByEmail(query, FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchAuthorsByEmail(query, SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchAuthorsByEmail(NULL_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchAuthorsByEmail(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchAuthorsByEmail(BLANK_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		
		users = test.searchAuthorsByEmail(query, INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testSearchAuthorsByName() {
		List<User> users = test.searchAuthorsByName(query, FIRST_RESULT);		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchAuthorsByName(query, SECOND_RESULT);		
		assertNotNull(users);
		
		for(User user:users){
			assertEquals(i++, user.getId());
		}		
		
		users = test.searchAuthorsByName(NULL_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchAuthorsByName(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		users = test.searchAuthorsByName(BLANK_STRING, FIRST_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
		
		users = test.searchAuthorsByName(query, INVALID_RESULT);
		assertNotNull(users);
		assertEquals(0, users.size());	
	}

	@Test
	public void testGetAuthorsFollowed() {
		User user1 = new User();
		user1.setId(idUser);
		
		User user2 = new User();
		user2.setId(2*idUser);
		
		List<User> authors = new ArrayList<User>();
		authors.add(user1);
		authors.add(user2);
		
		List<User> followings = new ArrayList<User>();
		followings.add(user1);
		
		List<AuthorFollowed> followed = test.getAuthorsFollowed(null, null);
		assertNotNull(followed);
		assertEquals(0, followed.size());

		followed = test.getAuthorsFollowed(null, followings);
		assertNotNull(followed);
		assertEquals(0, followed.size());
		
		followed = test.getAuthorsFollowed(authors, null);
		assertNotNull(followed);
		assertEquals(2, followed.size());
		
		assertEquals(user1.getId(), followed.get(0).getId());
		assertEquals(user2.getId(), followed.get(1).getId());
		assertFalse(followed.get(0).isFollowed());
		assertFalse(followed.get(1).isFollowed());
		
		followed = test.getAuthorsFollowed(authors, followings);
		assertNotNull(followed);
		assertEquals(2, followed.size());
		
		assertEquals(user1.getId(), followed.get(0).getId());
		assertEquals(user2.getId(), followed.get(1).getId());
		assertTrue(followed.get(0).isFollowed());
		assertFalse(followed.get(1).isFollowed());		
	}

	@Test
	public void testFollowAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		Map<String, String> ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNull(test.followAuthor(null, user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNull(test.followAuthor(user, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNotNull(test.followAuthor(user, user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
	}

	@Test
	public void testUnfollowAuthor() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		Map<String, String> ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertNull(test.unfollowAuthor(null, user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertNull(test.unfollowAuthor(user, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idUser, ids.get("["+idUser+"]"));
		
		assertNotNull(test.unfollowAuthor(user, user));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getAuthorFollowed();
		assertNotNull(ids);		
		assertEquals(0, ids.size());
	}

	@Test
	public void testFollowLesson() {
		Lesson lesson = new Lesson();
		lesson.setId(idLesson);
		
		User user = test.getUser(idUser);
		assertNotNull(user);
		Map<String, String> ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNull(test.followLesson(null, lesson));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNull(test.followLesson(user, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(0, ids.size());
		
		assertNotNull(test.followLesson(user, lesson));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
	}

	@Test
	public void testUnfollowLesson() {
		Lesson lesson = new Lesson();
		lesson.setId(idLesson);
		
		User user = test.getUser(idUser);
		assertNotNull(user);
		Map<String, String> ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertNull(test.unfollowLesson(null, lesson));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertNull(test.unfollowLesson(user, null));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertNotNull(ids);
		assertEquals(1, ids.size());
		assertEquals(""+idLesson, ids.get("["+idLesson+"]"));
		
		assertNotNull(test.unfollowLesson(user, lesson));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		ids = user.getLessonFollowed();
		assertNotNull(ids);		
		assertEquals(0, ids.size());
	}

	@Test
	public void testGetUsersFromIDs() {
		Map<String, String> followedAuthors = new HashMap<String, String>();
		followedAuthors.put(""+idUser, ""+idUser);
		
		List<User> users = test.getUsersFromIDs(null);		
		assertNull(users);

		users = test.getUsersFromIDs(followedAuthors);		
		assertNotNull(users);
		
		short i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}
	}

	@Test
	public void testGetAuthorFollowers() {
		User author = new User();
		author.setId(idUser);
		
		List<User> users = test.getAuthorFollowers(null);		
		assertNotNull(users);
		assertEquals(0, users.size());

		users = test.getAuthorFollowers(author);		
		assertNotNull(users);
		
		short i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}
	}

	@Test
	public void testGetLessonFollowers() {
		Lesson lesson = new Lesson();
		lesson.setId(idLesson);
		
		List<User> users = test.getLessonFollowers(null);		
		assertNotNull(users);
		assertEquals(0, users.size());

		users = test.getLessonFollowers(lesson);		
		assertNotNull(users);
		
		short i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}
	}

	@Test
	public void testGetTagFollowers() {
		Tag tag = new Tag();
		tag.setId(idTag);
		
		List<User> users = test.getTagFollowers(null);		
		assertNotNull(users);
		assertEquals(0, users.size());

		users = test.getTagFollowers(tag);		
		assertNotNull(users);
		
		short i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}
	}

	@Test
	public void testSaveExtraInfo() {
		User user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getExtraInfo());
		
		assertNull(test.saveExtraInfo(null, NULL_STRING));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(EMPTY_STRING, user.getExtraInfo());
		
		assertNotNull(test.saveExtraInfo(user, lastname));
		
		user = test.getUser(idUser);
		assertNotNull(user);
		assertEquals(lastname, user.getExtraInfo());
	}

	@Test
	public void testGetAdmins() {
		List<User> users = test.getAdmins();
		
		assertNotNull(users);
		
		int i=1;
		for(User user:users){
			assertEquals(i++, user.getId());
		}
	}
}
