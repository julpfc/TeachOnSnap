package com.julvez.pfc.teachonsnap.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;

/**
 * Repository to access/modify data related to lessons.
 * <p>
 * To be used only by the {@link LessonService}'s implementation
 */
public interface LessonRepository {

	/**
	 * Returns lesson from lesson's id
	 * @param idLesson lesson's id
	 * @return lesson object if it's a valid id, null otherwise
	 */
	public Lesson getLesson(int idLesson);
	
	/**
	 * Returns the lesson's id corresponding to the URI string specified
	 * @param lessonURI URI identifying a lesson
	 * @return lesson id if there is a match with the lesson URI, -1 otherwise
	 */
	public int getLessonIDFromURI(String lessonURI);

	/**
	 * Returns last published lessons ids. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons ids for this page
	 */
	public List<Integer> getLastLessonIDs(int firstResult);

	/**
	 * Returns author's lessons ids. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param author Author's URI
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons ids for this page
	 */
	public List<Integer> getLessonIDsFromAuthor(String author, int firstResult);

	/**
	 * Creates a new lesson
	 * @param newLesson to be created
	 * @return Newly created lesson id if success, -1 otherwise
	 */
	public int createLesson(Lesson newLesson);

	/**
	 * Updates the lesson's text
	 * @param idLesson Lesson
	 * @param newText new text for the lesson
	 */
	public void saveLessonText(int idLesson, String newText);

	/**
	 * Updates the lesson's language
	 * @param idLesson Lesson
	 * @param idLanguage new language for the lesson
	 */
	public void saveLessonLanguage(int idLesson, short idLanguage);

	/**
	 * Updates the lesson's title
	 * @param lesson Lesson
	 * @param title new title for the lesson
	 * @param URIName new title without special characters
	 * @return true if success
	 */
	public boolean saveLessonTitle(Lesson lesson, String title, String URIName);

	/**
	 * Sets the lesson optional text to blank
	 * @param idLesson Lesson's id
	 */
	public void removeLessonText(int idLesson);

	/**
	 * Publishes the lesson, so will be visible for other users.
	 * @param idLesson to be published
	 */
	public void publish(int idLesson);

	/**
	 * Unpublishes the lesson, son won't be visible for others.
	 * @param idLesson to be unpublished
	 */
	public void unpublish(int idLesson);

	/**
	 * Returns user's drafts ids. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param idUser Author
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of drafts ids for this page
	 */
	public List<Integer> getDraftLessonIDsFromUser(int idUser, int firstResult);

}
