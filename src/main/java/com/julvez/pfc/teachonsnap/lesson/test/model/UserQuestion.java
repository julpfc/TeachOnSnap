package com.julvez.pfc.teachonsnap.lesson.test.model;

import java.util.ArrayList;
import java.util.List;

public class UserQuestion {

	String[] answers;
	Question question;
	List<UserAnswer> userAnswers;
	
	public UserQuestion(Question question,String[] userAnswers){
		this.question = question;
		answers = userAnswers;
	}
	
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

	public int getId() {
		return question.getId();
	}

	public String getText() {
		return question.getText();
	}
	
	public boolean isOK(){
		boolean isOK = true;
		
		for(UserAnswer userAnswer:getAnswers()){
			isOK = isOK && userAnswer.isOK();			
		}
		
		return isOK;		
	}
	
	
	
	
	
	
}
