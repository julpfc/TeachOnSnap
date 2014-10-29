package com.julvez.pfc.teachonsnap.service.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public interface LessonService {
	
	public List<Lesson> getLessonsFromTag(String tag);
	
	
}
