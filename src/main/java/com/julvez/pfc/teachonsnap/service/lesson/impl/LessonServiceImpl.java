package com.julvez.pfc.teachonsnap.service.lesson.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;

public class LessonServiceImpl implements LessonService{

	private LessonRepository lessonRepository = LessonRepositoryFactory.getRepository();
	
	@Override
	public List<Lesson> getLessonsFromTag(String tag) {
		
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLessonIDsFromTag(tag);
		
		for(int id:ids){
			lessons.add(lessonRepository.getLesson(id));
		}
		
		return lessons;
	}

}
