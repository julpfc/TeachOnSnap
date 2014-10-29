package com.julvez.pfc.teachonsnap.repository.lesson;

import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;

public interface LessonRepository {

	public Lesson getLesson(int id);
	
	public List<Integer> getLessonIDsFromTag(String tag);
	
}
