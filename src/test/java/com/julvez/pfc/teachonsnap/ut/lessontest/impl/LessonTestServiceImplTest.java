package com.julvez.pfc.teachonsnap.ut.lessontest.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.impl.LessonTestServiceImpl;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepository;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.ut.lessontest.LessonTestServiceTest;

public class LessonTestServiceImplTest extends LessonTestServiceTest {

	@Mock
	private LessonTestRepository lessonTestRepository;
	
	@Mock
	private URLService urlService;

	@Override
	protected LessonTestService getService() {		
		return new LessonTestServiceImpl(lessonTestRepository, urlService);
	}
	
	@Test
	public void testGetLessonTestLesson() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
		when(lessonTestRepository.getLessonTestID(idLesson)).thenReturn(idTest);
		
		super.testGetLessonTestLesson();
		
		verify(lessonTestRepository).getLessonTestID(anyInt());
	}

	@Test
	public void testGetLessonTestInt() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
		super.testGetLessonTestInt();
		
		verify(lessonTestRepository).getLessonTest(anyInt());
	}

	@Test
	public void testPublish() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTest.isDraft()).thenReturn(true, true, true, false);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
				
		super.testPublish();
		
		verify(lessonTestRepository).publish(anyInt(), anyInt());
	}

	@Test
	public void testUnpublish() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTest.isDraft()).thenReturn(false, false, false, true);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
				
		super.testUnpublish();
		
		verify(lessonTestRepository).unpublish(anyInt(), anyInt());
	}

	@Test
	public void testGetQuestion() {
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(lessonTestRepository.getQuestion(idQuestion)).thenReturn(question);
		super.testGetQuestion();
		
		verify(lessonTestRepository).getQuestion(anyInt());
	}

	@Test
	public void testSaveQuestion() {
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(question.getText()).thenReturn(EMPTY_STRING, EMPTY_STRING, text);
		when(lessonTestRepository.getQuestion(idQuestion)).thenReturn(question);
		
		super.testSaveQuestion();
		verify(lessonTestRepository).saveQuestion(anyInt(), anyString(), anyByte(), anyInt());
	}

	@Test
	public void testCreateQuestion() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
		
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(question.getIdLessonTest()).thenReturn(idTest);
		when(lessonTestRepository.getQuestion(idQuestion)).thenReturn(question);

		when(lessonTestRepository.createQuestion(any(Question.class))).thenReturn(idQuestion);
		super.testCreateQuestion();
		verify(lessonTestRepository).createQuestion(any(Question.class));
	}

	@Test
	public void testRemoveQuestion() {
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(question.getIdLessonTest()).thenReturn(idTest);
		when(lessonTestRepository.getQuestion(idQuestion)).thenReturn(question, (Question)null);
		
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);
		when(lessonTest.getNumQuestions()).thenReturn((short)1,(short)1,(short)0);
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
		
		super.testRemoveQuestion();
		
		verify(lessonTestRepository).removeQuestion(any(LessonTest.class), any(Question.class));		
	}

	@Test
	public void testRemoveLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);		
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest, lessonTest, (LessonTest)null);
		
		super.testRemoveLessonTest();
		
		verify(lessonTestRepository).removeLessonTest(any(LessonTest.class));
	}

	@Test
	public void testMoveQuestion() {
		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(question.getIdLessonTest()).thenReturn(idTest);
		when(question.getPriority()).thenReturn(priority, priority, priority, newPriority);
		when(lessonTestRepository.getQuestion(idQuestion)).thenReturn(question);
		
		super.testMoveQuestion();
	}

	@Test
	public void testCreateLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getId()).thenReturn(idTest);		
		when(lessonTestRepository.getLessonTest(idTest)).thenReturn(lessonTest);
		
		when(lessonTestRepository.createLessonTest(eq(idLesson), anyBoolean(), anyInt())).thenReturn(idTest);

		when(lessonTestRepository.getLessonTestID(idLesson)).thenReturn(invalidIdTest);
		
		super.testCreateLessonTest();
		
		verify(lessonTestRepository).createLessonTest(anyInt(), anyBoolean(), anyInt());
	}
}
