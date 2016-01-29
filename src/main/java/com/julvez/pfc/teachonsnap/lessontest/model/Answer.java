package com.julvez.pfc.teachonsnap.lessontest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.julvez.pfc.teachonsnap.manager.json.JSONManager;

/**
 * Entity. Describes an answer from a test. It's a possible answer for a
 * question. It can be correct or not. It can include a reason why it's 
 * correct or not. 
 */
@Entity
public class Answer {
	
	/** Answer's identifier and primary key for the entity */
	@Id
	@Column (name="idAnswer")
	int id;
	
	/** Question's id */
	int idQuestion;
	
	/** Answer's text */
	@JsonView(JSONManager.SimpleView.class)
	String text;
	
	/** Indicates if the answer is the correct one for the question*/
	@JsonView(JSONManager.SimpleView.class)
	boolean correct;
	
	/** Indicates the reason why it's correct or not, if present*/
	@JsonView(JSONManager.SimpleView.class)
	String reason;

	@Override
	public String toString() {
		return "Answer [id=" + id + ", idQuestion=" + idQuestion + ", text="
				+ text + ", correct=" + correct + ", reason=" + reason + "]";
	}

	/**
	 * @return answer's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the answer's id
	 * @param id new answer's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Question's id
	 */
	public int getIdQuestion() {
		return idQuestion;
	}

	/**
	 * Sets the question for this answer
	 * @param idQuestion question's id
	 */
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	/**
	 * @return answer's text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @return tru if it's the correct answer for the question
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * @return reason why the answer is the correct one for the question, or not, if present
	 */
	public String getReason() {
		return reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + idQuestion;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		Answer other = (Answer) obj;
		if (correct != other.correct)
			return false;
		if (id != other.id)
			return false;
		if (idQuestion != other.idQuestion)
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}	
	
}
