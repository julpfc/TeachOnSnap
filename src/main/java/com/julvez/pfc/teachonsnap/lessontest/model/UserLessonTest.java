package com.julvez.pfc.teachonsnap.lessontest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserLessonTest {

	Map<String,String[]> answers;
	LessonTest test;
	List<UserQuestion> userQuestions;
	
	public UserLessonTest(LessonTest lessonTest,Map<String,String[]> userAnswers){
		test = lessonTest;		
		answers = userAnswers;
	}

	public List<UserQuestion> getQuestions() {
		if(userQuestions == null){
			userQuestions = new ArrayList<UserQuestion>();
			for(Question question:test.getQuestions()){				
				userQuestions.add(new UserQuestion(question,answers.get("question_"+question.getId())));
			}
		}
		return userQuestions;
	}

	public int getNumOKs(){
		int numOK = 0;
		
		for(UserQuestion userQuestion:getQuestions()){
			numOK = numOK + (userQuestion.isOK()?1:0);			
		}
		
		return numOK;		
	}
	
	public short getNumQuestions() {
		return test.getNumQuestions();
	}

	public boolean isMultipleChoice() {
		return test.isMultipleChoice();
	}

	public int getIdLessonTest() {
		return test.getId();
	}

	public int getPoints() {
		return (100 * getNumOKs()/getNumQuestions());
	}	
	
}
