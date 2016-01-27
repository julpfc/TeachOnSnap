package com.julvez.pfc.teachonsnap.lessontest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity. Describes the question answered by 
 * a user when performed a test. It contains the
 * question itself and the list of checked answers.
 */
public class UserQuestion {

	/** Question */
	Question question;
	
	/** List of checked answers */
	List<UserAnswer> userAnswers;
	
	/** List of checks to calculate the list of checked answers */
	String[] answers;
	
	/**
	 * Constructor
	 * @param question Question
	 * @param userAnswers List of checks to calculate the list of checked answers
	 */
	public UserQuestion(Question question, String[] userAnswers){
		this.question = question;
		answers = userAnswers;
	}
	
	/**
	 * @return List of checked answers
	 */
	public List<UserAnswer> getAnswers() {
		if(userAnswers == null){
			userAnswers = new ArrayList<UserAnswer>();
			for(Answer answer:question.getAnswers()){
				boolean checked = false;
				if(answers!=null){
					for(String marked:answers){
						if(Integer.parseInt(marked) == answer.getId())
							checked=true;
					}
				}
				userAnswers.add(new UserAnswer(answer, checked));
			}
		}
		return userAnswers;
		
	}

	/**
	 * @return question's id
	 */
	public int getId() {
		return question.getId();
	}

	/**
	 * @return question's text
	 */
	public String getText() {
		return question.getText();
	}
	
	/**
	 * @return true if all answers were checked correctly
	 */
	public boolean isOK(){
		boolean isOK = true;
		
		for(UserAnswer userAnswer:getAnswers()){
			isOK = isOK && userAnswer.isOK();			
		}
		
		return isOK;		
	}

	
	@Override
	public String toString() {
		return "UserQuestion [question=" + question + ", userAnswers="
				+ userAnswers + "]";
	}
		
}
