package com.julvez.pfc.teachonsnap.service.lesson.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public interface LessonRepository {

	public Lesson getLesson(int idLesson);
	
	public int getLessonIDFromURI(String lessonURI);

	public List<Integer> getLinkedLessonIDs(int idLesson);

	public List<Integer> getLastLessonIDs(int firstResult);

	public List<Integer> getLessonIDsFromAuthor(String author, int firstResult);

	public int createLesson(Lesson newLesson);

	public void saveLessonText(int idLesson, String newText);

}
