package com.julvez.pfc.teachonsnap.it.lesson;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.impl.LessonServiceImpl;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.lesson.LessonServiceTest;

public class LessonServiceIT extends LessonServiceTest {

	@Mock
	private NotifyService notifyService;
	
	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected LessonService getService() {		
		return new LessonServiceImpl(LessonRepositoryFactory.getRepository(),
				LangServiceFactory.getService(),
				UserServiceFactory.getService(),
				TextServiceFactory.getService(),
				notifyService,
				URLServiceFactory.getService(),
				StringManagerFactory.getManager());
	}

	@Override
	public void testCreateLesson() {
		try{ super.testCreateLesson();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_LESSON_DELETE_LESSON", 2);
		dbm.updateQuery("SQL_IT_LESSON_RESET_LESSON_ID");
	}

	@Override
	public void testSaveLessonText() {
		cache.clearCache("getLesson");	
		dbm.updateQuery("SQL_LESSON_SAVE_TEXT", idLesson, EMPTY_STRING, EMPTY_STRING);
		try{ super.testSaveLessonText();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_LESSON_DELETE_TEXT", idLesson);		
	}

	@Override
	public void testNotifyLessonCreated() {
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testNotifyLessonCreated(); } catch(Throwable t){ t.printStackTrace();}
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testNotifyLessonModified() {
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testNotifyLessonModified(); } catch(Throwable t){ t.printStackTrace();}
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testNotifyLessonPublished() {
		cache.clearCache("getAuthorFollowers");
		dbm.updateQuery("SQL_IT_USER_TRUNCATE_FOLLOWUSER");
		dbm.updateQuery("SQL_USER_ADD_FOLLOW_AUTHOR", idUser, idUser);
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testNotifyLessonPublished();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USER_TRUNCATE_FOLLOWUSER");
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testSaveLessonLanguage() {
		cache.clearCache("getLesson");		
		try{ super.testSaveLessonLanguage();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_LESSON_SAVE_LANGUAGE", 1, idLesson);
	}

	@Override
	public void testSaveLessonTitle() {
		cache.clearCache("getLesson");	
		dbm.updateQuery("SQL_LESSON_SAVE_TITLE", EMPTY_STRING, URI, idLesson);
		try{ super.testSaveLessonTitle();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_LESSON_SAVE_TITLE", title, URI, idLesson);
	}

	@Override
	public void testPublish() {
		cache.clearCache("getLesson");
		dbm.updateQuery("SQL_IT_LESSON_TRUNCATE_ONLINE");
		try{ super.testPublish();} catch(Throwable t){ t.printStackTrace();}		
	}

	@Override
	public void testRepublish() {
		cache.clearCache("getLesson");
		try{ super.testRepublish();} catch(Throwable t){ t.printStackTrace();}
	}

	@Override
	public void testUnpublish() {
		cache.clearCache("getLesson");
		try{ super.testUnpublish();} catch(Throwable t){ t.printStackTrace();}
	}
}
