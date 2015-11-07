package com.julvez.pfc.teachonsnap.lesson.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.julvez.pfc.teachonsnap.manager.json.JSONViews;

@Entity
public class Answer {
	
	@Id
	@Column (name="idAnswer")
	int id;
	int idQuestion;
	@JsonView(JSONViews.Simple.class)
	String text;
	@JsonView(JSONViews.Simple.class)
	boolean correct;
	@JsonView(JSONViews.Simple.class)
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
