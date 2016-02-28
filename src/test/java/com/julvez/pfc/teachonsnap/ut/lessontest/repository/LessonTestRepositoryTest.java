package com.julvez.pfc.teachonsnap.ut.lessontest.repository;

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

import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepository;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryTest;

public abstract class LessonTestRepositoryTest extends RepositoryTest<LessonTestRepository> {

	protected int idTest = 1;
	protected int invalidIdTest = -1;
	
	protected int idQuestion = 1;
	protected int invalidIdQuestion = -1;

	protected int idAnswer = 1;
	protected int invalidIdAnswer = -1;
	
	protected int idLesson = 1;
	protected int invalidIdLesson = -1;


	@Test
	public void testGetLessonTest() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
				
		assertNull(test.getLessonTest(invalidIdTest));
	}

	@Test
	public void testGetLessonTestQuestionIDs() {
		List<Integer> ids = test.getLessonTestQuestionIDs(invalidIdTest);		
		assertNull(ids);

		ids = test.getLessonTestQuestionIDs(idTest);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetQuestion() {
		Question question = test.getQuestion(idQuestion);
		assertNotNull(question);
				
		assertNull(test.getQuestion(invalidIdQuestion));
	}

	@Test
	public void testGetQuestionAnswerIDs() {
		List<Integer> ids = test.getQuestionAnswerIDs(invalidIdQuestion);		
		assertNull(ids);

		ids = test.getQuestionAnswerIDs(idQuestion);		
		assertNotNull(ids);
		
		int i=1;
		for(int b:ids){
			assertEquals(i++, b);
		}
	}

	@Test
	public void testGetAnswer() {
		Answer answer = test.getAnswer(idAnswer);
		assertNotNull(answer);
				
		assertNull(test.getAnswer(invalidIdAnswer));
	}

	@Test
	public void testGetLessonTestID() {
		assertEquals(idTest, test.getLessonTestID(idLesson));
		
		assertEquals(invalidIdTest, test.getLessonTestID(invalidIdLesson));
	}

	@Test
	public void testPublish() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
		
		test.publish(invalidIdTest, idLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
		
		test.publish(idTest, invalidIdLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
		
		test.publish(idTest, idLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
	}

	@Test
	public void testUnpublish() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
		
		test.unpublish(invalidIdTest, idLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
		
		test.unpublish(idTest, invalidIdLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertFalse(lessonTest.isDraft());
		
		test.unpublish(idTest, idLesson);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertTrue(lessonTest.isDraft());
	}

	@Test
	public void testSaveQuestion() {
		Question question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(EMPTY_STRING, question.getText());
		
		test.saveQuestion(invalidIdQuestion, NULL_STRING, (byte)0, invalidIdTest);

		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals(EMPTY_STRING, question.getText());
		
		test.saveQuestion(idQuestion, "text", (byte)0, idTest);
		
		question = test.getQuestion(idQuestion);
		assertNotNull(question);
		assertEquals("text", question.getText());
	}

	@Test
	public void testSaveAnswer() {
		Answer answer = test.getAnswer(idAnswer);
		assertNotNull(answer);
		assertEquals(EMPTY_STRING, answer.getText());
		
		test.saveAnswer(invalidIdAnswer, NULL_STRING, false, EMPTY_STRING, invalidIdQuestion, invalidIdTest);

		answer = test.getAnswer(idAnswer);
		assertNotNull(answer);
		assertEquals(EMPTY_STRING, answer.getText());
		
		test.saveAnswer(idAnswer, "text", false, EMPTY_STRING, idQuestion, idTest);
		
		answer = test.getAnswer(idAnswer);
		assertNotNull(answer);
		assertEquals("text", answer.getText());	}

	@Test
	public void testCreateQuestion() {
		Question newQuestion = new Question();
		newQuestion.setIdLessonTest(idTest);
		newQuestion.setPriority((byte)0);
		newQuestion.setText(EMPTY_STRING);;
		
		Answer answer = mock(Answer.class);
		when(answer.getReason()).thenReturn(EMPTY_STRING);
		when(answer.getText()).thenReturn(EMPTY_STRING);
		when(answer.isCorrect()).thenReturn(false);
		
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(answer);
		
		newQuestion.setAnswers(answers);
		
		assertEquals(idQuestion, test.createQuestion(newQuestion));
		
		Question invalidQuestion = new Question();
		
		assertEquals(invalidIdQuestion, test.createQuestion(null));
		assertEquals(invalidIdQuestion, test.createQuestion(invalidQuestion));
	}

	@Test
	public void testRemoveQuestion() {
		LessonTest lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)1, lessonTest.getNumQuestions());
		
		List<Integer> questionIDs = test.getLessonTestQuestionIDs(idTest);
		assertNotNull(questionIDs);
		assertEquals(idQuestion, (int)questionIDs.get(0));
		
		Question question = test.getQuestion(idQuestion);

		test.removeQuestion(null, null);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)1, lessonTest.getNumQuestions());
		
		test.removeQuestion(lessonTest, null);
		
		lessonTest = test.getLessonTest(idTest);
		assertNotNull(lessonTest);
		assertEquals((short)1, lessonTest.getNumQuestions());
		
		test.removeQuestion(lessonTest, question);
		
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
	public void testCreateLessonTest() {
		assertEquals(idTest, test.createLessonTest(idLesson, true, 1));

		assertEquals(invalidIdTest, test.createLessonTest(invalidIdLesson, true, 1));
	}
}
