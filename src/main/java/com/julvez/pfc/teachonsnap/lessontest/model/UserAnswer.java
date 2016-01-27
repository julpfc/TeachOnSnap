package com.julvez.pfc.teachonsnap.lessontest.model;

/**
 * Domain entity. Describes the answer checked by 
 * a user when performed a test. It contains the
 * answer itself and iddicates if it's checked or not.
 */
public class UserAnswer {

	/** Answer */
	private Answer answer;
	
	/** Indicates if it's checked by the user*/
	private boolean checked;
	
	
	/**
	 * Constructor
	 * @param answer Answer
	 * @param userChecked Indicates if it's checked by the user
	 */
	public UserAnswer(Answer answer, boolean userChecked){
		this.answer = answer;
		checked = userChecked;
	}
			
	/**
	 * @return true if the answer was checked by the user
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @return true if the answer was correctly marked or not
	 */
	public boolean isOK(){
		return checked == answer.isCorrect();
	}

	/**
	 * @return Answer's id
	 */
	public int getId() {
		return answer.getId();
	}

	/**
	 * @return ANswer's text
	 */
	public String getText() {
		return answer.getText();
	}

	/**
	 * @return Answer's reason
	 */
	public String getReason() {
		return answer.getReason();
	}

	@Override
	public String toString() {
		return "UserAnswer [answer=" + answer + ", checked=" + checked + "]";
	}	
		
}
