package com.julvez.pfc.teachonsnap.ut.media;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.media.MediaFileService;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.upload.model.FileMetadata;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class MediaFileServiceTest extends ServiceTest<MediaFileService> {

	protected int idLessonMedia = 1;
	protected int invalidIdLessonMedia = -1;

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;

	protected int idMediaFile = 1;
	protected int invalidIdMediaFile = -1;

	protected int size = 100;
	protected int maxSize = 1000;
	protected int invalidSize = -1;
	
	protected short idMediaRepository = 1;
	protected short invalidIdMediaRepository = -1;
	
	@Test
	public void testGetLessonMedias() {
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(1, medias.size());
		
		medias = test.getLessonMedias(invalidIdLessonMedia);
		assertNotNull(medias);
		assertEquals(0, medias.size());
	}

	@Test
	public void testSaveMediaFile() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.getIdLessonMedia()).thenReturn(idLessonMedia);
		
		User user = mock(User.class);
		when(lesson.getAuthor()).thenReturn(user);
		
		FileMetadata file = new FileMetadata();	
		file.setFileSize(""+size);
		file.setMediaType(MediaType.VIDEO);
		file.setFileType("video/mp4");
		file.setFileName("file.ext");
		
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(0, medias.size());
		
		assertEquals(invalidIdMediaFile, test.saveMediaFile(null, null));
		
		medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(0, medias.size());		
		
		assertEquals(idMediaFile, test.saveMediaFile(lesson, file));
		
		medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(1, medias.size());
	}

	@Test
	public void testGetAcceptedFileTypes() {
		List<String> medias = test.getAcceptedFileTypes();
		assertNotNull(medias);
		
		for(MediaType media:MediaType.values()){
			assertTrue(medias.contains(media.toString().toLowerCase()));
		}
	}

	@Test
	public void testRemoveMediaFiles() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		when(lesson.getIdLessonMedia()).thenReturn(idLessonMedia);
		
		List<MediaFile> medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(1, medias.size());
		
		assertNull(test.removeMediaFiles(null));
		
		medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(1, medias.size());
		
		assertNotNull(test.removeMediaFiles(lesson));
		
		medias = test.getLessonMedias(idLessonMedia);
		assertNotNull(medias);
		assertEquals(0, medias.size());
	}

	@Test
	public void testIsAuthorQuotaExceeded() {
		User user = mock(User.class);
		
		assertTrue(test.isAuthorQuotaExceeded(null, invalidSize));		
		assertTrue(test.isAuthorQuotaExceeded(user, maxSize));
		
		assertFalse(test.isAuthorQuotaExceeded(user, size));
	}

	@Test
	public void testIsRepositoryFull() {
		assertTrue(test.isRepositoryFull(invalidIdMediaRepository, invalidSize));		
		assertTrue(test.isRepositoryFull(idMediaRepository, maxSize));
		
		assertFalse(test.isRepositoryFull(idMediaRepository, size));
	}
}
