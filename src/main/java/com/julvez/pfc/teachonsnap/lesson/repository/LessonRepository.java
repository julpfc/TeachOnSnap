package com.julvez.pfc.teachonsnap.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;

public interface LessonRepository {

	public Lesson getLesson(int idLesson);
	
	public int getLessonIDFromURI(String lessonURI);

	public List<Integer> getLastLessonIDs(int firstResult);

	public List<Integer> getLessonIDsFromAuthor(String author, int firstResult);

	public int createLesson(Lesson newLesson);

	public void saveLessonText(int idLesson, String newText);

	public void saveLessonLanguage(int idLesson, short idLanguage);

	public boolean saveLessonTitle(Lesson lesson, String title, String URIName);

	public void removeLessonText(int idLesson);

	public void publish(int idLesson);

	public void unpublish(int idLesson);

	public List<Integer> getDraftLessonIDsFromUser(int idUser, int firstResult);

}
