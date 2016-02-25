package com.julvez.pfc.teachonsnap.ut.manager.json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerTest;

public abstract class JSONManagerTest extends ManagerTest<JSONManager> {

	protected final String QUESTION_JSON = "{\"text\":\"¿En qué año estamos?\",\"answers\":[{\"text\":\"1980\",\"correct\":false,\"reason\":\"No es mal año, pero no.\"},{\"text\":\"2010\",\"correct\":false,\"reason\":\"No es correcto\"},{\"text\":\"2016\",\"correct\":true,\"reason\":\"Correcto\"},{\"text\":\"Actual\",\"correct\":true,\"reason\":\";p\"}]}";
	protected final String QUESTION_JSON_FULL = "{\"id\":0,\"idLessonTest\":0,\"text\":\"¿En qué año estamos?\",\"priority\":0,\"answers\":[{\"id\":0,\"idQuestion\":0,\"text\":\"1980\",\"correct\":false,\"reason\":\"No es mal año, pero no.\"},{\"id\":0,\"idQuestion\":0,\"text\":\"2010\",\"correct\":false,\"reason\":\"No es correcto\"},{\"id\":0,\"idQuestion\":0,\"text\":\"2016\",\"correct\":true,\"reason\":\"Correcto\"},{\"id\":0,\"idQuestion\":0,\"text\":\"Actual\",\"correct\":true,\"reason\":\";p\"}]}";
	
	protected Question QUESTION;
	

	@Override
	public void setUp() {
		super.setUp();		
		createQuestion();		
	}

	protected void createQuestion() {
		List<Answer> answers = new ArrayList<Answer>(); 
		Answer answer = createAnswer("1980", false, "No es mal año, pero no.");
		answers.add(answer);
		answer = createAnswer("2010", false, "No es correcto");
		answers.add(answer);
		answer = createAnswer("2016", true, "Correcto");
		answers.add(answer);
		answer = createAnswer("Actual", true, ";p");
		answers.add(answer);
		
		QUESTION = new Question();
		QUESTION.setText("¿En qué año estamos?");
		QUESTION.setAnswers(answers);
	}

	protected Answer createAnswer(String text, boolean correct, String reason) {		
		Answer answer = new Answer();
		answer.setText(text);
		answer.setCorrect(correct);
		answer.setReason(reason);
		return answer;
	}

	@Test
	public void testJSON2Object() {		
		Assert.assertEquals(QUESTION, test.JSON2Object(QUESTION_JSON, Question.class));
		
		Assert.assertNull(test.JSON2Object(QUESTION_JSON, null));	
		Assert.assertNull(test.JSON2Object(NULL_STRING, Question.class));		
		Assert.assertNull(test.JSON2Object(BLANK_STRING, Question.class));
		Assert.assertNull(test.JSON2Object(EMPTY_STRING, Question.class));		
	}

	@Test
	public void testJSON2SimpleObject() {
		Assert.assertEquals(QUESTION, test.JSON2SimpleObject(QUESTION_JSON, Question.class));
		
		Assert.assertNull(test.JSON2SimpleObject(QUESTION_JSON, null));	
		Assert.assertNull(test.JSON2SimpleObject(NULL_STRING, Question.class));		
		Assert.assertNull(test.JSON2SimpleObject(BLANK_STRING, Question.class));
		Assert.assertNull(test.JSON2SimpleObject(EMPTY_STRING, Question.class));
	}

	@Test
	public void testObject2JSON() {
		Assert.assertEquals(QUESTION_JSON_FULL, test.object2JSON(QUESTION));		
		
		Assert.assertNull(test.object2JSON(null));
		Assert.assertEquals("\"" + Lesson.class.getName() + "\"", test.object2JSON(Lesson.class));
	}

	@Test
	public void testObject2SimpleJSON() {
		Assert.assertEquals(QUESTION_JSON, test.object2SimpleJSON(QUESTION));		
		
		Assert.assertNull(test.object2SimpleJSON(null));
		Assert.assertEquals("\"" + Lesson.class.getName() + "\"", test.object2SimpleJSON(Lesson.class));
	}

}
