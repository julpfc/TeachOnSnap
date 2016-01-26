package com.julvez.pfc.teachonsnap.stats.model;

import java.util.Map;

/**
 * Domain entity. Describes the global stats of a lesson test.
 * It contains the test's, number of total attempts users tried this test,
 * and a list of missed answers distribution
 */
public class StatsLessonTest{
	
	/** Test's identifier */
	private int idLessonTest;
	
	/** Number of total attempts users tried this test */
	private int numTests;
    
	/** Map of missed answers distribution */
    private Map<String, String> questionKOs; 

       
	/**
	 * Constructor
	 * @param idLessonTest Test's identifier
	 * @param numTests Number of total attempts usesr tried this test
	 * @param questionKOs Map of missed answers distribution
	 */
	public StatsLessonTest(int idLessonTest, int numTests, Map<String, String> questionKOs) {
		super();
		this.idLessonTest = idLessonTest;
		this.numTests = numTests;
		this.questionKOs = questionKOs;
	}

	/**
	 * @return Test's id
	 */
	public int getIdLessonTest() {
		return idLessonTest;
	}

	/**
	 * @return Number of total attempts users tried this test
	 */
	public int getNumTests() {
		return numTests;
	}
	
	/**
	 * @return Map of missed answers distribution
	 */
	public Map<String, String> getQuestionKOs() {
		return questionKOs;
	}

	@Override
	public String toString() {
		return "StatsLessonTest [idLessonTest=" + idLessonTest + ", numTests=" + numTests
				+ ", questionKOs=" + questionKOs + "]";
	}
}
