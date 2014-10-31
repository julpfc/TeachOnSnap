package com.julvez.pfc.teachonsnap.service.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;

public interface LessonService {
	
	public List<Lesson> getLessonsFromTag(String tag);

	public Lesson getLessonFromURI(String lessonURI);

	public List<Tag> getLessonTags(int idLesson);
	public List<Lesson> getLinkedLessons(int idLesson);
	public List<Link> getMoreInfoLinks(int idLesson);
	public List<Link> getSourceLinks(int idLesson);
	
	
}
