package com.julvez.pfc.teachonsnap.lessontest.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;

/**
 * Repository to access/modify data related to comments.
 * <p>
 * To be used only by the {@link LessonTestService}'s implementation
 */
public interface LessonTestRepository {

	/**
	 * Returns lesson's test from test's id
	 * @param idLessonTest Test's id
	 * @return the test object if it's a valid id, null otherwise
	 */
	public LessonTest getLessonTest(int idLessonTest);

	/**
	 * Returns list of questions ids for this test
	 * @param idLessonTest Test's id
	 * @return list of questions ids
	 */
	public List<Integer> getLessonTestQuestionIDs(int idLessonTest);

	/**
	 * Returns question from question's id
	 * @param idQuestion Question's id
	 * @return the question object if it's a valid id, null otherwise
	 */
	public Question getQuestion(int idQuestion);

	/**
	 * Returns list of answers ids for this question
	 * @param idQuestion QUestion's id
	 * @return list of answers
	 */
	public List<Integer> getQuestionAnswerIDs(int idQuestion);

	/**
	 * Returns answer from answer's id
	 * @param idAnswer Answer's id
	 * @return the answer object if it's a valid id, null otherwise
	 */
	public Answer getAnswer(int idAnswer);

	/**
	 * Returns lesson's test id
	 * @param idLesson Lesson
	 * @return the test id if present, null otherwise
	 */
	public int getLessonTestID(int idLesson);

	/**
	 * Publishes the test. Now will be visible to other users 
	 * and could be performed.
	 * @param idLessonTest Test's id
	 * @param idLesson Lessons's id
	 */
	public void publish(int idLessonTest, int idLesson);

	/**
	 * Unpublishes the test. Now it won't be visible to tohers.
	 * @param idLessonTest
	 * @param idLesson Lessons's id
	 */
	public void unpublish(int idLessonTest, int idLesson);

	/**
	 * Persists changes of the question
	 * @param idQuestion Question's id
	 * @param text Question's text
	 * @param priority Question's priority
	 * @param idLessonTest Test's id
	 */
	public void saveQuestion(int idQuestion, String text, byte priority, int idLessonTest);

	/**
	 * Persists changes of the answer
	 * @param idAnswer Answer's id
	 * @param text Answer's text
	 * @param correct Indicates if the answer is the/one of correct one/s
	 * @param reason why the answer is correct or not
	 * @param idQuestion Question's id
	 * @param idLessonTest Test's id
	 */
	public void saveAnswer(int idAnswer, String text, boolean correct, 
			String reason, int idQuestion, int idLessonTest);

	/**
	 * Creates a new question
	 * @param question contains the question to be created
	 * @return Question's id if success, -1 otherwise
	 */
	public int createQuestion(Question question);

	/**
	 * Removes question
	 * @param test which question will be removed from
	 * @param question to be removed
	 */
	public void removeQuestion(LessonTest test, Question question);

	/**
	 * Removes test from lesson
	 * @param test to be removed
	 */
	public void removeLessonTest(LessonTest test);

	/**
	 * Creates a new test for the lesson
	 * @param idLesson host of the test
	 * @param multipleChoice indicates if the test's question are multiple choice
	 * @param numAnswers Number of possible answers per question
	 * @return Newly created test's id if success, -1 otherwise
	 */
	public int createLessonTest(int idLesson, boolean multipleChoice, int numAnswers);

}
