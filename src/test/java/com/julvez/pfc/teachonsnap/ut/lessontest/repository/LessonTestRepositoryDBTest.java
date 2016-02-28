package com.julvez.pfc.teachonsnap.ut.lessontest.repository;

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
import com.julvez.pfc.teachonsnap.manager.db.DBManager;

public class LessonTestRepositoryDBTest extends LessonTestRepositoryTest {

	@Mock
	private DBManager dbm;
	
	@Override
	protected LessonTestRepository getRepository() {
		return new LessonTestRepositoryDB(dbm);
	}
	
	@Test
	public void testGetLessonTest() {
		LessonTest lessonTest = new LessonTest();	
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(invalidIdTest))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(idTest))).thenReturn(lessonTest);
		
		super.testGetLessonTest();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), anyInt());
	}

	@Test
	public void testGetLessonTestQuestionIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST"), eq(Integer.class), eq(invalidIdTest))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST"), eq(Integer.class), eq(idTest))).thenReturn(ids);

		super.testGetLessonTestQuestionIDs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST"), eq(Integer.class), anyInt());
	}

	@Test
	public void testGetQuestion() {
		Question question = new Question();	
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_QUESTION"), eq(Question.class), eq(invalidIdQuestion))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_QUESTION"), eq(Question.class), eq(idQuestion))).thenReturn(question);
		
		super.testGetQuestion();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LESSONTEST_GET_QUESTION"), eq(Question.class), anyInt());
	}

	@Test
	public void testGetQuestionAnswerIDs() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		
		when(dbm.getQueryResultList(eq("SQL_LESSONTEST_GET_ANSWERIDS_FROM_IDQUESTION"), eq(Integer.class), eq(invalidIdQuestion))).thenReturn(null);
		when(dbm.getQueryResultList(eq("SQL_LESSONTEST_GET_ANSWERIDS_FROM_IDQUESTION"), eq(Integer.class), eq(idQuestion))).thenReturn(ids);

		super.testGetQuestionAnswerIDs();
		
		verify(dbm, times(2)).getQueryResultList(eq("SQL_LESSONTEST_GET_ANSWERIDS_FROM_IDQUESTION"), eq(Integer.class), anyInt());

	}

	@Test
	public void testGetAnswer() {
		Answer answer = new Answer();	
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_ANSWER"), eq(Answer.class), eq(invalidIdAnswer))).thenReturn(null);
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_ANSWER"), eq(Answer.class), eq(idAnswer))).thenReturn(answer);
		
		super.testGetAnswer();
		
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LESSONTEST_GET_ANSWER"), eq(Answer.class), anyInt());
	}

	@Test
	public void testGetLessonTestID() {
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_LESSONTESTID_FROM_LESSONID"), eq(Integer.class), eq(idLesson))).thenReturn(idTest);
		super.testGetLessonTestID();
		verify(dbm, times(2)).getQueryResultUnique(eq("SQL_LESSONTEST_GET_LESSONTESTID_FROM_LESSONID"), eq(Integer.class), anyInt());
	}

	@Test
	public void testPublish() {
		LessonTest lessonTest = new LessonTest();
		lessonTest.setDraft(true);
		
		LessonTest lessonTestMod = new LessonTest();
		lessonTestMod.setDraft(false);		
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(idTest)))
				.thenReturn(lessonTest, lessonTest, lessonTest, lessonTestMod);		
		
		super.testPublish();
		
		verify(dbm, times(3)).updateQuery(eq("SQL_LESSONTEST_ADD_PUBLISHED"), anyInt());
	}

	@Test
	public void testUnpublish() {
		LessonTest lessonTest = new LessonTest();
		lessonTest.setDraft(true);
		
		LessonTest lessonTestMod = new LessonTest();
		lessonTestMod.setDraft(false);		
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(idTest)))
				.thenReturn(lessonTestMod, lessonTestMod, lessonTestMod, lessonTest);		
		
		super.testUnpublish();
		
		verify(dbm, times(3)).updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_PUBLISHED"), anyInt());
	}

	@Test
	public void testSaveQuestion() {
		Question question = new Question();
		question.setText(EMPTY_STRING);
		
		Question questionMod = new Question();
		questionMod.setText("text");		
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_QUESTION"), eq(Question.class), eq(idQuestion)))
				.thenReturn(question, question, questionMod);		
		
		super.testSaveQuestion();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSONTEST_SAVE_QUESTION"), anyString(), anyByte(), anyInt());
	}

	@Test
	public void testSaveAnswer() {
		Answer answer = new Answer();
		answer.setText(EMPTY_STRING);
		
		Answer answerMod = new Answer();
		answerMod.setText("text");		
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_ANSWER"), eq(Answer.class), eq(idAnswer)))
				.thenReturn(answer, answer, answerMod);		
		
		super.testSaveAnswer();
		
		verify(dbm, times(2)).updateQuery(eq("SQL_LESSONTEST_SAVE_ANSWER"), anyString(), anyBoolean(), anyString(), anyInt());
	}

	@Test
	public void testCreateQuestion() {
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE_QUESTION"), anyInt(), anyByte(), anyString())).thenReturn((long)invalidIdQuestion);
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE_QUESTION"), eq(idTest), anyByte(), anyString())).thenReturn((long)idQuestion);

		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE_ANSWER"), anyInt(), anyString(), anyString(), anyBoolean())).thenReturn((long)invalidIdAnswer);		
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE_ANSWER"), eq(idQuestion), anyString(), anyString(), anyBoolean())).thenReturn((long)idAnswer);		
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_ADD_NUM_QUESTIONS"), eq(idTest))).thenReturn(1);

		super.testCreateQuestion();
		
		verify(dbm, times(2)).insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE_QUESTION"), anyInt(), anyByte(), anyString());
	}

	@Test
	public void testRemoveQuestion() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getNumQuestions()).thenReturn((short)1,(short)1,(short)1,(short)0);
				
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(idTest))).thenReturn(lessonTest);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		when(dbm.getQueryResultList(eq("SQL_LESSONTEST_GET_QUESTIONIDS_FROM_IDLESSONTEST"), eq(Integer.class), eq(idTest))).thenReturn(ids);

		Question question = mock(Question.class);
		when(question.getId()).thenReturn(idQuestion);
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_QUESTION"), eq(Question.class), eq(idQuestion))).thenReturn(question);
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_QUESTION"), eq(idQuestion))).thenReturn(1);
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_ANSWERS"), eq(idQuestion))).thenReturn(1);
		
		super.testRemoveQuestion();
		
		verify(dbm).updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_QUESTION"), anyInt());
	}

	@Test
	public void testRemoveLessonTest() {
		LessonTest lessonTest = mock(LessonTest.class);
		when(lessonTest.getQuestions()).thenReturn(new ArrayList<Question>());
		when(lessonTest.getId()).thenReturn(idTest);
		
		when(dbm.getQueryResultUnique(eq("SQL_LESSONTEST_GET_TEST"), eq(LessonTest.class), eq(idTest))).thenReturn(lessonTest, lessonTest, (LessonTest)null);
		
		when(dbm.updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_LESSONTEST"), eq(idTest))).thenReturn(1);
		
		super.testRemoveLessonTest();
		
		verify(dbm).updateQuery_NoCommit(anyObject(), eq("SQL_LESSONTEST_REMOVE_LESSONTEST"), anyInt());
	}

	@Test
	public void testCreateLessonTest() {
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE"), anyInt(), anyBoolean(), anyInt())).thenReturn((long)invalidIdTest);
		when(dbm.insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE"), eq(idLesson), anyBoolean(), anyInt())).thenReturn((long)idTest);
		
		super.testCreateLessonTest();
		
		verify(dbm, times(2)).insertQueryAndGetLastInserID_NoCommit(anyObject(), eq("SQL_LESSONTEST_CREATE"), anyInt(), anyBoolean(), anyInt());
	}
}
