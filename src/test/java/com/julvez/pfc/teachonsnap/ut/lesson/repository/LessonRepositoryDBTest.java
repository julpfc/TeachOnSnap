package com.julvez.pfc.teachonsnap.ut.lesson.repository;

import static org.mockito.Matchers.anyInt;
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

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryDB;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;

public class LessonRepositoryDBTest extends LessonRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Mock
	private PropertyManager properties;
	
	@Override
	protected LessonRepository getRepository() {		
		return new LessonRepositoryDB(dbm, properties);
	}
	
	@Test
	public void testGetLesson() {
		Lesson lesson = new Lesson();	
				
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(invalidIdLesson))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson))).thenReturn(lesson);
		
		super.testGetLesson();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), anyInt());
	}

	@Test
	public void testGetLessonIDFromURI() {
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSONID_FROM_URI"), eq(Integer.class), eq("URI"))).thenReturn(idLesson);
		super.testGetLessonIDFromURI();
		verify(dbm, times(4)).getQueryResultUnique(eq("SQL_LESSON_GET_LESSONID_FROM_URI"), eq(Integer.class), anyString());
	}

	@Test
	public void testGetLastLessonIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LASTLESSONIDS"), eq(Integer.class), eq(INVALID_RESULT), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LASTLESSONIDS"), eq(Integer.class), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LASTLESSONIDS"), eq(Integer.class), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetLastLessonIDs();
		
		verify(dbm, times(3)).getQueryResultList(eq("SQL_LESSON_GET_LASTLESSONIDS"), eq(Integer.class),anyInt(),anyInt());
		verify(properties, times(3)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testGetLessonIDsFromAuthor() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR"), eq(Integer.class), anyString(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR"), eq(Integer.class), eq("author"), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR"), eq(Integer.class), eq("author"), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetLessonIDsFromAuthor();
		
		verify(dbm, times(6)).getQueryResultList(eq("SQL_LESSON_GET_LESSONIDS_FROM_AUTHOR"), eq(Integer.class),anyString(), anyInt(),anyInt());
		verify(properties, times(6)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}

	@Test
	public void testCreateLesson() {
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_LESSON_CREATE_LESSON"), anyInt(), anyShort(), anyString(), anyString())).thenReturn((long)invalidIdLesson);
		when(dbm.insertQueryAndGetLastInserID(eq("SQL_LESSON_CREATE_LESSON"), eq(idUser), eq((short)1), eq("title"), eq("lesson"))).thenReturn((long)idLesson);
		
		super.testCreateLesson();
		
		verify(dbm, times(2)).insertQueryAndGetLastInserID(eq("SQL_LESSON_CREATE_LESSON"), anyInt(), anyShort(), anyString(), anyString());
	}

	@Test
	public void testSaveLessonText() {
		Lesson lesson= new Lesson();
		lesson.setText("");
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setText("text");
		
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
				.thenReturn(lesson, lesson, lessonTxt);		
		
		super.testSaveLessonText();
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSON_SAVE_TEXT"), anyInt(), anyString(), anyString());
	}

	@Test
	public void testSaveLessonLanguage() {
		Lesson lesson= new Lesson();
		lesson.setIdLanguage((short)1);
		
		Lesson lessonES= new Lesson();
		lessonES.setIdLanguage((short)2);
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
				.thenReturn(lesson, lesson, lessonES);		
		
		super.testSaveLessonLanguage();
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSON_SAVE_LANGUAGE"), anyInt(), anyInt());
	}

	@Test
	public void testSaveLessonTitle() {
		Lesson lesson= new Lesson();
		lesson.setTitle("");
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setTitle("title");
		lessonTxt.setURIname("lesson");
				
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
				.thenReturn(lesson, lesson, lessonTxt);		
		
		super.testSaveLessonTitle();
		verify(dbm, times(1)).updateQuery(eq("SQL_LESSON_SAVE_TITLE"), anyString(), anyString(), anyInt());
	}

	@Test
	public void testRemoveLessonText() {
		Lesson lesson= new Lesson();
		lesson.setText(null);
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setText("text");
				
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
				.thenReturn(lessonTxt, lessonTxt, lesson);		
		
		super.testRemoveLessonText();
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSON_DELETE_TEXT"), anyInt());
	}

	@Test
	public void testPublish() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.isDraft()).thenReturn(true, true, false);
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
			.thenReturn(lesson);		
		
		super.testPublish();
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSON_PUBLISH"), anyInt());
	}

	@Test
	public void testUnpublish() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.isDraft()).thenReturn(false, false, true);
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSON_GET_LESSON"), eq(Lesson.class), eq(idLesson)))
			.thenReturn(lesson);		
		
		super.testUnpublish();
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSON_UNPUBLISH"), anyInt());
	}

	@Test
	public void testGetDraftLessonIDsFromUser() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids.add(3);
		ids.add(4);
		
		when(properties.getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS)).thenReturn((long)2);
		
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_DRAFT_LESSONIDS_FROM_USER"), eq(Integer.class), anyInt(), anyInt(), eq((long)3))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_DRAFT_LESSONIDS_FROM_USER"), eq(Integer.class), eq(idUser), eq(FIRST_RESULT), eq((long)3))).thenReturn(ids);
		when(dbm.getQueryResultList(eq("SQL_LESSON_GET_DRAFT_LESSONIDS_FROM_USER"), eq(Integer.class), eq(idUser), eq(SECOND_RESULT), eq((long)3))).thenReturn(ids2);

		super.testGetDraftLessonIDsFromUser();
		
		verify(dbm, times(4)).getQueryResultList(eq("SQL_LESSON_GET_DRAFT_LESSONIDS_FROM_USER"), eq(Integer.class), anyInt(), anyInt(),anyInt());
		verify(properties, times(4)).getNumericProperty(LessonPropertyName.MAX_PAGE_RESULTS);
	}
}
