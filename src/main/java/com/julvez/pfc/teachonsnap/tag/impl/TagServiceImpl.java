package com.julvez.pfc.teachonsnap.tag.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

public class TagServiceImpl implements TagService {

	private LessonService lessonService = LessonServiceFactory.getService();
	private UserService userService = UserServiceFactory.getService();
	
	private TagRepository tagRepository = TagRepositoryFactory.getRepository();
	
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	public Tag getTag(int idTag) {
		Tag tag = null;
	
		if(idTag>0){
			tag = tagRepository.getTag(idTag);
			String md5 = stringManager.generateMD5(tag.getTag());
			tag.setMD5(md5);
		}
		return tag;
	}
	
	@Override
	public List<Lesson> getLessonsFromTag(String tag,int firstResult) {
		
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		if(tag != null){
			List<Integer> ids = tagRepository.getLessonIDsFromTag(tag,firstResult);
			
			for(int id:ids){
				lessons.add(lessonService.getLesson(id));
			}
		}
		
		return lessons;
	}

	@Override
	public List<Tag> getLessonTags(int idLesson) {
		
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = tagRepository.getLessonTagIDs(idLesson);
		
		for(int id:ids){
			tags.add(getTag(id));
		}		
		return tags;
	}
	
	
	@Override
	public List<CloudTag> getCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Object[]> result= tagRepository.getCloudTags();
				
		int max=0;
		int min=0;
		for(Object ids[]:result){
			int aux= ((BigInteger)ids[1]).intValue();
			if(aux>max) max=aux;
			if(min==0 || aux<min) min=aux;
			
			cloudTags.add(new CloudTag(getTag(((Integer)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}
	
	private List<CloudTag> getCloudTagListNormalized(List<CloudTag> cloudTags,int max, int min){
		final int weightLevel = 6;
		// Normalizamos los pesos
		for(CloudTag cloudTag:cloudTags){
			short weight = (short) (Math.floor(((double)((weightLevel-1) * (cloudTag.getWeight() - min)))/(double)(max-min)) + 1);
			cloudTag.setWeight(weight);
		}				
		return cloudTags;
	}
	
	@Override
	public List<CloudTag> getAuthorCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Object[]> result= tagRepository.getAuthorCloudTags();
				
		int max=0;
		int min=0;
		for(Object ids[]:result){
			int aux= ((BigInteger)ids[1]).intValue();
			if(aux>max) max=aux;
			if(min==0 || aux<min) min=aux;
			
			cloudTags.add(new CloudTag(userService.getUser(((Short)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}

	@Override
	public void addLessonTags(Lesson lesson, List<String> tags) {
		ArrayList<Integer> tagIDs = new ArrayList<Integer>();
		
		if(lesson!=null && lesson.getId()>0 && tags!=null){

			for(String tag:tags){
				int tagID = 0;
				tagID = tagRepository.getTagID(tag.toLowerCase());
				if(tagID>0){
					tagIDs.add(tagID);
				}
				else{
					tagID = tagRepository.createTag(tag.toLowerCase());	
					if(tagID>0){
						tagIDs.add(tagID);
					}
				}
			}
			if(tagIDs.size()>0){
				tagRepository.addLessonTags(lesson.getId(), tagIDs);
			}
		}

	}

}
