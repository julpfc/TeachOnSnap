package com.julvez.pfc.teachonsnap.ut.user.impl;

import static org.mockito.Matchers.anyInt;
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

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.impl.UserServiceImpl;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.ut.user.UserServiceTest;

public class UserServiceImplTest extends UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private LangService langService; 
	
	@Mock
	private URLService urlService;
	
	@Mock
	private NotifyService notifyService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private StringManager stringManager;
	
	@Mock
	private DateManager dateManager;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected UserService getService() {		
		return new UserServiceImpl(userRepository, langService, urlService, 
				notifyService, textService, stringManager, dateManager, properties);
	}
	
	@Test
	public void testGetUser() {
		User user = new User();	
		
		when(userRepository.getUser(eq(invalidIdUser))).thenReturn(null);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testGetUser();
		
		verify(userRepository).getUser(anyInt());
	}

	@Test
	public void testGetUserFromEmail() {
		User user = new User();	
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
				
		when(userRepository.getIdUserFromEmail(eq(email))).thenReturn(idUser);
		super.testGetUserFromEmail();
		verify(userRepository, times(4)).getIdUserFromEmail(anyString());
	}

	@Test
	public void testValidatePassword() {
		User user = new User();	
		user.setId(idUser);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.isValidPassword(eq(idUser), eq(password))).thenReturn(1);
		super.testValidatePassword();
		verify(userRepository, times(3)).isValidPassword( anyInt(), anyString());
	}

	@Test
	public void testSaveUserLanguage() {
		User user = mock(User.class);	
		when(user.getIdLanguage()).thenReturn((short)1, (short)1, (short)1, (short)1, (short)2);
		
		Language lang = new Language();
		lang.setId((short)1);
		when(user.getLanguage()).thenReturn(lang);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveUserLanguage();
		
		verify(userRepository).saveUserLanguage(anyInt(), anyShort());
	}

	@Test
	public void testSaveFirstName() {
		User user = mock(User.class);	
		when(user.getFirstName()).thenReturn(EMPTY_STRING, EMPTY_STRING, firstname);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveFirstName();
		
		verify(userRepository).saveFirstName(anyInt(), anyString());
	}

	@Test
	public void testSaveLastName() {
		User user = mock(User.class);	
		when(user.getLastName()).thenReturn(EMPTY_STRING, EMPTY_STRING, lastname);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveLastName();
		
		verify(userRepository).saveLastName(anyInt(), anyString());
	}

	@Test
	public void testSavePassword() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
				
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(eq(password))).thenReturn(false);
		
		when(userRepository.isValidPassword(eq(idUser), eq(password))).thenReturn(0,0,1);
		
		super.testSavePassword();
		
		verify(userRepository).savePassword(anyInt(), anyString());
	}

	@Test
	public void testSendPasswordRemind() {
		User user = mock(User.class);	
		when(user.getIdLanguage()).thenReturn((short)1, (short)1, (short)1, (short)1, (short)2);
		
		Language lang = new Language();
		lang.setId((short)1);
		when(user.getLanguage()).thenReturn(lang);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(stringManager.generateMD5(anyString())).thenReturn(token);		
		when(userRepository.getIdUserFromPasswordTemporaryToken(eq(token))).thenReturn(idUser);
		
		when(notifyService.info(eq(user), anyString(), anyString())).thenReturn(true);
		
		super.testSendPasswordRemind();
		
		verify(notifyService).info(eq(user), anyString(), anyString());		
	}

	@Test
	public void testSavePasswordTemporaryToken() {
		User user = mock(User.class);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(stringManager.generateMD5(anyString())).thenReturn(token);		
		when(userRepository.getIdUserFromPasswordTemporaryToken(eq(token))).thenReturn(idUser);
		
		super.testSavePasswordTemporaryToken();
		
		verify(userRepository).savePasswordTemporaryToken(anyInt(), anyString());
	}

	@Test
	public void testGetUserFromPasswordTemporaryToken() {
		User user = mock(User.class);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.getIdUserFromPasswordTemporaryToken(eq(token))).thenReturn(idUser);
		
		super.testGetUserFromPasswordTemporaryToken();
		
		verify(userRepository, times(4)).getIdUserFromPasswordTemporaryToken(anyString());
	}

	@Test
	public void testDeletePasswordTemporaryToken() {
		User user = mock(User.class);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.getIdUserFromPasswordTemporaryToken(eq(token))).thenReturn(idUser, idUser, invalidIdUser);
		
		super.testDeletePasswordTemporaryToken();
		
		verify(userRepository, times(1)).deletePasswordTemporaryToken(anyInt());
	}

	@Test
	public void testSendRegister() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(eq(email))).thenReturn(false);
		when(stringManager.isEmpty(eq("2"+email))).thenReturn(false);
		
		List<String> domains = new ArrayList<String>();
		domains.add("gmail.com");
		
		when(properties.getListProperty(eq(UserPropertyName.VERIFIED_EMAIL_DOMAINS))).thenReturn(domains);
		
		User user = mock(User.class);	
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
				
		when(userRepository.getIdUserFromEmail(eq(email))).thenReturn(idUser, idUser, invalidIdUser, invalidIdUser);
		when(userRepository.getIdUserFromEmail(eq("2"+email))).thenReturn(invalidIdUser);

		Language lang = new Language();
		lang.setId((short)1);
		when(user.getLanguage()).thenReturn(lang);
		
		when(stringManager.generateMD5(anyString())).thenReturn(token);		
		when(userRepository.getIdUserFromPasswordTemporaryToken(eq(token))).thenReturn(idUser);
		
		when(notifyService.info(eq(user), anyString(), anyString())).thenReturn(true);
		
		when(userRepository.createUser(eq("2"+email), anyString(), anyString(), anyShort())).thenReturn(idUser);
		
		super.testSendRegister();
	}

	@Test
	public void testCreateUser() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(eq(email+"2"))).thenReturn(false);
		
		when(userRepository.createUser(eq(email+"2"), anyString(), anyString(), anyShort())).thenReturn(idUser);
		
		User user = mock(User.class);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testCreateUser();
		
		verify(userRepository).createUser(anyString(), anyString(), anyString(), anyShort());
	}

	@Test
	public void testVerifyEmailDomain() {
		when(stringManager.isEmpty(anyString())).thenReturn(true);
		when(stringManager.isEmpty(eq(email))).thenReturn(false);
		
		List<String> domains = new ArrayList<String>();
		domains.add("gmail.com");
		
		when(properties.getListProperty(eq(UserPropertyName.VERIFIED_EMAIL_DOMAINS))).thenReturn(domains);
		
		super.testVerifyEmailDomain();
	}

	@Test
	public void testGetUsers() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.getUsers(eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.getUsers(eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetUsers();
		
		verify(userRepository, times(2)).getUsers(anyInt());
	}

	@Test
	public void testSearchUsersByEmail() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.searchUsersByEmail(eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.searchUsersByEmail(eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testSearchUsersByEmail();
		
		verify(userRepository, times(5)).searchUsersByEmail(anyString(), anyInt());
	}

	@Test
	public void testSearchUsersByName() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.searchUsersByName(eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.searchUsersByName(eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testSearchUsersByName();
		
		verify(userRepository, times(5)).searchUsersByName(anyString(), anyInt());
	}

	@Test
	public void testSaveAuthor() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAuthor()).thenReturn(false, false, true);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveAuthor();
		
		verify(userRepository).saveAuthor(anyInt(), anyString());
	}

	@Test
	public void testSaveAdmin() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAdmin()).thenReturn(false, false, true);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveAdmin();
		
		verify(userRepository).saveAdmin(anyInt());
	}

	@Test
	public void testRemoveAdmin() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAdmin()).thenReturn(true, true, false);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testRemoveAdmin();
		
		verify(userRepository).removeAdmin(anyInt());
	}

	@Test
	public void testRemoveAuthor() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isAuthor()).thenReturn(true, true, false);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testRemoveAuthor();
		
		verify(userRepository).removeAuthor(anyInt());
	}

	@Test
	public void testBlockUser() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isBanned()).thenReturn(false, false, false, false, true);
		when(user.isAdmin()).thenReturn(true);
		
		when(stringManager.isEmpty(eq("reason"))).thenReturn(false);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testBlockUser();
		
		verify(userRepository).blockUser(anyInt(), anyString(), anyInt());
	}

	@Test
	public void testUnblockUser() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.isBanned()).thenReturn(true, true, true, true, false);
		when(user.isAdmin()).thenReturn(true);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testUnblockUser();
		
		verify(userRepository).unblockUser(anyInt());
	}

	@Test
	public void testGetAuthors() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.getAuthors(eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.getAuthors(eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetAuthors();
		
		verify(userRepository, times(2)).getAuthors(anyInt());
	}

	@Test
	public void testSearchAuthorsByEmail() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.searchAuthorsByEmail(eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.searchAuthorsByEmail(eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testSearchAuthorsByEmail();
		
		verify(userRepository, times(5)).searchAuthorsByEmail(anyString(), anyInt());

	}

	@Test
	public void testSearchAuthorsByName() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2,3,4);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		List<Short> ids2 = new ArrayList<Short>();
		ids2.add((short) 3);
		ids2.add((short) 4);
		
		when(userRepository.searchAuthorsByName(eq(query), eq(FIRST_RESULT))).thenReturn(ids);
		when(userRepository.searchAuthorsByName(eq(query), eq(SECOND_RESULT))).thenReturn(ids2);
				
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testSearchAuthorsByName();
		
		verify(userRepository, times(5)).searchAuthorsByName(anyString(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowAuthor() {
		Map<String, String> idsEmpty = new HashMap<String, String>();
		
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("["+idUser+"]", ""+idUser);
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.getAuthorFollowed()).thenReturn(idsEmpty, idsEmpty, idsEmpty, idsEmpty, idsEmpty, ids);
		when(user.isAuthor()).thenReturn(true);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.followAuthor(eq(idUser), eq(idUser))).thenReturn(true);
		super.testFollowAuthor();
		
		verify(userRepository).followAuthor(anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowAuthor() {
		Map<String, String> idsEmpty = new HashMap<String, String>();
		
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("["+idUser+"]", ""+idUser);
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.getAuthorFollowed()).thenReturn(ids, ids, ids, idsEmpty);
		when(user.isAuthor()).thenReturn(true);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.unfollowAuthor(eq(idUser), eq(idUser))).thenReturn(true);
		super.testUnfollowAuthor();
		
		verify(userRepository).unfollowAuthor(anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFollowLesson() {
		Map<String, String> idsEmpty = new HashMap<String, String>();
		
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("["+idLesson+"]", ""+idLesson);
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.getLessonFollowed()).thenReturn(idsEmpty, idsEmpty, idsEmpty, idsEmpty, idsEmpty, ids);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.followLesson(eq(idUser), eq(idLesson))).thenReturn(true);
		super.testFollowLesson();
		
		verify(userRepository).followLesson(anyInt(), anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testUnfollowLesson() {
		Map<String, String> idsEmpty = new HashMap<String, String>();
		
		Map<String, String> ids = new HashMap<String, String>();
		ids.put("["+idLesson+"]", ""+idLesson);
		
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(user.getLessonFollowed()).thenReturn(ids, ids, ids, idsEmpty);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		when(userRepository.unfollowLesson(eq(idUser), eq(idLesson))).thenReturn(true);
		super.testUnfollowLesson();
		
		verify(userRepository).unfollowLesson(anyInt(), anyInt());
	}

	@Test
	public void testGetUsersFromIDs() {		
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		User user = mock(User.class);
		when(user.getId()).thenReturn(idUser);
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		super.testGetUsersFromIDs();
	}

	@Test
	public void testGetAuthorFollowers() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(userRepository.getAuthorFollowers(eq(idUser))).thenReturn(ids);
		
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetAuthorFollowers();
		
		verify(userRepository).getAuthorFollowers(anyInt());
	}

	@Test
	public void testGetLessonFollowers() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(userRepository.getLessonFollowers(eq(idLesson))).thenReturn(ids);
		
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetLessonFollowers();
		
		verify(userRepository).getLessonFollowers(anyInt());
	}

	@Test
	public void testGetTagFollowers() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(userRepository.getTagFollowers(eq(idTag))).thenReturn(ids);
		
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetTagFollowers();
		
		verify(userRepository).getTagFollowers(anyInt());
	}

	@Test
	public void testSaveExtraInfo() {
		User user = mock(User.class);	
		when(user.getExtraInfo()).thenReturn(EMPTY_STRING, EMPTY_STRING, lastname);
		
		when(userRepository.getUser(eq(idUser))).thenReturn(user);
		
		super.testSaveExtraInfo();
		
		verify(userRepository).saveExtraInfo(anyInt(), anyString());
	}

	@Test
	public void testGetAdmins() {
		User user = mock(User.class);
		when(user.getId()).thenReturn(1,1,1,1,1,2,2,2,2,2);
		
		List<Short> ids = new ArrayList<Short>();
		ids.add((short) 1);
		ids.add((short) 2);
		
		when(userRepository.getAdmins()).thenReturn(ids);
		
		when(userRepository.getUser(anyInt())).thenReturn(user);
		
		super.testGetAdmins();
		
		verify(userRepository).getAdmins();
	}
}
