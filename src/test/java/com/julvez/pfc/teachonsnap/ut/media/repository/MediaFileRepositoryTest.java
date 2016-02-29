package com.julvez.pfc.teachonsnap.ut.media.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaFileRepositoryPath;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepository;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class MediaFileRepositoryTest extends RepositoryTest<MediaFileRepository> {

	protected int idLessonMedia = 1;
	protected int invalidIdLessonMedia = -1;
	
	protected String fileType = "video/mp4";
	protected String fileName = "video.mp4";
	
	protected short idMimeType = 1;
	protected short invalidIdMimeType = -1;

	protected short idMediaFileRepository = 1;
	protected short invalidIdMediaFileRepository = -1;
	
	protected long size = 1000;
	protected long invalidSize = -1;
	
	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idMediaFile = 1;
	protected int invalidIdMediaFile = -1;

	@Test
	public void testGetLessonMedias() {
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
				
		assertNull(test.getLessonMedias(invalidIdLessonMedia));
	}

	@Test
	public void testGetMimeTypeID() {
		assertEquals(idMimeType, test.getMimeTypeID(MediaType.VIDEO, fileType));
		
		assertEquals(invalidIdMimeType, test.getMimeTypeID(null, NULL_STRING));
		assertEquals(invalidIdMimeType, test.getMimeTypeID(MediaType.VIDEO, NULL_STRING));
		assertEquals(invalidIdMimeType, test.getMimeTypeID(MediaType.VIDEO, EMPTY_STRING));
		assertEquals(invalidIdMimeType, test.getMimeTypeID(MediaType.VIDEO, BLANK_STRING));
	}

	@Test
	public void testGetDefaultRepositoryID() {
		assertEquals(idMediaFileRepository, test.getDefaultRepositoryID());
		assertEquals(idMediaFileRepository, test.getDefaultRepositoryID());
	}

	@Test
	public void testSaveMediaFile() {
		FileMetadata file = new FileMetadata();
		file.setFileName(fileName);
		file.setFileSize(String.valueOf(size));
		file.setFileType(fileType);
		file.setMediaType(MediaType.VIDEO);		
		
		MediaFileRepositoryPath repo = mock(MediaFileRepositoryPath.class);
				
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);		
		assertNull(medias);
		
		assertEquals(invalidIdMediaFile, test.saveMediaFile(invalidIdLesson, null, null, invalidIdMimeType));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNull(medias);
		
		assertEquals(invalidIdMediaFile, test.saveMediaFile(idLesson, null, null, idMimeType));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNull(medias);
		
		assertEquals(idMediaFile, test.saveMediaFile(idLesson, file, repo, idMimeType));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNotNull(medias);
		assertEquals(fileName, ((MediaFile)medias.get(0)).getFilename());
	}

	@Test
	public void testGetMediaFileRepositoryPath() {
		MediaFileRepositoryPath repo = test.getMediaFileRepositoryPath(idMediaFileRepository);
		assertNotNull(repo);
				
		assertNull(test.getMediaFileRepositoryPath(invalidIdMediaFileRepository));
	}

	@Test
	public void testCreateMimeTypeID() {
		assertEquals(idMimeType, test.createMimeTypeID(MediaType.VIDEO, fileType, fileName));
		
		assertEquals(invalidIdMimeType, test.createMimeTypeID(null, NULL_STRING, NULL_STRING));
		assertEquals(invalidIdMimeType, test.createMimeTypeID(MediaType.VIDEO, NULL_STRING, NULL_STRING));
		assertEquals(invalidIdMimeType, test.createMimeTypeID(MediaType.VIDEO, EMPTY_STRING, NULL_STRING));
		assertEquals(invalidIdMimeType, test.createMimeTypeID(MediaType.VIDEO, BLANK_STRING, NULL_STRING));
	}

	@Test
	public void testRemoveMediaFiles() {
		MediaFileRepositoryPath repo = mock(MediaFileRepositoryPath.class);
		
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);		
		assertNotNull(medias);
		assertEquals(fileName, ((MediaFile)medias.get(0)).getFilename());
		
		assertFalse(test.removeMediaFiles(invalidIdLesson, null, null));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNotNull(medias);
		assertEquals(fileName, ((MediaFile)medias.get(0)).getFilename());
		
		assertFalse(test.removeMediaFiles(idLesson, null, null));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNotNull(medias);
		assertEquals(fileName, ((MediaFile)medias.get(0)).getFilename());
		
		assertTrue(test.removeMediaFiles(idLesson, (ArrayList<MediaFile>)medias, repo));
		
		medias = test.getLessonMedias(idLessonMedia);		
		assertNull(medias);		
	}

	@Test
	public void testGetAuthorQuotaUsed() {
		assertEquals(size, test.getAuthorQuotaUsed(idUser));
		assertEquals(invalidSize, test.getAuthorQuotaUsed(invalidIdUser));		
	}

	@Test
	public void testGetRepositorySize() {
		assertEquals(size, test.getRepositorySize(idMediaFileRepository));
		assertEquals(invalidSize, test.getRepositorySize(invalidIdMediaFileRepository));
	}
}
