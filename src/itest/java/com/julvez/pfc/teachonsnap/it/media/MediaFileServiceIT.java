package com.julvez.pfc.teachonsnap.it.media;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.impl.MediaFileServiceImpl;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryDB;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryDBCache;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.media.MediaFileServiceTest;

public class MediaFileServiceIT extends MediaFileServiceTest {

	@Mock
	private NotifyService notifyService;
	
	@Mock
	private FileManager fileManager;
	
	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected MediaFileService getService() {
		MediaFileRepository mediaRepository = new MediaFileRepositoryDBCache(
				new MediaFileRepositoryDB(DBManagerFactory.getManager(),
						fileManager,
						PropertyManagerFactory.getManager()),
				CacheManagerFactory.getManager(),
				StringManagerFactory.getManager());
		
		return new MediaFileServiceImpl(mediaRepository,
				PropertyManagerFactory.getManager(),
				StringManagerFactory.getManager(),
				LogManagerFactory.getManager(),
				URLServiceFactory.getService(),
				UserServiceFactory.getService(),
				TextServiceFactory.getService(),
				notifyService);
	}

	@Override
	public void testSaveMediaFile() {		
		cache.clearCache("getLessonMedias");
		dbm.updateQuery("SQL_IT_MEDIA_DELETE_FILE");
		dbm.updateQuery("SQL_IT_MEDIA_RESET_FILE_ID");
		dbm.updateQuery("SQL_IT_MEDIA_DELETE_LESSONMEDIA");
		dbm.updateQuery("SQL_IT_MEDIA_RESET_LESSONMEDIA_ID");
		when(fileManager.copyStream(any(InputStream.class), anyString(), anyString())).thenReturn(true);
		try{ super.testSaveMediaFile();} catch(Throwable t){ t.printStackTrace();}
		verify(fileManager).copyStream(any(InputStream.class), anyString(), anyString());
	}

	@Override
	public void testRemoveMediaFiles() {
		cache.clearCache("getLessonMedias");
		when(fileManager.delete(anyString(), anyString())).thenReturn(true);
		super.testRemoveMediaFiles();
		verify(fileManager).delete(anyString(), anyString());
	}

	@Override
	public void testIsAuthorQuotaExceeded() {		
		super.testIsAuthorQuotaExceeded();
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}

	@Override
	public void testIsRepositoryFull() {
		dbm.updateQuery("SQL_USER_SAVE_ADMIN", 1);
		try{ super.testIsRepositoryFull();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USER_DELETE_ADMIN", 1);
		verify(notifyService).info(any(User.class), anyString(), anyString());
	}
}
