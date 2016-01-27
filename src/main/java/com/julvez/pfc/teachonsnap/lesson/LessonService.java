package com.julvez.pfc.teachonsnap.lesson;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to work with lessons.
 */
public interface LessonService {
	
	/**
	 * Returns the lesson corresponding to the URI string specified
	 * @param lessonURI URI identifying a lesson
	 * @return lesson if there is a match with the lesson URI, null otherwise
	 */
	public Lesson getLessonFromURI(String lessonURI);

	/**
	 * Returns lesson from lesson's id
	 * @param idLesson lesson's id
	 * @return lesson object if it's a valid id, null otherwise
	 */
	public Lesson getLesson(int idLesson);

	/**
	 * Returns last published lessons. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons for this page
	 */
	public List<Lesson> getLastLessons(int firstResult);

	/**
	 * Returns author's lessons. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param author Author's URI
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons for this page
	 */
	public List<Lesson> getLessonsFromAuthor(String author, int firstResult);

	/**
	 * Creates a new lesson
	 * @param newLesson to be created
	 * @return Newly created lesson if success, null otherwise
	 */
	public Lesson createLesson(Lesson newLesson);
	
	/**
	 * Updates the lesson's text
	 * @param lesson Lesson
	 * @param newText new text for the lesson
	 * @return modified lesson if success, null otherwise
	 */
	public Lesson saveLessonText(Lesson lesson, String newText);

	/**
	 * Notifies to the author than a new lesson was 
	 * successfully created.
	 * @param lesson new lesson
	 * @return true if notification is successfully sent
	 */
	public boolean notifyLessonCreated(Lesson lesson);

	/**
	 * Notifies to lesson's followers than it was modified.
	 * @param lesson modified 
	 * @return true if notification is successfully sent
	 */	public boolean notifyLessonModified(Lesson lesson);
	
	 /**
	 * Notifies to author's followers than a new lesson was published.
	 * @param lesson published 
	 * @return true if notification is successfully sent
	 */
	 public boolean notifyLessonPublished(Lesson lesson);

	/**
	 * Updates the lesson's language
	 * @param lesson Lesson
	 * @param language new language for the lesson
	 * @return modified lesson if success, null otherwise
	 */
	public Lesson saveLessonLanguage(Lesson lesson, Language language);

	/**
	 * Updates the lesson's title
	 * @param lesson Lesson
	 * @param title new title for the lesson
	 * @return modified lesson if success, null otherwise
	 */
	public Lesson saveLessonTitle(Lesson lesson, String title);

	/**
	 * Publishes the lesson, so will be visible for other users.
	 * Author's followers will be notified.
	 * @param lesson to be published
	 */
	public void publish(Lesson lesson);
	
	/**
	 * Publishes the lesson without notifications.
	 * @param lesson to be publish
	 */
	public void republish(Lesson lesson);	

	/**
	 * Unpublishes the lesson, son won't be visible for others.
	 * @param lesson to be unpublished
	 */
	public void unpublish(Lesson lesson);

	/**
	 * Returns user's drafts. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param user Author
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons for this page
	 */
	public List<Lesson> getLessonDraftsFromUser(User user, int firstResult);

	/**
	 * Extracts a list of lesson from a map of lesson ids.
	 * @param lessonFollowed map of ids
	 * @return List of lessons extracted.
	 */
	public List<Lesson> getLessonsFromIDs(Map<String, String> lessonFollowed);

}
