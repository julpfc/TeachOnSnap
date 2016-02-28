package com.julvez.pfc.teachonsnap.ut.lessontest.repository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyByte;
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

import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepository;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepositoryDB;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepositoryDBCache;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

public class LessonTestRepositoryDBCacheTest extends LessonTestRepositoryTest {

	@Mock
	private LessonTestRepositoryDB repoDB;
	
	@Mock
	private CacheManager cache;
	
	@Mock
	private StringManager stringManager;
	
	@Override
	protected LessonTestRepository getRepository() {
		return new LessonTestRepositoryDBCache(repoDB, cache, stringManager);
	}
	
	@Test
	public void testGetLessonTest() {
		LessonTest lessonTest = new LessonTest();
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdTest))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idTest))).thenReturn(lessonTest);
		
		super.testGetLessonTest();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetLessonTestQuestionIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idTest))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdTest))).thenReturn(null);
		
		super.testGetLessonTestQuestionIDs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetQuestion() {
		Question question = new Question(); 
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdQuestion))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idQuestion))).thenReturn(question);
		
		super.testGetQuestion();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetQuestionAnswerIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(cache.executeImplCached(eq(repoDB), eq(idQuestion))).thenReturn(ids);
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdQuestion))).thenReturn(null);
		
		super.testGetQuestionAnswerIDs();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetAnswer() {
		Answer answer = new Answer();
		
		when(cache.executeImplCached(eq(repoDB), eq(invalidIdAnswer))).thenReturn(null);
		when(cache.executeImplCached(eq(repoDB), eq(idAnswer))).thenReturn(answer);
		
		super.testGetAnswer();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testGetLessonTestID() {
		when(cache.executeImplCached(eq(repoDB), anyInt())).thenReturn(invalidIdTest);
		when(cache.executeImplCached(eq(repoDB), eq(idLesson))).thenReturn(idTest);
		super.testGetLessonTestID();
		
		verify(cache, times(2)).executeImplCached(eq(repoDB), anyInt());
	}

	@Test
	public void testPublish() {
		LessonTest lessonTest = new LessonTest();
		lessonTest.setDraft(true);
		
		LessonTest lessonTestMod = new LessonTest();
		lessonTestMod.setDraft(false);		
		
		when(cache.executeImplCached(eq(repoDB), eq(idTest)))
				.thenReturn(lessonTest, lessonTest, lessonTest, lessonTestMod);		
		
		super.testPublish();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testUnpublish() {
		LessonTest lessonTest = new LessonTest();
		lessonTest.setDraft(true);
		
		LessonTest lessonTestMod = new LessonTest();
		lessonTestMod.setDraft(false);		
		
		when(cache.executeImplCached(eq(repoDB), eq(idTest)))
				.thenReturn(lessonTestMod, lessonTestMod, lessonTestMod, lessonTest);		
		
		super.testUnpublish();
		
		verify(cache, times(3)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyInt());
	}

	@Test
	public void testSaveQuestion() {
		Question question = new Question();
		question.setText(EMPTY_STRING);
		
		Question questionMod = new Question();
		questionMod.setText("text");	
		
		when(cache.executeImplCached(eq(repoDB), eq(idQuestion)))
		.thenReturn(question, question, questionMod);		

		super.testSaveQuestion();

		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString(), anyByte(), anyInt());
	}

	@Test
	public void testSaveAnswer() {
		Answer answer = new Answer();
		answer.setText(EMPTY_STRING);
		
		Answer answerMod = new Answer();
		answerMod.setText("text");	
		
		when(cache.executeImplCached(eq(repoDB), eq(idAnswer)))
		.thenReturn(answer, answer, answerMod);		

		super.testSaveAnswer();

		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyString(), anyBoolean(), anyString(), anyInt(), anyInt());
	}

	@Test
	public void testCreateQuestion() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Question.class))).thenReturn(idTest, invalidIdTest);

		super.testCreateQuestion();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(Question.class));

	}

	@Test
	public void testRemoveQuestion() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getNumQuestions()).thenReturn((short)1,(short)1,(short)1,(short)0);
				
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);

		when(cache.executeImplCached(eq(repoDB), eq(idTest))).thenReturn(lessonTest, ids, question, lessonTest, lessonTest, lessonTest);
		super.testRemoveQuestion();
		
		verify(cache).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), any(LessonTest.class), any(Question.class));

	}

	@Test
	public void testRemoveLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getQuestions()).thenReturn(new ArrayList<Question>());
		when(lessonTest.getId()).thenReturn(idTest);
		
		when(cache.executeImplCached(eq(repoDB), eq(idTest))).thenReturn(lessonTest, lessonTest, (LessonTest)null);
		
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt())).thenReturn(1);
		
		super.testRemoveLessonTest();
		
		verify(cache).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt());

	}

	@Test
	public void testCreateLessonTest() {
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyBoolean(), anyInt())).thenReturn(invalidIdTest);
		when(cache.updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), eq(idLesson), anyBoolean(), anyInt())).thenReturn(idTest);
		
		super.testCreateLessonTest();
		
		verify(cache, times(2)).updateImplCached(eq(repoDB), (String[])anyObject(), (String[])anyObject(), anyInt(), anyBoolean(), anyInt());
	}
}
