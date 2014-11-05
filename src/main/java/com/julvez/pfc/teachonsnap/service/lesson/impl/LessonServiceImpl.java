package com.julvez.pfc.teachonsnap.service.lesson.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;
import com.julvez.pfc.teachonsnap.repository.user.UserRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;

public class LessonServiceImpl implements LessonService{

	private LessonRepository lessonRepository = LessonRepositoryFactory.getRepository();
	private UserRepository userRepository = UserRepositoryFactory.getRepository();
	private LangRepository langRepository = LangRepositoryFactory.getRepository();
	
	
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

	@Override
	public User getAuthor(Lesson lesson) {
		User author = null;
		
		author = userRepository.getUser(lesson.getIdUser());
		return author;
	}

	@Override
	public Language getLanguage(Lesson lesson) {
		Language lang = null;
		lang = langRepository.getLanguage(lesson.getIdLanguage());
		return lang;
	}

	@Override
	public List<Lesson> getLastLessons() {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLastLessonIDs();
		
		for(int id:ids){
			lessons.add(lessonRepository.getLesson(id));
		}
		
		return lessons;
	}

}
