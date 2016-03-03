package com.julvez.pfc.teachonsnap.ut.lesson.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
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
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryDB;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryDBCache;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public class LessonRepositoryDBCacheTest extends LessonRepositoryTest {

	@Mock
	private LessonRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected LessonRepository getRepository() {
		return new LessonRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetLesson() {
		Lesson lesson = new Lesson();	
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdLesson))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson);
		
		super.testGetLesson();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetLessonIDFromURI() {
		when(cache.executeImplCached(eq(repoDB), anyString())).thenReturn(invalidIdLesson);
		when(cache.executeImplCached(eq(repoDB), eq("URI"))).thenReturn(idLesson);
		super.testGetLessonIDFromURI();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyString());
	}

	@Test
	public void testGetLastLessonIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(cache.executeImplCached(eq(repoDB), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(SECOND_RESULT))).thenReturn(ids2);
		when(cache.executeImplCached(eq(repoDB), eq(INVALID_RESULT))).thenReturn(null);
		
		super.testGetLastLessonIDs();
		
		verify(cache, times(3)).executeImplCached(eq(repoDB),anyInt());	
	}

	@Test
	public void testGetLessonIDsFromAuthor() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(cache.executeImplCached(eq(repoDB), anyString(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq("author"), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq("author"), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testGetLessonIDsFromAuthor();
		
		verify(cache, times(6)).executeImplCached(eq(repoDB), anyString(), anyInt());	
	}

	@Test
	public void testCreateLesson() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Lesson.class))).thenReturn((int)idLesson, (int)invalidIdLesson);

		super.testCreateLesson();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Lesson.class));
	}

	@Test
	public void testSaveLessonText() {
		Lesson lesson= new Lesson();
		lesson.setText("");
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setText("text");
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson, lesson, lessonTxt);		
		
		super.testSaveLessonText();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString());
	}

	@Test
	public void testSaveLessonLanguage() {
		Lesson lesson= new Lesson();
		lesson.setIdLanguage((short)1);
		
		Lesson lessonES= new Lesson();
		lessonES.setIdLanguage((short)2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson, lesson, lessonES);		
		
		super.testSaveLessonLanguage();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSaveLessonTitle() {
		Lesson lesson= new Lesson();
		lesson.setTitle("");
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setTitle("title");
		lessonTxt.setURIname("lesson");
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson, lesson, lessonTxt);		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Lesson.class), anyString(), anyString())).thenReturn(true);
		
		super.testSaveLessonTitle();
		verify(cache, times(1)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Lesson.class), anyString(), anyString());
	}

	@Test
	public void testRemoveLessonText() {
		Lesson lesson= new Lesson();
		lesson.setText(null);
		
		Lesson lessonTxt= new Lesson();
		lessonTxt.setText("text");
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lessonTxt, lessonTxt, lesson);		
		
		super.testRemoveLessonText();
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());

	}

	@Test
	public void testPublish() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.isDraft()).thenReturn(true, true, false);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson);		
		super.testPublish();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testUnpublish() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.isDraft()).thenReturn(false, false, true);
		
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(lesson);		
		super.testUnpublish();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());
	}

	@Test
	public void testGetDraftLessonIDsFromUser() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		List<Integer> ids2 = new ArrayList<Integer>();
		ids2.add(3);
		ids2.add(4);
		
		when(cache.executeImplCached(eq(repoDB), anyInt(), anyInt())).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idUser), eq(FIRST_RESULT))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(idUser), eq(SECOND_RESULT))).thenReturn(ids2);
		
		super.testGetDraftLessonIDsFromUser();
		
		verify(cache, times(4)).executeImplCached(eq(repoDB), anyInt(), anyInt());	
	}
}
