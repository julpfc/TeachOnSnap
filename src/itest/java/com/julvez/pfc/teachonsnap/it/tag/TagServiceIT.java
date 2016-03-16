package com.julvez.pfc.teachonsnap.it.tag;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.impl.TagServiceImpl;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.tag.TagServiceTest;

public class TagServiceIT extends TagServiceTest {

	@Mock
	private NotifyService notifyService;
	
	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
		
	private int idUser = 1;
	private int idUserGroup = 1;
	
	@Override
	protected TagService getService() {		
		return new TagServiceImpl(TagRepositoryFactory.getRepository(),
				LessonServiceFactory.getService(),
				UserServiceFactory.getService(),
				TextServiceFactory.getService(),
				notifyService,
				StatsServiceFactory.getService(),
				URLServiceFactory.getService(),
				StringManagerFactory.getManager());
	}

	@Override
	public void testGetLessonsFromTag() {
		cache.clearCache("getLessonIDsFromTag");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
		dbm.updateQuery("SQL_TAG_SAVE_LESSON_TAG", idLesson, idTag);
		try{ super.testGetLessonsFromTag();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
	}

	@Override
	public void testGetLessonTags() {
		cache.clearCache("getLessonTagIDs");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
		dbm.updateQuery("SQL_TAG_SAVE_LESSON_TAG", idLesson, idTag);
		try{ super.testGetLessonTags();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
	}

	@Override
	public void testAddLessonTags() {
		cache.clearCache("getLessonTagIDs");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
		try{ super.testAddLessonTags();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");		
	}

	@Override
	public void testSaveLessonTags() {
		cache.clearCache("getLessonTagIDs");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
		try{ super.testSaveLessonTags();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
	}

	@Override
	public void testRemoveLessonTags() {
		cache.clearCache("getLessonTagIDs");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");
		dbm.updateQuery("SQL_TAG_SAVE_LESSON_TAG", idLesson, idTag);
		try{ super.testRemoveLessonTags();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_LESSONTAG");		
	}

	@Override
	public void testNotifyLessonTagged() {
		cache.clearCache("getTagFollowers");
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_GROUPMEMBER");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_FOLLOWGROUPTAG");
		dbm.updateQuery("SQL_USERGROUP_ADD_USER", idUserGroup, idUser);
		dbm.updateQuery("SQL_USERGROUP_ADD_FOLLOW_TAG", idUserGroup, idTag);
		when(notifyService.info(any(User.class), anyString(), anyString())).thenReturn(true);
		try{ super.testNotifyLessonTagged();		} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_GROUPMEMBER");
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_FOLLOWGROUPTAG");
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}
}
