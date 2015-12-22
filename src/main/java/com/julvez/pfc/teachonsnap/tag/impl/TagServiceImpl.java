package com.julvez.pfc.teachonsnap.tag.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.tag.model.TagMessageKey;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryFactory;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class TagServiceImpl implements TagService {

	private LessonService lessonService = LessonServiceFactory.getService();
	private UserService userService = UserServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	private StatsService statsService = StatsServiceFactory.getService();
	private URLService urlService = URLServiceFactory.getService();
	
	private TagRepository tagRepository = TagRepositoryFactory.getRepository();
	
	private StringManager stringManager = StringManagerFactory.getManager();

	@Override
	public Tag getTag(int idTag) {
		Tag tag = null;
	
		if(idTag>0){
			tag = tagRepository.getTag(idTag);
			String md5 = stringManager.generateMD5(tag.getTag());
			tag.setMD5(md5);
			
			tag.setURLs(urlService.getTagURL());
		}
		return tag;
	}
	
	@Override
	public List<Lesson> getLessonsFromTag(Tag tag,int firstResult) {
		
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		if(tag != null){
			List<Integer> ids = tagRepository.getLessonIDsFromTag(tag.getTag(),firstResult);
			
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
	public List<CloudTag> getTagUseCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Object[]> result= tagRepository.getTagUseCloudTags();
				
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
					
					if(!lesson.isDraft()){
						notifyLessonTagged(lesson, tagID);
					}
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

	@Override
	public boolean saveLessonTags(Lesson lesson, List<Tag> oldTags, List<String> newTags) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
		
			ArrayList<Integer> removeTagIDs = getTagIDsToRemove(oldTags, newTags);
		
			if(removeTagIDs.size()>0){
				removeLessonTags(lesson, removeTagIDs);
				modified = true;
			}
			
			if(newTags != null && newTags.size() > 0){
				
				for(Tag tag:oldTags){
					if(newTags.contains(tag.getTag())){
						newTags.remove(tag.getTag());
					}
				}
				
				if(newTags.size() > 0){
					addLessonTags(lesson, newTags);
					modified = true;
				}
			}
		}
		
		return modified;
	}
	
	@Override
	public void removeLessonTags(Lesson lesson, ArrayList<Integer> removeTagIDs) {
		if(lesson != null && lesson.getId()>0 && removeTagIDs != null && removeTagIDs.size() > 0){
			tagRepository.removeLessonTags(lesson.getId(), removeTagIDs);
		}		
	}

	private ArrayList<Integer> getTagIDsToRemove(List<Tag> oldTags, List<String> newTags){
		ArrayList<Integer> removeTagIDs = new ArrayList<Integer>();

		if(newTags == null){
			newTags = new ArrayList<String>();
		}
		for(Tag tag:oldTags){
			if(!newTags.contains(tag.getTag())){
				removeTagIDs.add(tag.getId());
			}
		}
		
		return removeTagIDs;
	}

	@Override
	public List<Tag> getTags(int firstResult) {
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = tagRepository.getTags(firstResult);
		
		for(int id:ids){
			tags.add(getTag(id));
		}
		
		return tags;
	}

	@Override
	public List<TagFollowed> getTagsFollowed(List<Tag> tags, List<Tag> tagFollowings) {
		List<TagFollowed> retList = new ArrayList<TagFollowed>();
		
		if(tags != null){
			
			for(Tag tag:tags){
				TagFollowed followed = new TagFollowed(tag);
				
				if(tagFollowings != null && tagFollowings.contains(tag)){
					followed.setFollowed(true);
				}				
				retList.add(followed);				
			}			
		}
		
		return retList;
	}

	@Override
	public List<Tag> searchTag(String searchQuery, int firstResult) {
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = tagRepository.searchTag(searchQuery, firstResult);
		
		for(int id:ids){
			tags.add(getTag(id));
		}
		
		return tags;
	}

	@Override
	public boolean notifyLessonTagged(Lesson lesson, int idTag) {		
		boolean success = false;
		
		if(lesson != null && !lesson.isDraft() && idTag > 0){
			Tag tag = getTag(idTag);
			
			List<User> followers = userService.getTagFollowers(tag);
			
			if(followers != null){
				String url = lesson.getURL();

				for(User follower:followers){
					String subject = textService.getLocalizedText(follower.getLanguage(),TagMessageKey.LESSON_TAGGED_SUBJECT, lesson.getTitle(), tag.getTag());
					String message = textService.getLocalizedText(follower.getLanguage(),TagMessageKey.LESSON_TAGGED_MESSAGE, url, tag.getTag(), lesson.getTitle());
					notifyService.info(follower, subject, message);					
				}
			}
		
		}
		
		return success;		
	}

	@Override
	public Tag getTag(String tag) {
		Tag ret = null;
		
		if(!stringManager.isEmpty(tag)){
			int tagId = tagRepository.getTagID(tag);
			ret = getTag(tagId);
		}
		return ret;
	}

	@Override
	public List<CloudTag> getTagSearchCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Integer> ids = tagRepository.getTagSearchCloudTags();
				
		int max=0;
		int min=0;
		for(int id:ids){
			Tag tag = getTag(id);			
			short visits = (short)statsService.getTagViewsCount(tag);
			if(visits > max) max = visits;
			if(min == 0 || visits < min) min = visits;
			
			cloudTags.add(new CloudTag(tag, visits));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}
	
	@Override
	public List<CloudTag> getLessonViewCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Integer> ids = tagRepository.getLessonViewCloudTags();
		
		int max=0;
		int min=0;
		for(int id:ids){
			Lesson lesson = lessonService.getLesson(id);			
			short visits = (short)statsService.getLessonViewsCount(lesson);
			if(visits > max) max = visits;
			if(min == 0 || visits < min) min = visits;
			
			cloudTags.add(new CloudTag(lesson, visits));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}

}
