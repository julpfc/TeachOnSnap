package com.julvez.pfc.teachonsnap.lessontest.model;


public class UserAnswer {

	Answer answer;
	boolean checked;
	
	
	public UserAnswer(Answer answer,boolean userChecked){
		this.answer = answer;
		checked = userChecked;
	}
	
		
	public boolean isChecked() {
		return checked;
	}


	public boolean isOK(){
		return checked == answer.isCorrect();
	}

	public int getId() {
		return answer.getId();
	}

	public String getText() {
		return answer.getText();
	}

	public String getReason() {
		return answer.getReason();
	}	
	
}
