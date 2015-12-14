package com.julvez.pfc.teachonsnap.stats.model;

import java.util.Map;

public class StatsLessonTest{
	private int idLessonTest;
	private int numTests;
    
    private Map<String, String> questionKOs; 

       
	public StatsLessonTest(int idLessonTest, int numTests, Map<String, String> questionKOs) {
		super();
		this.idLessonTest = idLessonTest;
		this.numTests = numTests;
		this.questionKOs = questionKOs;
	}

	public int getIdLessonTest() {
		return idLessonTest;
	}

	public void setIdLessonTest(int id) {
		this.idLessonTest = id;
	}

	public int getNumTests() {
		return numTests;
	}

	public void setNumTests(int numTests) {
		this.numTests = numTests;
	}

	public Map<String, String> getQuestionKOs() {
		return questionKOs;
	}

	public void setQuestionKOs(Map<String, String> questionKOs) {
		this.questionKOs = questionKOs;
	}

	@Override
	public String toString() {
		return "StatsLessonTest [idLessonTest=" + idLessonTest + ", numTests=" + numTests
				+ ", questionKOs=" + questionKOs + "]";
	}

}
