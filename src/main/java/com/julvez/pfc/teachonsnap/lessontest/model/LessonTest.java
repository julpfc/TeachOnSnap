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
 * Entity. Describes a test for a lesson. It describes the number of
 * questions, the number of possible answers per question, indicates
 * it's a multiple choice test or not, idicates if it's a draft.
 * It also include the list of questions, the urls to perform, edit
 * and edit its question. 
 */
@Entity
@JsonIgnoreProperties({"editURL","URL","newQuestionURL"})
public class LessonTest {
	
	/** Test's identifier and primary key for the entity */
	@Id
	@Column (name="idLessonTest")
	int id;
	
	/** Lesson's id*/
	int idLesson;
	
	/** Number of questions */
	@JsonView(JSONViews.Simple.class)
	short numQuestions;
	
	/** Number of answers per question */
	@JsonView(JSONViews.Simple.class)
	short numAnswers;
	
	/** Indicates if the questions are multiple choice*/
	@JsonView(JSONViews.Simple.class)
	boolean multipleChoice;
	
	/** Indicates if the test is a draft */
	boolean draft;
	
	/** List of questions */
	@Transient
	@JsonView(JSONViews.Simple.class)
	List<Question> questions;

	/** Test's URL */
	@Transient
	private String url;
	
	/** Test edition URL */
	@Transient
	private String editURL;
	
	/** New question's URL */
	@Transient
	private String questionURL;

	@Override
	public String toString() {
		return "LessonTest [id=" + id + ", idLesson=" + idLesson
				+ ", numQuestions=" + numQuestions + ", numAnswers="
				+ numAnswers + ", multipleChoice=" + multipleChoice
				+ ", draft=" + draft + ", questions=" + questions + "]";
	}

	/**
	 * @return Test's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Lesson's id
	 */
	public int getIdLesson() {
		return idLesson;
	}

	/**
	 * @return Number of questions in the test
	 */
	public short getNumQuestions() {
		return numQuestions;
	}

	/**
	 * @return Number of answers per question
	 */
	public short getNumAnswers() {
		return numAnswers;
	}

	/**
	 * @return true if it's a multiple choice test
	 */
	public boolean isMultipleChoice() {
		return multipleChoice;
	}
	
	/**
	 * @return List of questions
	 */
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * Sets the list of questions for this test
	 * @param questions List of questions
	 */
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}	

	/**
	 * @return true if the test is a draft and cannot be showed to other users
	 */
	public boolean isDraft() {
		return draft;
	}

	/**
	 * Sets if a test is a draft
	 * @param draft or not
	 */
	public void setDraft(boolean draft) {
		this.draft = draft;
	}
	
	/**
	 * Sets test's URLs
	 * @param testURL Test's URL
	 * @param editURL Test edition URL
	 * @param questionURL New question's URL
	 */
	public void setURLs(String testURL, String editURL, String questionURL){
		this.url = testURL;
		this.editURL = editURL;
		this.questionURL = questionURL;
	}

	/**
	 * @return Test's URL
	 */
	public String getURL() {
		return url + id;
	}
	
	/**
	 * @return Test edition URL
	 */
	public String getEditURL() {
		return editURL + id;
	}

	/**
	 * @return New question's URL
	 */
	public String getNewQuestionURL() {
		return questionURL + id;
	}
}
