package com.julvez.pfc.teachonsnap.repository.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;

public interface LessonRepository {

	public Lesson getLesson(int idLesson);
	
	public List<Integer> getLessonIDsFromTag(String tag);

	public int getLessonIDFromURI(String lessonURI);

	public List<Integer> getLessonTagIDs(int idLesson);

	public List<Integer> getLinkedLessonIDs(int idLesson);

	public List<Integer> getMoreInfoLinkIDs(int idLesson);

	public List<Integer> getSourceLinkIDs(int idLesson);

	public Tag getTag(int idTag);

	public Link getLink(int idLink);
	
}
