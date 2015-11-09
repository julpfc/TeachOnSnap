package com.julvez.pfc.teachonsnap.tag;

import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;

public interface TagService {

	public List<Lesson> getLessonsFromTag(String tag, int firstResult);
	
	public List<Tag> getLessonTags(int idLesson);
	
	public Tag getTag(int idTag);
	
	public List<CloudTag> getCloudTags();
	
	public List<CloudTag> getAuthorCloudTags();
	
	public void addLessonTags(Lesson lesson, List<String> tags);
	
	//TODO un removeLessonTags para el editor de lecciones, le dices cuales añades y cuales quitas
}
