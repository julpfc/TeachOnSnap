package com.julvez.pfc.teachonsnap.service.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public interface LessonService {
	
	public Lesson getLessonFromURI(String lessonURI);

	public List<Lesson> getLinkedLessons(int idLesson);

	public Lesson getLesson(int idLesson);

	public List<Lesson> getLastLessons(int firstResult);

	public List<Lesson> getLessonsFromAuthor(String author, int firstResult);

	public Lesson createLesson(Lesson newLesson);
	
	public Lesson saveLessonText(Lesson lesson,String newText);
	
}
