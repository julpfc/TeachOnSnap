package com.julvez.pfc.teachonsnap.ut.lesson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class LessonServiceTest extends ServiceTest<LessonService> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	protected String URI = "URI";
	protected String text = "text";
	protected String title = "title";
	
	@Test
	public void testGetLessonFromURI() {
		assertNotNull(test.getLessonFromURI(URI));
		
		assertNull(test.getLessonFromURI(NULL_STRING));
		assertNull(test.getLessonFromURI(EMPTY_STRING));
		assertNull(test.getLessonFromURI(BLANK_STRING));
	}

	@Test
	public void testGetLesson() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
				
		assertNull(test.getLesson(invalidIdLesson));
	}

	@Test
	public void testGetLastLessons() {
		List<Lesson> lessons = test.getLastLessons(FIRST_RESULT);		
		assertNotNull(lessons);
		
		int i=1;
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}		
		
		lessons = test.getLastLessons(SECOND_RESULT);		
		assertNotNull(lessons);
		
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}
		
		lessons = test.getLastLessons(INVALID_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
	}

	@Test
	public void testGetLessonsFromAuthor() {
		List<Lesson> lessons = test.getLessonsFromAuthor(URI, FIRST_RESULT);		
		assertNotNull(lessons);
		
		int i=1;
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}	
		
		lessons = test.getLessonsFromAuthor(URI, SECOND_RESULT);		
		assertNotNull(lessons);
		
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}
		
		lessons = test.getLessonsFromAuthor(NULL_STRING, FIRST_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
		lessons = test.getLessonsFromAuthor(EMPTY_STRING, FIRST_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
		lessons = test.getLessonsFromAuthor(BLANK_STRING, FIRST_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());		
		lessons = test.getLessonsFromAuthor(URI, INVALID_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
	}

	@Test
	public void testCreateLesson() {
		Lesson newLesson = new Lesson();
		newLesson.setIdUser(idUser);
		newLesson.setIdLanguage((short)1);
		newLesson.setTitle(title);
		newLesson.setURIname(URI);
		
		User user = mock(User.class);
		newLesson.setAuthor(user);
		
		Lesson lesson = test.createLesson(newLesson);
		assertNotNull(lesson);
		assertEquals(idLesson, lesson.getId());
		
		Lesson invalidLesson = new Lesson();
		invalidLesson.setIdUser(invalidIdUser);
		invalidLesson.setIdLanguage((short)1);
		invalidLesson.setTitle(NULL_STRING);
		invalidLesson.setURIname(NULL_STRING);
		invalidLesson.setAuthor(user);
		
		assertNull(test.createLesson(null));		
	}

	@Test
	public void testSaveLessonText() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals(EMPTY_STRING, lesson.getText());
		
		assertNull(test.saveLessonText(null, NULL_STRING));
		
		lesson = test.saveLessonText(lesson, text);
		assertNotNull(lesson);
		assertEquals(text, lesson.getText());	
	}

	@Test
	public void testNotifyLessonCreated() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		
		assertTrue(test.notifyLessonCreated(lesson));
		assertFalse(test.notifyLessonCreated(null));
	}

	@Test
	public void testNotifyLessonModified() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		
		assertTrue(test.notifyLessonModified(lesson));
		assertFalse(test.notifyLessonModified(null));
	}

	@Test
	public void testNotifyLessonPublished() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		
		assertTrue(test.notifyLessonPublished(lesson));
		assertFalse(test.notifyLessonPublished(null));
	}

	@Test
	public void testSaveLessonLanguage() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals((short)1, lesson.getIdLanguage());
		
		Language lang = new Language();
		lang.setId((short)2);
		
		assertNull(test.saveLessonLanguage(null, null));
		
		lesson = test.saveLessonLanguage(lesson, lang);
		assertNotNull(lesson);
		assertEquals((short)2, lesson.getIdLanguage());
	}

	@Test
	public void testSaveLessonTitle() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals(EMPTY_STRING, lesson.getTitle());
		
		assertNull(test.saveLessonTitle(null, NULL_STRING));
		
		lesson = test.saveLessonTitle(lesson, title);
		assertNotNull(lesson);
		assertEquals(title, lesson.getTitle());
		assertEquals(URI, lesson.getURIname());
	}

	@Test
	public void testPublish() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());
		
		test.publish(null);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());
		
		test.publish(lesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());	
	}

	@Test
	public void testRepublish() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.republish(null);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.republish(lesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());	
	}

	@Test
	public void testUnpublish() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.unpublish(null);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.unpublish(lesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());	
	}

	@Test
	public void testGetLessonDraftsFromUser() {
		User user = mock(User.class);
		
		List<Lesson> lessons = test.getLessonDraftsFromUser(user, FIRST_RESULT);		
		assertNotNull(lessons);
		
		int i=1;
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}	
		
		lessons = test.getLessonDraftsFromUser(user, SECOND_RESULT);		
		assertNotNull(lessons);
		
		for(Lesson l:lessons){
			assertEquals(i++, l.getId());
		}
		
		lessons = test.getLessonDraftsFromUser(null, FIRST_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());		
		
		lessons = test.getLessonDraftsFromUser(user, INVALID_RESULT);
		assertNotNull(lessons);
		assertEquals(0, lessons.size());
	}

	@Test
	public void testGetLessonsFromIDs() {
		Map<String, String> followedLessons = new HashMap<String, String>();
		followedLessons.put(""+idUser, ""+idUser);
		
		List<Lesson> lessons = test.getLessonsFromIDs(null);		
		assertNull(lessons);

		lessons = test.getLessonsFromIDs(followedLessons);		
		assertNotNull(lessons);
		
		short i=1;
		for(Lesson lesson:lessons){
			assertEquals(i++, lesson.getId());
		}
	}
}
