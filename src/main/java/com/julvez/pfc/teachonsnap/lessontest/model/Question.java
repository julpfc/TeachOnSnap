package com.julvez.pfc.teachonsnap.lessontest.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.julvez.pfc.teachonsnap.manager.json.JSONViews;

/**
 * Entity. Describes a question of a test. It describes the text of
 * the question and its order within the test. It also include the 
 * list of answers.
 */
@Entity
@JsonIgnoreProperties({"editURL","editedVersion","fullFilled"})
public class Question {

	/** Question's identifier and primary key for the entity */
	@Id
	@Column (name="idQuestion")
	int id;
	
	/** Test's id */
	int idLessonTest;
	@JsonView(JSONViews.Simple.class)
	
	/** Question's text */
	String text;
	
	/** Question's order within the test*/
	byte priority;
	
	/** List of answers */
	@Transient
	@JsonView(JSONViews.Simple.class)
	List<Answer> answers;

	/** Question edition URL */
	@Transient
	private String editURL;
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", idLessonTest=" + idLessonTest
				+ ", text=" + text + ", priority=" + priority + ", answers="
				+ answers + "]";
	}

	/**
	 * @return Question's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets question's id
	 * @param id new question's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Test's id
	 */
	public int getIdLessonTest() {
		return idLessonTest;
	}

	/**
	 * Sets test's id
	 * @param idLessonTest new question's test
	 */
	public void setIdLessonTest(int idLessonTest) {
		this.idLessonTest = idLessonTest;
	}

	/**
	 * @return Question's text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return Question's order within the test
	 */
	public byte getPriority() {
		return priority;
	}

	/**
	 * Sets question's order within the test
	 * @param priority question's order
	 */
	public void setPriority(byte priority) {
		this.priority = priority;
	}

	/**
	 * @return List of answers
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * Sets the answers of the question
	 * @param answers list of answers
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	/**
	 * Sets question's URLs
	 * @param editURL Question edition URL
	 */
	public void setURLs(String editURL){
		this.editURL = editURL;
	}
	
	/**
	 * @return Question's edition URL
	 */
	public String getEditURL(){
		return editURL + id;
	}

	/**
	 * Indicates if the question is an edited version of the other question 
	 * @param otherQ to compare this question to
	 * @return true if it's an edited version of the same question
	 */
	public boolean isEditedVersion(Question otherQ) {
		if (this == otherQ)
			return true;
		if (otherQ == null)
			return false;
		if (id != otherQ.id)
			return false;
		if (idLessonTest != otherQ.idLessonTest)
			return false;
		if (priority != otherQ.priority)
			return false;

		if (answers == null) {
			if (otherQ.answers != null)
				return false;
		} else {
			if(otherQ.answers == null){
				return false;
			}
			else{
				boolean answersOK = true;
				
				for(Answer answer:answers){
					boolean answerOK = false;
					for(Answer otherAnswer:otherQ.answers){
						if(answer.getId() == otherAnswer.getId()){
							answerOK = true;
							break;
						}
					}					
					if(!answerOK){
						answersOK = false;
						break;
					}					
				}
				
				if(!answersOK){			
					return false;
				}
			}
		}		
		return true;
	}
	
	/**
	 * Indicates if the question is fullfilled with all the required 
	 * information to be considered as that.
	 * @return tru if the question is fullfilled
	 */
	public boolean isFullFilled(){
		boolean isFullFilled = true;
		
		if(text == null || answers==null){
			isFullFilled = false;
		}
		else{
			for(Answer answer:answers){
				if(answer.getReason() == null || answer.getText() == null){
					isFullFilled = false;
					break;
				}
			}
		}
		
		return isFullFilled;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + id;
		result = prime * result + idLessonTest;
		result = prime * result + priority;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (id != other.id)
			return false;
		if (idLessonTest != other.idLessonTest)
			return false;
		if (priority != other.priority)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}
