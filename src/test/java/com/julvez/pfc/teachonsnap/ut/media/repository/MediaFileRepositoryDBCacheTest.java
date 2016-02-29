package com.julvez.pfc.teachonsnap.ut.media.repository;

import static org.mockito.Matchers.any;
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
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryDB;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryDBCache;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;

public class MediaFileRepositoryDBCacheTest extends MediaFileRepositoryTest {
	
	@Mock
	private MediaFileRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected MediaFileRepository getRepository() {		
		return new MediaFileRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetLessonMedias() {
		List<MediaFile> medias = new ArrayList<MediaFile>();
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLessonMedia))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLessonMedia))).thenReturn(medias);
		
		super.testGetLessonMedias();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetMimeTypeID() {
		when(cache.executeImplCached(eq(repoDB), any(MediaType.class), anyString())).thenReturn(invalidIdMimeType);
		when(cache.executeImplCached(eq(repoDB), any(MediaType.class), eq(fileType))).thenReturn(idMimeType);
		super.testGetMimeTypeID();
		
		verify(cache, times(5)).executeImplCached(eq(repoDB),any(MediaType.class), anyString());
	}

	@Test
	public void testGetDefaultRepositoryID() {
		when(repoDB.getDefaultRepositoryID()).thenReturn(idMediaFileRepository);
		super.testGetDefaultRepositoryID();
		verify(repoDB, times(2)).getDefaultRepositoryID();
	}

	@Test
	public void testSaveMediaFile() {
		MediaFile media = mock(MediaFile.class);
		when(media.getFilename()).thenReturn(fileName);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();
		medias.add(media);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLessonMedia)))
		.thenReturn(null, null, null, medias);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), any(FileMetadata.class), any(MediaFileRepositoryPath.class), anyShort()))
			.thenReturn(1);
	
		super.testSaveMediaFile();
		
		verify(cache).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), any(FileMetadata.class), any(MediaFileRepositoryPath.class), anyShort());
	}

	@Test
	public void testGetMediaFileRepositoryPath() {
		MediaFileRepositoryPath repo = new MediaFileRepositoryPath();
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdMediaFileRepository))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idMediaFileRepository))).thenReturn(repo);
		
		super.testGetMediaFileRepositoryPath();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());
	}

	@Test
	public void testCreateMimeTypeID() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(MediaType.class), anyString(), anyString())).thenReturn(invalidIdMimeType);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(MediaType.class), eq(fileType), eq(fileName))).thenReturn(idMimeType);
		
		super.testCreateMimeTypeID();
		
		verify(cache, times(5)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(MediaType.class), anyString(), anyString());
	}

	@Test
	public void testRemoveMediaFiles() {
		MediaFile media = mock(MediaFile.class);
		when(media.getFilename()).thenReturn(fileName);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();
		medias.add(media);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLessonMedia)))
		.thenReturn(medias, medias, medias, null);

		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), any(ArrayList.class), any(MediaFileRepositoryPath.class)))
			.thenReturn(true);
	
		super.testRemoveMediaFiles();
		
		verify(cache).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), any(ArrayList.class), any(MediaFileRepositoryPath.class));
	}

	@Test
	public void testGetAuthorQuotaUsed() {
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdUser))).thenReturn(invalidSize);
		when(cache.executeImplCached(eq(repoDB), eq(idUser))).thenReturn(size);
		
		super.testGetAuthorQuotaUsed();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetRepositorySize() {
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdMediaFileRepository))).thenReturn(invalidSize);
		when(cache.executeImplCached(eq(repoDB), eq(idMediaFileRepository))).thenReturn(size);
		
		super.testGetRepositorySize();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyShort());
	}
}
