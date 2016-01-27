package com.julvez.pfc.teachonsnap.lessontest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Domain entity. Describes the test performed by a user. 
 * It contains the test itself and the list of answered questions.
 */
public class UserLessonTest {

	/** Test */
	LessonTest test;
	
	/** List of answered questions */
	List<UserQuestion> userQuestions;
	
	/** Map with checked answers for each question in the test */
	Map<String,String[]> answers;
	
	
	/**
	 * Constructor
	 * @param lessonTest Test
	 * @param userAnswers Map with checked answers for each question in the test
	 */
	public UserLessonTest(LessonTest lessonTest, Map<String,String[]> userAnswers){
		test = lessonTest;		
		answers = userAnswers;
	}

	/**
	 * @return List of answered questions
	 */
	public List<UserQuestion> getQuestions() {
		if(userQuestions == null){
			if(answers != null){
				userQuestions = new ArrayList<UserQuestion>();
				for(Question question:test.getQuestions()){				
					userQuestions.add(new UserQuestion(question,answers.get("question_"+question.getId())));
				}
			}
		}
		return userQuestions;
	}

	/**
	 * @return Number of correct answered questions
	 */
	public int getNumOKs(){
		int numOK = 0;
		
		for(UserQuestion userQuestion:getQuestions()){
			numOK = numOK + (userQuestion.isOK()?1:0);			
		}
		
		return numOK;		
	}
		
	/**
	 * @return Test's number of questions
	 */
	public short getNumQuestions() {
		return test.getNumQuestions();
	}

	/**
	 * @return true if the test is multiple choice
	 */
	public boolean isMultipleChoice() {
		return test.isMultipleChoice();
	}

	/**
	 * @return Test's id
	 */
	public int getIdLessonTest() {
		return test.getId();
	}

	/**
	 * @return Total points obtained by user
	 */
	public int getPoints() {
		return (100 * getNumOKs()/getNumQuestions());
	}

	@Override
	public String toString() {
		return "UserLessonTest [test=" + test + ", userQuestions="
				+ userQuestions + "]";
	}	
	
}
