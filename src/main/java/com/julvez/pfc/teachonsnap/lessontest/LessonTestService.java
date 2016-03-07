package com.julvez.pfc.teachonsnap.lessontest;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;

/**
 * Provides the functionality to work with lesson's tests.
 */
public interface LessonTestService {

	/**
	 * Returns lesson's test
	 * @param lesson Lesson
	 * @return the test object if present, null otherwise
	 */
	public LessonTest getLessonTest(Lesson lesson);

	/**
	 * Returns lesson's test from test's id
	 * @param idLessonTest Test's id
	 * @return the test object if it's a valid id, null otherwise
	 */
	public LessonTest getLessonTest(int idLessonTest);

	/**
	 * Publishes the test. Now will be visible to other users 
	 * and could be performed.
	 * @param idLessonTest Test's id
	 */
	public void publish(int idLessonTest);

	/**
	 * Unpublishes the test. Now it won't be visible to tohers.
	 * @param idLessonTest
	 */
	public void unpublish(int idLessonTest);

	/**
	 * Returns question from question's id
	 * @param idQuestion Question's id
	 * @return the question object if it's a valid id, null otherwise
	 */
	public Question getQuestion(int idQuestion);

	/**
	 * Persists changes of the question
	 * @param question to be persisted
	 */
	public void saveQuestion(Question question);

	
	/**
	 * Creates a new question
	 * @param question contains the question to be created
	 * @return Modified test if success, null otherwise
	 */
	public LessonTest createQuestion(Question question);

	/**
	 * Removes question
	 * @param question to be removed
	 * @return true if success
	 */
	public boolean removeQuestion(Question question);

	/**
	 * Removes test from lesson
	 * @param test to be removed
	 * @return true if success
	 */
	public boolean removeLessonTest(LessonTest test);

	/**
	 * Moves a question respect the other's positions within a test
	 * @param question to be moved
	 * @param newPriority new position within the test
	 * @return true if success
	 */
	public boolean moveQuestion(Question question, int newPriority);

	/**
	 * Creates a new test for the lesson
	 * @param lesson host of the test
	 * @param multipleChoice indicates if the test's question are multiple choice
	 * @param numAnswers Number of possible answers per question
	 * @return Newly created test if success, null otherwise
	 */
	public LessonTest createLessonTest(Lesson lesson, boolean multipleChoice, int numAnswers);	
	
}
