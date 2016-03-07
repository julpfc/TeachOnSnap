package com.julvez.pfc.teachonsnap.ut.lessontest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.ut.service.ServiceTest;

public abstract class LessonTestServiceTest extends ServiceTest<LessonTestService> {

	protected int idTest = 1;
	protected int invalidIdTest = -1;
	
	protected int idQuestion = 1;
	protected int invalidIdQuestion = -1;

	protected int idAnswer = 1;
	protected int invalidIdAnswer = -1;
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;
	
	protected String text = "text";
	
	protected byte priority = 1;
	protected byte invalidPriority = -1;
	protected byte newPriority = 0;
	
	@Test
	public void testGetLessonTestLesson() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		LessonTest lessonTest = test.getLessonTest(lesson);
		assertNotNull(lessonTest);
				
		assertNull(test.getLessonTest(null));
	}

	@Test
	public void testGetLessonTestInt() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
				
		assertNull(test.getLessonTest(invalidIdTest));
	}

	@Test
	public void testPublish() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
		
		test.publish(invalidIdTest);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
		
		test.publish(idTest);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
	}

	@Test
	public void testUnpublish() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
		
		test.unpublish(invalidIdTest);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
		
		
		test.unpublish(idTest);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
	}

	@Test
	public void testGetQuestion() {
		Question question = test.getQuestion(idQuestion);
		assertNotNull(question);
				
		assertNull(test.getQuestion(invalidIdQuestion));
	}

	@Test
	public void testSaveQuestion() {
		Question question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(EMPTY_STRING, question.getText());
		
		test.saveQuestion(null);

		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(EMPTY_STRING, question.getText());
		
		question.setText(text);
		test.saveQuestion(question);
		
		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(text, question.getText());
	}

	@Test
	public void testCreateQuestion() {
		Question newQuestion = new Question();
		newQuestion.setIdLessonTest(idTest);
		newQuestion.setPriority((byte)0);
		newQuestion.setText(EMPTY_STRING);
				
		Answer answer = mock(Answer.class);
		when(answer.getReason()).thenReturn(EMPTY_STRING);
		when(answer.getText()).thenReturn(EMPTY_STRING);
		when(answer.isCorrect()).thenReturn(false);
		
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(answer);
		
		newQuestion.setAnswers(answers);
		
		assertNotNull(test.createQuestion(newQuestion));
		
		Question invalidQuestion = new Question();
		
		assertNull(test.createQuestion(null));
		assertNull(test.createQuestion(invalidQuestion));
	}

	@Test
	public void testRemoveQuestion() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)1, lessonTest.getNumQuestions());
		
		Question question = test.getQuestion(idQuestion);

		assertFalse(test.removeQuestion(null));
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)1, lessonTest.getNumQuestions());
		
		assertTrue(test.removeQuestion(question));
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)0, lessonTest.getNumQuestions());
	}

	@Test
	public void testRemoveLessonTest() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		
		test.removeLessonTest(null);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		
		test.removeLessonTest(lessonTest);
		
		lessonTest = test.getLessonTest(idTest);
		assertNull(lessonTest);
	}

	@Test
	public void testMoveQuestion() {
		Question question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(priority, question.getPriority());
				
		assertFalse(test.moveQuestion(null, newPriority));
		
		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(priority, question.getPriority());
		
		assertFalse(test.moveQuestion(question, invalidPriority));
		
		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(priority, question.getPriority());
		
		assertTrue(test.moveQuestion(question, newPriority));
		
		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(newPriority, question.getPriority());	
	}

	@Test
	public void testCreateLessonTest() {
		Lesson lesson = mock(Lesson.class);
		when(lesson.getId()).thenReturn(idLesson);
		
		assertNotNull(test.createLessonTest(lesson, true, 2));

		assertNull(test.createLessonTest(null, true, 1));
	}
}
