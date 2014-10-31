package com.julvez.pfc.teachonsnap.service.lesson.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
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

	@Override
	public Lesson getLessonFromURI(String lessonURI) {
		Lesson lesson = null;

		int id = lessonRepository.getLessonIDFromURI(lessonURI);
		
		lesson= lessonRepository.getLesson(id);
		
		return lesson;
	}

	@Override
	public List<Tag> getLessonTags(int idLesson) {
		
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = lessonRepository.getLessonTagIDs(idLesson);
		
		for(int id:ids){
			tags.add(lessonRepository.getTag(id));
		}		
		return tags;
	}

	@Override
	public List<Lesson> getLinkedLessons(int idLesson) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLinkedLessonIDs(idLesson);
		
		for(int id:ids){
			lessons.add(lessonRepository.getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public List<Link> getMoreInfoLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = lessonRepository.getMoreInfoLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(lessonRepository.getLink(id));
			}
		
		return links;	
	}

	@Override
	public List<Link> getSourceLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = lessonRepository.getSourceLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(lessonRepository.getLink(id));
			}
		
		return links;
	}

}
