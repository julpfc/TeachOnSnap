package com.julvez.pfc.teachonsnap.ut.user.repository;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyShort;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryDB;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryDBCache;

public class UserRepositoryDBCacheTest extends UserRepositoryTest {

	@Mock
	private UserRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected UserRepository getRepository() {
		return new UserRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetUser() {
		User user = new User();	
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);
		
		super.testGetUser();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetIdUserFromEmail() {
		when(cache.executeImplCached(eq(repoDB), anyString())).thenReturn(invalidIdUser);
		when(cache.executeImplCached(eq(repoDB), eq(email))).thenReturn(idUser);
		super.testGetIdUserFromEmail();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Test
	public void testIsValidPassword() {
		when(cache.executeImplCached(eq(repoDB), anyInt(), anyString())).thenReturn(-1);
		when(cache.executeImplCached(eq(repoDB), eq(idUser), eq(password))).thenReturn(1);
		super.testIsValidPassword();
		
		verify(cache, times(5)).executeImplCached(eq(repoDB), anyInt(), anyString());
	}

	@Test
	public void testSaveUserLanguage() {
		User user= new User();
		user.setIdLanguage((short)1);
		
		User userES= new User();
		userES.setIdLanguage((short)2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, user, userES);		
		
		super.testSaveUserLanguage();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSaveFirstName() {
		User user = new User();
		user.setFirstName(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setFirstName(firstname);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, user, userMod);		
		
		super.testSaveFirstName();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testSaveLastName() {
		User user = new User();
		user.setLastName(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setLastName(lastname);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, user, userMod);		
		
		super.testSaveLastName();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testSavePassword() {
		when(cache.executeImplCached(eq(repoDB), eq(idUser), eq(password))).thenReturn(0,0,1);	
		
		super.testSavePassword();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testSavePasswordTemporaryToken() {
		when(cache.executeImplCached(eq(repoDB), eq(token))).thenReturn(invalidIdUser, invalidIdUser, idUser);
		super.testSavePasswordTemporaryToken();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testGetIdUserFromPasswordTemporaryToken() {
		when(cache.executeImplCached(eq(repoDB), anyString())).thenReturn(invalidIdUser);
		when(cache.executeImplCached(eq(repoDB), eq(token))).thenReturn(idUser);
		super.testGetIdUserFromPasswordTemporaryToken();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Test
	public void testDeletePasswordTemporaryToken() {
		when(cache.executeImplCached(eq(repoDB), eq(token))).thenReturn(idUser, idUser, invalidIdUser);
		super.testDeletePasswordTemporaryToken();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testCreateUser() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString(), anyString(), anyString(), anyShort())).thenReturn(invalidIdUser);
		when(cache.updateImplCached(eq(repoDB), eq((String[])null), eq((String[])null), eq(email), eq(firstname), eq(lastname), eq((short)1))).thenReturn(idUser);
		
		super.testCreateUser();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyString(), anyString(), anyString(), anyShort());
	}

	@Test
	public void testGetUsers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetUsers();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testSearchUsersByEmail() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchUsersByEmail();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());	
	}

	@Test
	public void testSearchUsersByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchUsersByName();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());	
	}

	@Test
	public void testSaveAuthor() {
		User user = new User();
		user.setAuthor(false);
		
		User userMod = new User();
		userMod.setAuthor(true);
		userMod.setURIName(firstname + "-" + lastname);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, user, user, userMod);		
		
		super.testSaveAuthor();
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testSaveAdmin() {
		User user = mock(User.class);
		when(user.isAdmin()).thenReturn(false, false, true);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testSaveAdmin();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testRemoveAdmin() {
		User user = mock(User.class);
		when(user.isAdmin()).thenReturn(true, true, false);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testRemoveAdmin();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testRemoveAuthor() {
		User user = mock(User.class);
		when(user.isAuthor()).thenReturn(true, true, false);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testRemoveAuthor();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testGetUserBannedInfo() {
		User user = mock(User.class);
		UserBannedInfo info = mock(UserBannedInfo.class);
		when(user.isBanned()).thenReturn(false, true);		
		when(info.getReason()).thenReturn("reason");
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, null, user, info);
		super.testGetUserBannedInfo();
		verify(cache, times(5)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testBlockUser() {
		User user = mock(User.class);
		when(user.isBanned()).thenReturn(false, false, false, true);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testBlockUser();
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString(), anyInt());
	}

	@Test
	public void testUnblockUser() {
		User user = mock(User.class);
		when(user.isBanned()).thenReturn(true, true, false);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testUnblockUser();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testGetAuthors() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetAuthors();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testSearchAuthorsByEmail() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchAuthorsByEmail();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());
	}

	@Test
	public void testSearchAuthorsByName() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids.add((short) 3);
		ids.add((short) 4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testSearchAuthorsByName();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());
	}

	@Test
	public void testGetAuthorFollowed() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");		
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(new HashMap<String, String>());
		
		super.testGetAuthorFollowed();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetLessonFollowed() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");		
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(new HashMap<String, String>());
		
		super.testGetLessonFollowed();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testUnfollowAuthor() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser)))
			.thenReturn(ids, ids, ids, new HashMap<String, String>());
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);

		super.testUnfollowAuthor();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testFollowAuthor() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser)))
			.thenReturn(new HashMap<String, String>(), new HashMap<String, String>(), new HashMap<String, String>(), ids);
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);

		super.testFollowAuthor();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testFollowLesson() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser)))
			.thenReturn(new HashMap<String, String>(), new HashMap<String, String>(), new HashMap<String, String>(), ids);
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);

		super.testFollowLesson();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testUnfollowLesson() {
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("[1]","1");
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser)))
			.thenReturn(ids, ids, ids, new HashMap<String, String>());
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt()))
			.thenReturn(false, false, true);

		super.testUnfollowLesson();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());

	}

	@Test
	public void testGetAuthorFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetAuthorFollowers();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetLessonFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetLessonFollowers();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetTagFollowers() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(null);
		
		super.testGetTagFollowers();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testSaveExtraInfo() {
		User user = new User();
		user.setExtraInfo(EMPTY_STRING);
		
		User userMod = new User();
		userMod.setExtraInfo(lastname);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user, user, userMod);		
		
		super.testSaveExtraInfo();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testRemoveExtraInfo() {
		User user = mock(User.class);
		when(user.getExtraInfo()).thenReturn(EMPTY_STRING, EMPTY_STRING, NULL_STRING);
		
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(user);		
		
		super.testRemoveExtraInfo();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testGetAdmins() {
		List<Short> ids = new ArrayList<Short>();
		ids.add((short)1);
		ids.add((short)2);
		
		when(cache.executeImplCached(eq(repoDB))).thenReturn(ids);
		super.testGetAdmins();
		verify(cache).executeImplCached(eq(repoDB));
	}
}
