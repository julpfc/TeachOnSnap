package com.julvez.pfc.teachonsnap.service.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;

public interface LessonService {
	
	public List<Lesson> getLessonsFromTag(String tag, int firstResult);

	public Lesson getLessonFromURI(String lessonURI);

	public List<Tag> getLessonTags(int idLesson);
	public List<Lesson> getLinkedLessons(int idLesson);
	public List<Link> getMoreInfoLinks(int idLesson);
	public List<Link> getSourceLinks(int idLesson);

	public Lesson getLesson(int idLesson);

	public List<Lesson> getLastLessons(int firstResult);

	public List<CloudTag> getCloudTags();

	public List<Lesson> getLessonsFromAuthor(String author, int firstResult);

	public LessonTest getLessonTest(int idLessonTest);

	public List<CloudTag> getAuthorCloudTags();

	public Lesson createLesson(Lesson newLesson);
	
	public Lesson saveLessonText(Lesson lesson,String newText);

	public Lesson addLessonTags(Lesson lesson, List<String> tags);

	public Lesson addLessonSources(Lesson lesson, List<String> sources);

	public int createLink(String URL);

	public Lesson addLessonMoreInfo(Lesson lesson, List<String> moreInfos);

		
	//TODO un removeLessonTags para el editor de lecciones, le dices cuales añades y cuales quitas
	
	
}
