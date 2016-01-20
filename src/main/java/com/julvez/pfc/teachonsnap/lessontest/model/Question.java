package com.julvez.pfc.teachonsnap.lessontest.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.julvez.pfc.teachonsnap.manager.json.JSONViews;

@Entity
@JsonIgnoreProperties({"editURL","editedVersion","fullFilled"})
public class Question {

	@Id
	@Column (name="idQuestion")
	int id;
	int idLessonTest;
	@JsonView(JSONViews.Simple.class)
	String text;
	byte priority;
	
	@Transient
	@JsonView(JSONViews.Simple.class)
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
		return "/test/question/"+idLessonTest+"/"+id;
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
	
	
}
