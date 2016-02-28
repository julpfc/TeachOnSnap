package com.julvez.pfc.teachonsnap.ut.user.repository;

import static org.mockito.Matchers.any;
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

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryDB;

public class UserRepositoryDBTest extends UserRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private StringManager stringManager;
	
	@Mock
	private PropertyManager properties;
	
	
	@Override
	protected UserRepository getRepository() {
		return new UserRepositoryDB(dbm, stringManager, properties);
	}
	
	@Test
	public void testGetUser() {
		User user = new User();	
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);
		
		super.testGetUser();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetIdUserFromEmail() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_EMAIL"), any(Class.class), eq(email))).thenReturn(idUser);
		super.testGetIdUserFromEmail();
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_EMAIL"), any(Class.class), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testIsValidPassword() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_ISVALIDPASSWORD"), any(Class.class), eq(password), eq(idUser))).thenReturn(1);
		super.testIsValidPassword();
		verify(dbm, times(5)).getQueryResultUnique(eq("SQL_USER_GET_ISVALIDPASSWORD"), any(Class.class), anyString(), anyInt());
	}

	@Test
	public void testSaveUserLanguage() {
		User user= new User();
		user.setIdLanguage((short)1);
		
		User userES= new User();
		userES.setIdLanguage((short)2);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser)))
				.thenReturn(user, user, userES);		
		
		super.testSaveUserLanguage();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_LANGUAGE"), anyInt(), anyInt());
	}

	@Test
	public void testSaveFirstName() {
		User user = new User();
		user.setFirstName(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setFirstName(firstname);		
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser)))
				.thenReturn(user, user, userMod);		
		
		super.testSaveFirstName();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_FIRSTNAME"), anyString(), anyInt());
	}

	@Test
	public void testSaveLastName() {
		User user = new User();
		user.setLastName(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setLastName(lastname);		
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser)))
				.thenReturn(user, user, userMod);		
		
		super.testSaveLastName();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_LASTNAME"), anyString(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSavePassword() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_ISVALIDPASSWORD"), any(Class.class), eq(password), eq(idUser))).thenReturn(0,0,1);	
		
		super.testSavePassword();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_PASSWORD"), anyString(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSavePasswordTemporaryToken() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_TMP_TOKEN"), any(Class.class), eq(token)))
				.thenReturn(invalidIdUser, invalidIdUser, idUser);	
		
		super.testSavePasswordTemporaryToken();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_TMP_TOKEN"),  anyInt(), anyString(), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testGetIdUserFromPasswordTemporaryToken() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_TMP_TOKEN"), any(Class.class), eq(token))).thenReturn(idUser);
		super.testGetIdUserFromPasswordTemporaryToken();
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_TMP_TOKEN"), any(Class.class), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDeletePasswordTemporaryToken() {
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_IDUSER_FROM_TMP_TOKEN"), any(Class.class), eq(token)))
		.thenReturn(idUser, idUser, invalidIdUser);	

		super.testDeletePasswordTemporaryToken();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_DELETE_TMP_TOKEN"),  anyInt());
	}

	@Test
	public void testCreateUser() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_USER_CREATE_USER"), anyString(), anyString(), anyString(), anyShort())).thenReturn((long)invalidIdUser);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_USER_CREATE_USER"), eq(email), eq(firstname), eq(lastname), eq((short)1))).thenReturn((long)idUser);
		
		super.testCreateUser();
		
		verify(dbm, times(3)).insertQueryAndGetLastInserID(eq("SQL_USER_CREATE_USER"), anyString(), anyString(), anyString(), anyShort());
	}

	@Test
	public void testGetUsers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_USERIDS"), eq(Short.class), eq(INVALID_RESULT), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_USERIDS"), eq(Short.class), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_USERIDS"), eq(Short.class), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetUsers();
		
		verify(dbm, times(3)).getQueryResultList(eq("SQL_USER_GET_USERIDS"), eq(Short.class),anyInt(),anyInt());
		verify(properties, times(3)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSearchUsersByEmail() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_EMAIL"), eq(Short.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_EMAIL"), eq(Short.class), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_EMAIL"), eq(Short.class), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchUsersByEmail();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_EMAIL"), eq(Short.class),anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSearchUsersByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_NAME"), eq(Short.class), anyString(), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_NAME"), eq(Short.class), eq(query), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_NAME"), eq(Short.class), eq(query), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchUsersByName();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_USER_SEARCH_USERIDS_BY_NAME"), eq(Short.class),anyString(), anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSaveAuthor() {
		User user = new User();
		user.setAuthor(false);
		
		User userMod = new User();
		userMod.setAuthor(true);
		userMod.setURIName(firstname + "-" + lastname);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser)))
				.thenReturn(user, user, user, userMod);		
		
		when(stringManager.generateURIname(anyString())).thenReturn(NULL_STRING);
		when(stringManager.generateURIname(eq(firstname + " " + lastname))).thenReturn(firstname + "-" + lastname);
		
		super.testSaveAuthor();
		verify(dbm).updateQuery(eq("SQL_USER_SAVE_AUTHOR"), anyInt());
		verify(dbm).updateQuery(eq("SQL_USER_SAVE_AUTHOR_URI"), anyInt(), anyString(), anyString());
	}

	@Test
	public void testSaveAdmin() {
		User user = mock(User.class);
		when(user.isAdmin()).thenReturn(false, false, true);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testSaveAdmin();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_ADMIN"), anyInt());
	}

	@Test
	public void testRemoveAdmin() {
		User user = mock(User.class);
		when(user.isAdmin()).thenReturn(true, true, false);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testRemoveAdmin();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_DELETE_ADMIN"), anyInt());
	}

	@Test
	public void testRemoveAuthor() {
		User user = mock(User.class);
		when(user.isAuthor()).thenReturn(true, true, false);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testRemoveAuthor();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_DELETE_AUTHOR"), anyInt());
	}

	@Test
	public void testGetUserBannedInfo() {
		User user = mock(User.class);
		UserBannedInfo info = mock(UserBannedInfo.class);
		when(user.isBanned()).thenReturn(false, true);		
		when(info.getReason()).thenReturn("reason");
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER_BANNED_INFO"), eq(UserBannedInfo.class), eq(idUser))).thenReturn(null, info);		
				
		super.testGetUserBannedInfo();
		verify(dbm, times(3)).getQueryResultUnique(eq("SQL_USER_GET_USER_BANNED_INFO"), eq(UserBannedInfo.class), anyInt());
	}

	@Test
	public void testBlockUser() {
		User user = mock(User.class);
		when(user.isBanned()).thenReturn(false, false, false, true);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testBlockUser();
		verify(dbm, times(3)).updateQuery(eq("SQL_USER_BLOCK"), anyInt(), anyString(),anyInt(), anyString(),anyInt());
	}

	@Test
	public void testUnblockUser() {
		User user = mock(User.class);
		when(user.isBanned()).thenReturn(true, true, false);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testUnblockUser();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_UNBLOCK"), anyInt());
	}

	@Test
	public void testGetAuthors() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_AUTHORIDS"), eq(Short.class), eq(INVALID_RESULT), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_AUTHORIDS"), eq(Short.class), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_AUTHORIDS"), eq(Short.class), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetAuthors();
		
		verify(dbm, times(3)).getQueryResultList(eq("SQL_USER_GET_AUTHORIDS"), eq(Short.class),anyInt(),anyInt());
		verify(properties, times(3)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSearchAuthorsByEmail() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_EMAIL"), eq(Short.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_EMAIL"), eq(Short.class), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_EMAIL"), eq(Short.class), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchAuthorsByEmail();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_EMAIL"), eq(Short.class), anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testSearchAuthorsByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_NAME"), eq(Short.class), anyString(), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_NAME"), eq(Short.class), eq(query), eq(query), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_NAME"), eq(Short.class), eq(query), eq(query), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testSearchAuthorsByName();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_USER_SEARCH_AUTHORIDS_BY_NAME"), eq(Short.class),anyString(), anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetAuthorFollowed() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUser))).thenReturn(ids);

		super.testGetAuthorFollowed();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USER_GET_FOLLOW_AUTHORIDS"), eq(Short.class),anyInt());
	}

	@Test
	public void testGetLessonFollowed() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_LESSONIDS"), eq(Integer.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_LESSONIDS"), eq(Integer.class), eq(idUser))).thenReturn(ids);

		super.testGetLessonFollowed();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USER_GET_FOLLOW_LESSONIDS"), eq(Integer.class),anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");

		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUser)))
			.thenReturn(ids,ids,ids, null);
		
		when(dbm.updateQuery(eq("SQL_USER_REMOVE_FOLLOW_AUTHOR"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testUnfollowAuthor();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USER_REMOVE_FOLLOW_AUTHOR"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowAuthor() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");

		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_AUTHORIDS"), eq(Short.class), eq(idUser)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_USER_ADD_FOLLOW_AUTHOR"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testFollowAuthor();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USER_ADD_FOLLOW_AUTHOR"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowLesson() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");

		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_LESSONIDS"), eq(Integer.class), eq(idUser)))
			.thenReturn(null, null, null, ids);
		
		when(dbm.updateQuery(eq("SQL_USER_ADD_FOLLOW_LESSON"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testFollowLesson();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USER_ADD_FOLLOW_LESSON"), anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowLesson() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		
		when(stringManager.getKey(anyString())).thenReturn("[1]");

		when(dbm.getQueryResultList(eq("SQL_USER_GET_FOLLOW_LESSONIDS"), eq(Integer.class), eq(idUser)))
			.thenReturn(ids, ids, ids, null);
		
		when(dbm.updateQuery(eq("SQL_USER_REMOVE_FOLLOW_LESSON"), anyInt(), anyInt())).thenReturn(-1, -1, 1);

		super.testUnfollowLesson();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_USER_REMOVE_FOLLOW_LESSON"), anyInt(), anyInt());
	}

	@Test
	public void testGetAuthorFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_AUTHOR_FOLLOWERIDS"), eq(Short.class), eq(invalidIdUser), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_AUTHOR_FOLLOWERIDS"), eq(Short.class), eq(idUser), eq(idUser))).thenReturn(ids);

		super.testGetAuthorFollowers();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USER_GET_AUTHOR_FOLLOWERIDS"), eq(Short.class),anyInt(),anyInt());
	}

	@Test
	public void testGetLessonFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_LESSON_FOLLOWERIDS"), eq(Short.class), eq(invalidIdUser), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_LESSON_FOLLOWERIDS"), eq(Short.class), eq(idUser), eq(idUser))).thenReturn(ids);

		super.testGetLessonFollowers();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USER_GET_LESSON_FOLLOWERIDS"), eq(Short.class),anyInt(),anyInt());
	}

	@Test
	public void testGetTagFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_TAG_FOLLOWERIDS"), eq(Short.class), eq(invalidIdUser))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_USER_GET_TAG_FOLLOWERIDS"), eq(Short.class), eq(idUser))).thenReturn(ids);

		super.testGetTagFollowers();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_USER_GET_TAG_FOLLOWERIDS"), eq(Short.class),anyInt());
	}

	@Test
	public void testSaveExtraInfo() {
		User user = new User();
		user.setExtraInfo(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setExtraInfo(lastname);		
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser)))
				.thenReturn(user, user, userMod);		
		
		super.testSaveExtraInfo();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_SAVE_EXTRAINFO"), anyInt(), anyString(), anyString());
	}

	@Test
	public void testRemoveExtraInfo() {
		User user = mock(User.class);
		when(user.getExtraInfo()).thenReturn(EMPTY_STRING, EMPTY_STRING, NULL_STRING);
		
		when(dbm.getQueryResultUnique(eq("SQL_USER_GET_USER"), eq(User.class), eq(idUser))).thenReturn(user);		
		
		super.testRemoveExtraInfo();
		verify(dbm, times(2)).updateQuery(eq("SQL_USER_REMOVE_EXTRAINFO"), anyInt());
	}

	@Test
	public void testGetAdmins() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		ids.add((short)2);
		
		when(dbm.getQueryResultList(eq("SQL_USER_GET_ADMINS"), eq(Short.class))).thenReturn(ids);
		super.testGetAdmins();
		verify(dbm).getQueryResultList(eq("SQL_USER_GET_ADMINS"), eq(Short.class));
	}
}
