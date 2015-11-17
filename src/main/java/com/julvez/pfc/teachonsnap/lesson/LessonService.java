package com.julvez.pfc.teachonsnap.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;

public interface LessonService {
	
	public Lesson getLessonFromURI(String lessonURI);

	public Lesson getLesson(int idLesson);

	public List<Lesson> getLastLessons(int firstResult);

	public List<Lesson> getLessonsFromAuthor(String author, int firstResult);

	public Lesson createLesson(Lesson newLesson);
	
	public Lesson saveLessonText(Lesson lesson, String newText);

	public boolean notifyLessonCreated(Lesson lesson);

	public boolean notifyLessonModified(Lesson lesson);

	public Lesson saveLessonLanguage(Lesson lesson, Language language);

	public Lesson saveLessonTitle(Lesson lesson, String title);

	public void publish(Lesson lesson);

	public void unpublish(Lesson lesson);

	public List<Lesson> getLessonDraftsFromUser(String userId, int firstResult);
	
}