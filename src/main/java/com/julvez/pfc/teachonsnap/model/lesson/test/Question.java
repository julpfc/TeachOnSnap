package com.julvez.pfc.teachonsnap.model.lesson.test;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Question {

	@Id
	@Column (name="idQuestion")
	int id;
	int idLessonTest;
	String text;
	byte priority;
	
	@Transient
	List<Answer> answers;

	@Override
	public String toString() {
		return "Question [id=" + id + ", idLessonTest=" + idLessonTest
				+ ", text=" + text + ", priority=" + priority + ", answers="
				+ answers + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdLessonTest() {
		return idLessonTest;
	}

	public void setIdLessonTest(int idLessonTest) {
		this.idLessonTest = idLessonTest;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public String getEditURL(){
		return "/test/question/"+id;
	}
	
	
}
