package com.julvez.pfc.teachonsnap.ut.media.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyShort;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.impl.MediaFileServiceImpl;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.ut.media.MediaFileServiceTest;

public class MediaFileServiceImplTest extends MediaFileServiceTest {

	@Mock
	private MediaFileRepository mediaFileRepository;
	
	@Mock
	private PropertyManager properties;
	
	@Mock
	private StringManager stringManager;
	
	@Mock
	private LogManager logger;
	
	@Mock
	private URLService urlService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private TextService textService;
	
	@Mock
	private NotifyService notifyService;
	
	@Override
	protected MediaFileService getService() {
		return new MediaFileServiceImpl(mediaFileRepository, properties, stringManager, 
				logger, urlService, userService, textService, notifyService);
	}

	@Test
	public void testGetLessonMedias() {
		List<MediaFile> medias = new ArrayList<MediaFile>();	
		medias.add(new MediaFile());
		when(mediaFileRepository.getLessonMedias(idLessonMedia)).thenReturn(medias);
		super.testGetLessonMedias();
		verify(mediaFileRepository, times(2)).getLessonMedias(anyInt());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSaveMediaFile() {
		when(properties.getNumericProperty(MediaPropertyName.MEDIAFILE_MAX_SIZE)).thenReturn((long)maxSize);
		when(stringManager.isNumeric(anyString())).thenReturn(true);
		
		when(properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE)).thenReturn((long)maxSize);
		when(properties.getNumericProperty(any(MediaPropertyName.class), anyString())).thenReturn((long)maxSize);
		when(mediaFileRepository.getAuthorQuotaUsed(anyInt())).thenReturn((long)size);
		when(mediaFileRepository.getDefaultRepositoryID()).thenReturn(idMediaRepository);
		
		when(properties.getNumericProperty(MediaPropertyName.MAX_REPOSITORY_SIZE)).thenReturn((long)maxSize);
		when(mediaFileRepository.getRepositorySize(anyShort())).thenReturn((long)size);
		
		when(mediaFileRepository.saveMediaFile(anyInt(), any(FileMetadata.class), any(MediaFileRepositoryPath.class), anyShort())).thenReturn(idMediaFile);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();	
		medias.add(new MediaFile());
		when(mediaFileRepository.getLessonMedias(idLessonMedia)).thenReturn(null, null, medias);
		
		super.testSaveMediaFile();
		
		verify(mediaFileRepository).saveMediaFile(anyInt(), any(FileMetadata.class), any(MediaFileRepositoryPath.class), anyShort());
	}

	@Test
	public void testIsAuthorQuotaExceeded() {
		when(properties.getNumericProperty(MediaPropertyName.MAX_USER_REPOSITORY_SIZE)).thenReturn((long)maxSize);
		when(properties.getNumericProperty(any(MediaPropertyName.class), anyString())).thenReturn((long)maxSize);
		when(mediaFileRepository.getAuthorQuotaUsed(anyInt())).thenReturn((long)size);
		super.testIsAuthorQuotaExceeded();
		verify(mediaFileRepository, times(2)).getAuthorQuotaUsed(anyInt());
	}

	@Test
	public void testIsRepositoryFull() {
		when(properties.getNumericProperty(MediaPropertyName.MAX_REPOSITORY_SIZE)).thenReturn((long)maxSize);
		when(mediaFileRepository.getRepositorySize(anyShort())).thenReturn((long)size);
		super.testIsRepositoryFull();
		verify(mediaFileRepository, times(2)).getRepositorySize(anyShort());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveMediaFiles() {
		
		when(mediaFileRepository.removeMediaFiles(anyInt(), any(ArrayList.class), any(MediaFileRepositoryPath.class))).thenReturn(true);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();	
		medias.add(new MediaFile());
		when(mediaFileRepository.getLessonMedias(idLessonMedia)).thenReturn(medias, medias, medias, null);
		
		super.testRemoveMediaFiles();
		
		verify(mediaFileRepository).removeMediaFiles(anyInt(), any(ArrayList.class), any(MediaFileRepositoryPath.class));
	}
}
