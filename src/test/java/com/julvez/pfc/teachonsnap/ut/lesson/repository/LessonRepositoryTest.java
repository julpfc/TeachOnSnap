package com.julvez.pfc.teachonsnap.ut.lesson.repository;

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
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class LessonRepositoryTest extends RepositoryTest<LessonRepository> {

	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	protected int idUser = 1;
	protected int invalidIdUser = -1;
	
	@Test
	public void testGetLesson() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
				
		assertNull(test.getLesson(invalidIdLesson));
	}

	@Test
	public void testGetLessonIDFromURI() {
		assertEquals(idLesson,test.getLessonIDFromURI("URI"));
		
		assertEquals(invalidIdLesson, test.getLessonIDFromURI(NULL_STRING));
		assertEquals(invalidIdLesson, test.getLessonIDFromURI(EMPTY_STRING));
		assertEquals(invalidIdLesson, test.getLessonIDFromURI(BLANK_STRING));
	}

	@Test
	public void testGetLastLessonIDs() {
		List<Integer> ids = test.getLastLessonIDs(FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getLastLessonIDs(SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getLastLessonIDs(INVALID_RESULT);
		assertNull(ids);	
	}

	@Test
	public void testGetLessonIDsFromAuthor() {
		List<Integer> ids = test.getLessonIDsFromAuthor("author", FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getLessonIDsFromAuthor("author", SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getLessonIDsFromAuthor(NULL_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.getLessonIDsFromAuthor(EMPTY_STRING, FIRST_RESULT);
		assertNull(ids);
		ids = test.getLessonIDsFromAuthor(BLANK_STRING, FIRST_RESULT);
		assertNull(ids);
		
		ids = test.getLessonIDsFromAuthor("author", INVALID_RESULT);
		assertNull(ids);	
	}

	@Test
	public void testCreateLesson() {
		Lesson newLesson = new Lesson();
		newLesson.setIdUser(idUser);
		newLesson.setIdLanguage((short)1);
		newLesson.setTitle("title");
		newLesson.setURIname("lesson");
		
		User user = mock(User.class);
		newLesson.setAuthor(user);
		
		assertEquals(idLesson, test.createLesson(newLesson));
		
		Lesson invalidLesson = new Lesson();
		invalidLesson.setIdUser(invalidIdUser);
		invalidLesson.setIdLanguage((short)1);
		invalidLesson.setTitle(NULL_STRING);
		invalidLesson.setURIname(NULL_STRING);
		invalidLesson.setAuthor(user);
		
		assertEquals(invalidIdLesson, test.createLesson(null));
		assertEquals(invalidIdLesson, test.createLesson(invalidLesson));
	}

	@Test
	public void testSaveLessonText() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("", lesson.getText());
		
		test.saveLessonText(invalidIdLesson, NULL_STRING);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("", lesson.getText());
		
		test.saveLessonText(idLesson, "text");
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("text", lesson.getText());	
	}

	@Test
	public void testSaveLessonLanguage() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals((short)1, lesson.getIdLanguage());
		
		test.saveLessonLanguage(invalidIdLesson, (short)2);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals((short)1, lesson.getIdLanguage());
		
		test.saveLessonLanguage(idLesson, (short)2);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals((short)2, lesson.getIdLanguage());
	}

	@Test
	public void testSaveLessonTitle() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("", lesson.getTitle());
		
		assertFalse(test.saveLessonTitle(null, NULL_STRING, NULL_STRING));
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("", lesson.getTitle());
		
		
		Lesson oldLesson = mock(Lesson.class);
		when(oldLesson.getId()).thenReturn(idLesson);
		when(oldLesson.getURIname()).thenReturn("uri");
		
		assertTrue(test.saveLessonTitle(oldLesson, "title", "lesson"));
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("title", lesson.getTitle());
		assertEquals("lesson", lesson.getURIname());
	}

	@Test
	public void testRemoveLessonText() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("text", lesson.getText());
		
		test.removeLessonText(invalidIdLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertEquals("text", lesson.getText());
		
		test.removeLessonText(idLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertNull(lesson.getText());	
	}

	@Test
	public void testPublish() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());
		
		test.publish(invalidIdLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());
		
		test.publish(idLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());	
	}

	@Test
	public void testUnpublish() {
		Lesson lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.unpublish(invalidIdLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertFalse(lesson.isDraft());
		
		test.unpublish(idLesson);
		
		lesson = test.getLesson(idLesson);
		assertNotNull(lesson);
		assertTrue(lesson.isDraft());	
	}

	@Test
	public void testGetDraftLessonIDsFromUser() {
		List<Integer> ids = test.getDraftLessonIDsFromUser(idUser, FIRST_RESULT);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}		
		
		ids = test.getDraftLessonIDsFromUser(idUser, SECOND_RESULT);		
		assertNotNull(ids);
		
		for(int b:ids){
			assertEquals(i++, b);
		}
		
		ids = test.getDraftLessonIDsFromUser(invalidIdUser, FIRST_RESULT);
		assertNull(ids);
		
		
		ids = test.getDraftLessonIDsFromUser(idUser, INVALID_RESULT);
		assertNull(ids);	
	}
}
