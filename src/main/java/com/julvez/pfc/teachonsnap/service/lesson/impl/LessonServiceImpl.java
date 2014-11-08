package com.julvez.pfc.teachonsnap.service.lesson.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
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
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public Lesson getLessonFromURI(String lessonURI) {
		Lesson lesson = null;

		int id = lessonRepository.getLessonIDFromURI(lessonURI);
		
		lesson= getLesson(id);
		
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
			lessons.add(getLesson(id));
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
	public List<Lesson> getLastLessons() {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLastLessonIDs();
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public List<CloudTag> getCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		final int weightLevel = 6;
		
		List<Object[]> result= lessonRepository.getCloudTags();
		int max=0;
		int min=0;
		for(Object ids[]:result){
			int aux= ((BigInteger)ids[1]).intValue();
			if(aux>max) max=aux;
			if(min==0 || aux<min) min=aux;
			
			cloudTags.add(new CloudTag(lessonRepository.getTag(((Integer)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
		}
		
		// Normalizamos los pesos
		for(CloudTag cloudTag:cloudTags){
			short weight = (short) (Math.floor(((double)((weightLevel-1) * (cloudTag.getWeight() - min)))/(double)(max-min)) + 1);
			cloudTag.setWeight(weight);
		}
		
		return cloudTags;
	}

	@Override
	public Lesson getLesson(int idLesson) {
		Lesson lesson = lessonRepository.getLesson(idLesson);
		lesson.setAuthor(userRepository.getUser(lesson.getIdUser()));
		lesson.setLanguage(langRepository.getLanguage(lesson.getIdLanguage()));
		return lesson;
	}

}
