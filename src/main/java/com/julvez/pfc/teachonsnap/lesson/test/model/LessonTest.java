package com.julvez.pfc.teachonsnap.lesson.test.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.julvez.pfc.teachonsnap.manager.json.JSONViews;

@Entity
@JsonIgnoreProperties({"editURL","URL","newQuestionURL"})
public class LessonTest {
	
	@Id
	@Column (name="idLessonTest")
	int id;
	int idLesson;
	@JsonView(JSONViews.Simple.class)
	short numQuestions;
	@JsonView(JSONViews.Simple.class)
	short numAnswers;
	@JsonView(JSONViews.Simple.class)
	boolean multipleChoice;
	boolean draft;
	
	
	@Transient
	@JsonView(JSONViews.Simple.class)
	List<Question> questions;



	@Override
	public String toString() {
		return "LessonTest [id=" + id + ", idLesson=" + idLesson
				+ ", numQuestions=" + numQuestions + ", numAnswers="
				+ numAnswers + ", multipleChoice=" + multipleChoice
				+ ", draft=" + draft + ", questions=" + questions + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdLesson() {
		return idLesson;
	}

	public void setIdLesson(int idLesson) {
		this.idLesson = idLesson;
	}

	public short getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(short numQuestions) {
		this.numQuestions = numQuestions;
	}

	public short getNumAnswers() {
		return numAnswers;
	}

	public void setNumAnswers(short numAnswers) {
		this.numAnswers = numAnswers;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}	

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public String getURL() {
		return "/test/"+id;
	}
	
	public String getEditURL() {
		return "/test/edit/"+id;
	}

	public String getNewQuestionURL() {
		return "/test/question/"+id;
	}

}