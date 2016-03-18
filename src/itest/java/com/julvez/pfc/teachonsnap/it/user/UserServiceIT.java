package com.julvez.pfc.teachonsnap.it.user;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.date.DateManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.impl.UserServiceImpl;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.user.UserServiceTest;

public class UserServiceIT extends UserServiceTest {

	@Mock
	private NotifyService notifyService;
	
	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected UserService getService() {
		return new UserServiceImpl(UserRepositoryFactory.getRepository(),
				LangServiceFactory.getService(),
				URLServiceFactory.getService(),
				notifyService,
				TextServiceFactory.getService(),
				StringManagerFactory.getManager(),
				DateManagerFactory.getManager(),
				PropertyManagerFactory.getManager());
	}
	
	@Override
	public void testSaveUserLanguage() {
		cache.clearCache("getUser");
		try{ super.testSaveUserLanguage(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_SAVE_LANGUAGE", (short)1, idUser);	
	}

	@Override
	public void testSaveFirstName() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_FIRSTNAME", EMPTY_STRING, idUser);
		super.testSaveFirstName();
	}

	@Override
	public void testSaveLastName() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_LASTNAME", EMPTY_STRING, idUser);
		super.testSaveLastName();
	}

	@Override
	public void testSavePassword() {
		cache.clearCache("isValidPassword");
		dbm.updateQuery("SQL_USER_SAVE_PASSWORD", EMPTY_STRING, idUser);
		super.testSavePassword();
	}

	@Override
	public void testSendPasswordRemind() {
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testSendPasswordRemind();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_TMP_TOKEN", idUser);
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testSavePasswordTemporaryToken() {
		cache.clearCache("getIdUserFromPasswordTemporaryToken");
		try{ super.testSavePasswordTemporaryToken(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_TMP_TOKEN", idUser);		
	}

	@Override
	public void testGetUserFromPasswordTemporaryToken() {
		cache.clearCache("getIdUserFromPasswordTemporaryToken");
		dbm.updateQuery("SQL_USER_SAVE_TMP_TOKEN", idUser, token, token);	
		try{ super.testGetUserFromPasswordTemporaryToken(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_TMP_TOKEN", idUser);
	}

	@Override
	public void testDeletePasswordTemporaryToken() {
		cache.clearCache("getIdUserFromPasswordTemporaryToken");
		dbm.updateQuery("SQL_USER_SAVE_TMP_TOKEN", idUser, token, token);	
		super.testDeletePasswordTemporaryToken();
	}

	@Override
	public void testSendRegister() {
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testSendRegister();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_TMP_TOKEN", 2);
		dbm.updateQuery("SQL_IT_USER_DELETE_USER", 2);
		dbm.updateQuery("SQL_IT_USER_RESET_USER_ID");
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testCreateUser() {
		try{ super.testCreateUser();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USER_DELETE_USER", 2);
		dbm.updateQuery("SQL_IT_USER_RESET_USER_ID");
	}

	@Override
	public void testSaveAuthor() {
		cache.clearCache("getUser");
		try{ super.testSaveAuthor(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_AUTHOR", idUser);
	}

	@Override
	public void testSaveAdmin() {
		cache.clearCache("getUser");
		try{ super.testSaveAdmin(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_ADMIN", idUser);
	}

	@Override
	public void testRemoveAdmin() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_ADMIN", idUser);
		super.testRemoveAdmin();
	}

	@Override
	public void testRemoveAuthor() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_AUTHOR", idUser);
		super.testRemoveAuthor();
	}

	@Override
	public void testBlockUser() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_ADMIN", idUser);
		try{ super.testBlockUser(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_UNBLOCK", idUser);
		dbm.updateQuery("SQL_USER_DELETE_ADMIN", idUser);
	}

	@Override
	public void testUnblockUser() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_ADMIN", idUser);
		dbm.updateQuery("SQL_USER_BLOCK", idUser, "reason", idUser, "reason", idUser);
		try{ super.testUnblockUser(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_UNBLOCK", idUser);
	}

	@Override
	public void testFollowAuthor() {
		cache.clearCache("getAuthorFollowed");
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_AUTHOR", idUser);
		try{ super.testFollowAuthor(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_AUTHOR", idUser);
		dbm.updateQuery("SQL_USER_REMOVE_FOLLOW_AUTHOR", idUser, idUser);
	}

	@Override
	public void testUnfollowAuthor() {
		cache.clearCache("getAuthorFollowed");
		dbm.updateQuery("SQL_USER_ADD_FOLLOW_AUTHOR", idUser, idUser);
		super.testUnfollowAuthor();
	}

	@Override
	public void testFollowLesson() {
		cache.clearCache("getLessonFollowed");
		try{ super.testFollowLesson(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_REMOVE_FOLLOW_LESSON", idUser, idLesson);
	}

	@Override
	public void testUnfollowLesson() {
		cache.clearCache("getLessonFollowed");
		dbm.updateQuery("SQL_USER_ADD_FOLLOW_LESSON", idUser, idLesson);
		super.testUnfollowLesson();
	}

	@Override
	public void testSaveExtraInfo() {
		cache.clearCache("getUser");
		dbm.updateQuery("SQL_USER_SAVE_EXTRAINFO", idUser, EMPTY_STRING, EMPTY_STRING);	
		try{ super.testSaveExtraInfo(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_REMOVE_EXTRAINFO", idUser);
	}
}
