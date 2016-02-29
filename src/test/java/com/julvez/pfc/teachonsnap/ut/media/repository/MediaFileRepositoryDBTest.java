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

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.file.FileManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaPropertyName;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryDB;

public class MediaFileRepositoryDBTest extends MediaFileRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private FileManager fileManager;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected MediaFileRepository getRepository() {
		return new MediaFileRepositoryDB(dbm, fileManager, properties);
	}

	@Test
	public void testGetLessonMedias() {
		List<MediaFile> medias = new ArrayList<MediaFile>();	
		
		when(dbm.getQueryResultList(eq("SQL_MEDIA_GET_MEDIAFILES"), eq(MediaFile.class), eq(invalidIdLessonMedia))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_MEDIA_GET_MEDIAFILES"), eq(MediaFile.class), eq(idLessonMedia))).thenReturn(medias);
		
		super.testGetLessonMedias();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_MEDIA_GET_MEDIAFILES"), eq(MediaFile.class), anyInt());
	}

	@Test
	public void testGetMimeTypeID() {
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_MIMETYPEID"), eq(Byte.class), any(MediaType.class), eq(fileType))).thenReturn((byte)idMimeType);
		super.testGetMimeTypeID();
		verify(dbm, times(3)).getQueryResultUnique(eq("SQL_MEDIA_GET_MIMETYPEID"), eq(Byte.class), any(MediaType.class), anyString());
	}

	@Test
	public void testGetDefaultRepositoryID() {
		
		when(properties.getNumericProperty(MediaPropertyName.DEFAULT_REPOSITORY)).thenReturn((long)idMediaFileRepository, (long)-1);
		super.testGetDefaultRepositoryID();
		verify(properties, times(2)).getNumericProperty(MediaPropertyName.DEFAULT_REPOSITORY);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSaveMediaFile() {
		MediaFile media = mock(MediaFile.class);
		when(media.getFilename()).thenReturn(fileName);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();
		medias.add(media);
		
		when(dbm.getQueryResultList(eq("SQL_MEDIA_GET_MEDIAFILES"), eq(MediaFile.class), eq(idLessonMedia)))
			.thenReturn(null, null, null, medias);
		
		when(fileManager.copyStream(any(InputStream.class), anyString(), anyString())).thenReturn(true);
		
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_MEDIA_CREATE_LESSONMEDIA"), anyInt())).thenReturn((long)1);
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_MEDIA_CREATE_MEDIAFILE"), anyInt(), anyShort(), anyShort(), anyString(), any(Long.class))).thenReturn((long)1);
		
		super.testSaveMediaFile();
		
		verify(dbm).insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_MEDIA_CREATE_MEDIAFILE"), anyInt(), anyShort(), anyShort(), anyString(), any(Long.class));
	}

	@Test
	public void testGetMediaFileRepositoryPath() {
		MediaFileRepositoryPath repo = new MediaFileRepositoryPath();	
		
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_MEDIAREPOSITORY"), eq(MediaFileRepositoryPath.class), eq(invalidIdMediaFileRepository))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_MEDIAREPOSITORY"), eq(MediaFileRepositoryPath.class), eq(idMediaFileRepository))).thenReturn(repo);
		
		super.testGetMediaFileRepositoryPath();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_MEDIA_GET_MEDIAREPOSITORY"), eq(MediaFileRepositoryPath.class), anyShort());
	}

	@Test
	public void testCreateMimeTypeID() {
		when(fileManager.getFileExtension(eq(fileName))).thenReturn("mp4");
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_MEDIA_CREATE_MIMETYPE"), any(MediaType.class), eq(fileType), eq("mp4"))).thenReturn((long) idMimeType);
		super.testCreateMimeTypeID();
		verify(dbm).insertQueryAndGetLastInserID(eq("SQL_MEDIA_CREATE_MIMETYPE"),any(MediaType.class), anyString(), anyString());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testRemoveMediaFiles() {
		MediaFile media = mock(MediaFile.class);
		when(media.getFilename()).thenReturn(fileName);
		
		List<MediaFile> medias = new ArrayList<MediaFile>();
		medias.add(media);
		
		when(dbm.getQueryResultList(eq("SQL_MEDIA_GET_MEDIAFILES"), eq(MediaFile.class), eq(idLessonMedia)))
			.thenReturn(medias, medias, medias, null);
		
		when(fileManager.delete(anyString(), anyString())).thenReturn(true);
				
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_MEDIA_DELETE_MEDIAFILE"), anyInt())).thenReturn(1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_MEDIA_DELETE_LESSONMEDIA"), anyInt())).thenReturn(1);
		
		super.testRemoveMediaFiles();
		
		verify(dbm).updateQuery_NoCommit(anyObject(), eq("SQL_MEDIA_DELETE_LESSONMEDIA"), anyInt());
	}

	@Test
	public void testGetAuthorQuotaUsed() {
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_AUTHOR_QUOTA_USED"), eq(BigDecimal.class), eq(invalidIdUser))).thenReturn(BigDecimal.valueOf(invalidSize));
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_AUTHOR_QUOTA_USED"), eq(BigDecimal.class), eq(idUser))).thenReturn(BigDecimal.valueOf(size));
		
		super.testGetAuthorQuotaUsed();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_MEDIA_GET_AUTHOR_QUOTA_USED"), eq(BigDecimal.class), anyInt());
	}

	@Test
	public void testGetRepositorySize() {
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_REPOSITORY_SIZE_USED"), eq(BigDecimal.class), eq(invalidIdMediaFileRepository))).thenReturn(BigDecimal.valueOf(invalidSize));
		when(dbm.getQueryResultUnique(eq("SQL_MEDIA_GET_REPOSITORY_SIZE_USED"), eq(BigDecimal.class), eq(idMediaFileRepository))).thenReturn(BigDecimal.valueOf(size));
		
		super.testGetRepositorySize();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_MEDIA_GET_REPOSITORY_SIZE_USED"), eq(BigDecimal.class), anyShort());
	}
}
