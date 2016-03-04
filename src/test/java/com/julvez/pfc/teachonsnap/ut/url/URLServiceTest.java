package com.julvez.pfc.teachonsnap.ut.url;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.media.model.MediaFile;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class URLServiceTest extends ServiceTest<URLService> {
	
	protected String host = "localhost";
	protected String protocol = "https://";
	
	protected int id = 1;
	protected int invalidId = -1;
	protected String param = "param";
	

	@Test
	public void testGetProtocol() {
		assertEquals(protocol, test.getProtocol());
	}

	@Test
	public void testGetHost() {
		assertEquals(protocol + host, test.getHost());
	}

	@Test
	public void testGetAbsoluteURL() {
		assertEquals(protocol + host + "relativeURL", test.getAbsoluteURL("relativeURL"));
	}

	@Test
	public void testGetHomeURL() {
		assertEquals(protocol + host + ControllerURI.HOME, test.getHomeURL());
	}

	@Test
	public void testGetLessonURL() {
		assertEquals(protocol + host + ControllerURI.LESSON, test.getLessonURL());
	}

	@Test
	public void testGetLessonPreviewURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_PREVIEW, test.getLessonPreviewURL());
	}

	@Test
	public void testGetLessonEditURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_EDIT, test.getLessonEditURL());
	}

	@Test
	public void testGetLessonCommentURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_COMMENT, test.getLessonCommentURL());
	}

	@Test
	public void testGetLessonNewTestURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_NEW, test.getLessonNewTestURL());
	}

	@Test
	public void testGetLessonURLLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(id);
		when(lesson.getURIname()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON + param, test.getLessonURL(lesson));
		
		assertNull(test.getLessonURL(null));	
	}

	@Test
	public void testGetLessonPreviewURLLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(id);
		when(lesson.getURIname()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON_PREVIEW + id, test.getLessonPreviewURL(lesson));
		
		assertNull(test.getLessonPreviewURL(null));	
	}

	@Test
	public void testGetLessonEditURLLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(id);
		when(lesson.getURIname()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON_EDIT + id, test.getLessonEditURL(lesson));
		
		assertNull(test.getLessonEditURL(null));	
	}

	@Test
	public void testGetLessonCommentURLLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(id);
		when(lesson.getURIname()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON_COMMENT + param, test.getLessonCommentURL(lesson));
		
		assertNull(test.getLessonCommentURL(null));	
	}

	@Test
	public void testGetLessonNewTestURLLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(id);
		when(lesson.getURIname()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_NEW + id, test.getLessonNewTestURL(lesson));
		
		assertNull(test.getLessonNewTestURL(null));	
	}

	@Test
	public void testGetAuthorURL() {
		assertEquals(protocol + host + ControllerURI.AUTHOR, test.getAuthorURL());
	}

	@Test
	public void testGetAuthorURLUser() {
		User author = mock(User.class);
		when(author.getId()).thenReturn(id);
		when(author.getURIName()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.AUTHOR + param, test.getAuthorURL(author));
		
		assertNull(test.getAuthorURL(null));	
	}

	@Test
	public void testGetAuthorDraftsURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_DRAFTS_BY_USER, test.getAuthorDraftsURL());
	}

	@Test
	public void testGetAuthorDraftsURLUser() {
		User author = mock(User.class);
		when(author.getId()).thenReturn(id);
		when(author.getURIName()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.LESSON_DRAFTS_BY_USER + id, test.getAuthorDraftsURL(author));
		
		assertNull(test.getAuthorDraftsURL(null));	
	}

	@Test
	public void testGetLessonTestURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_TEST, test.getLessonTestURL());
	}

	@Test
	public void testGetLessonTestEditURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_EDIT, test.getLessonTestEditURL());
	}

	@Test
	public void testGetLessonTestNewQuestionURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_QUESTION, test.getLessonTestNewQuestionURL());
	}

	@Test
	public void testGetLessonTestURLLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(id);
						
		assertEquals(protocol + host + ControllerURI.LESSON_TEST + id, test.getLessonTestURL(lessonTest));
		
		assertNull(test.getLessonTestURL(null));	
	}

	@Test
	public void testGetLessonTestEditURLLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(id);
						
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_EDIT + id, test.getLessonTestEditURL(lessonTest));
		
		assertNull(test.getLessonTestEditURL(null));
	}

	@Test
	public void testGetLessonTestNewQuestionURLLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(id);
						
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_QUESTION + id, test.getLessonTestNewQuestionURL(lessonTest));
		
		assertNull(test.getLessonTestNewQuestionURL(null));
	}

	@Test
	public void testGetLessonTestEditQuestionURL() {
		assertEquals(protocol + host + ControllerURI.LESSON_TEST_QUESTION + id + "/", test.getLessonTestEditQuestionURL(id));
		
		assertNull(test.getLessonTestEditQuestionURL(invalidId));
	}

	@Test
	public void testGetTagURL() {
		assertEquals(protocol + host + ControllerURI.TAG, test.getTagURL());
	}

	@Test
	public void testGetTagURLTag() {
		Tag tag = mock(Tag.class);		
		when(tag.getTag()).thenReturn(param);
				
		assertEquals(protocol + host + ControllerURI.TAG + param, test.getTagURL(tag));
		
		assertNull(test.getTagURL(null));	
	}

	@Test
	public void testGetMediaFileURL() {
		assertEquals(protocol + host + ControllerURI.RESOURCES_MEDIA, test.getMediaFileURL());
	}

	@Test
	public void testGetMediaFileURLMediaFile() {
		MediaFile media = mock(MediaFile.class);
		when(media.getId()).thenReturn(id);
		when(media.getFilename()).thenReturn(param);
		when(media.getIdLessonMedia()).thenReturn(id);
		
		assertEquals(protocol + host + ControllerURI.RESOURCES_MEDIA + "1/1/param", test.getMediaFileURL(media));
		
		assertNull(test.getMediaFileURL(null));		
	}
}
