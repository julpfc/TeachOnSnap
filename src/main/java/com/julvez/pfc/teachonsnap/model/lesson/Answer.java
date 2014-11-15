package com.julvez.pfc.teachonsnap.model.lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Answer {
	
	@Id
	@Column (name="idAnswer")
	int id;
	int idQuestion;
	String text;
	boolean correct;
	String reason;

	@Override
	public String toString() {
		return "Answer [id=" + id + ", idQuestion=" + idQuestion + ", text="
				+ text + ", correct=" + correct + ", reason=" + reason + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdQuestion() {
		return idQuestion;
	}

	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
}
