package com.julvez.pfc.teachonsnap.tag.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.tag.model.TagMessageKey;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the TagService interface, uses an internal {@link TagRepository} 
 * to access/modify the tags related data.
 */
public class TagServiceImpl implements TagService {

	/** Repository than provides data access/modification */
	private TagRepository tagRepository;

	/** Provides the functionality to work with lessons */
	private LessonService lessonService;
	
	/** Provides the functionality to work with application's users */
	private UserService userService;
	
	/** Provides the functionality to work with localized texts. */
	private TextService textService;
	
	/** Provides the functionality to work with notifications */
	private NotifyService notifyService;
	
	/** Provides the functionality to work with stats */
	private StatsService statsService;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;

	
	/**
	 * Constructor requires all parameters not to be null
	 * @param tagRepository Repository than provides data access/modification
	 * @param lessonService Provides the functionality to work with lessons
	 * @param userService Provides the functionality to work with application's users
	 * @param textService Provides the functionality to work with localized texts
	 * @param notifyService Provides the functionality to work with notifications
	 * @param statsService Provides the functionality to work with stats
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public TagServiceImpl(TagRepository tagRepository,
			LessonService lessonService, UserService userService,
			TextService textService, NotifyService notifyService,
			StatsService statsService, URLService urlService,
			StringManager stringManager) {
		
		if(tagRepository == null || lessonService == null || urlService == null
				|| notifyService == null || textService == null || userService == null 
				|| statsService == null || stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.tagRepository = tagRepository;
		this.lessonService = lessonService;
		this.userService = userService;
		this.textService = textService;
		this.notifyService = notifyService;
		this.statsService = statsService;
		this.urlService = urlService;
		this.stringManager = stringManager;
	}

	@Override
	public Tag getTag(int idTag) {
		Tag tag = null;
	
		if(idTag>0){
			//get tag from repo
			tag = tagRepository.getTag(idTag);
			//generate MD5 hash
			String md5 = stringManager.generateMD5(tag.getTag());
			//set hash
			tag.setMD5(md5);
			//set URL
			tag.setURLs(urlService.getTagURL());
		}
		return tag;
	}
	
	@Override
	public List<Lesson> getLessonsFromTag(Tag tag, int firstResult) {		
		List<Lesson> lessons = Collections.emptyList();
		
		if(tag != null && firstResult >= 0){
			//get lesson ids from tag
			List<Integer> ids = tagRepository.getLessonIDsFromTag(tag.getTag(), firstResult);
			
			if(ids != null){
				lessons = new ArrayList<Lesson>();				
				//get Lesson from id and fill list
				for(int id:ids){
					lessons.add(lessonService.getLesson(id));
				}
			}
		}
		
		return lessons;
	}

	@Override
	public List<Tag> getLessonTags(int idLesson) {		
		List<Tag> tags = Collections.emptyList();
		
		//get tags ids from lesson
		List<Integer> ids = tagRepository.getLessonTagIDs(idLesson);
		
		if(ids != null){
			tags = new ArrayList<Tag>();
			//get Tags from id and fill list
			for(int id:ids){
				tags.add(getTag(id));
			}		
		}
		return tags;
	}
	
	
	@Override
	public List<CloudTag> getTagUseCloudTags() {
		List<CloudTag> cloudTags = Collections.emptyList();
		
		//get tags from repository
		List<Object[]> result= tagRepository.getTagUseCloudTags();
				
		int max=0;
		int min=0;
		
		if(result != null){
			cloudTags = new ArrayList<CloudTag>();
			//extract ids and get tags from ids to create a CloudTag
			for(Object ids[]:result){
				int aux= ((BigInteger)ids[1]).intValue();
				if(aux>max) max=aux;
				if(min==0 || aux<min) min=aux;
				
				cloudTags.add(new CloudTag(getTag(((Integer)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
			}
		}
		//calculate tags's weights and return
		return getCloudTagListNormalized(cloudTags,max,min);
	}
	

	@Override
	public List<CloudTag> getAuthorCloudTags() {
		List<CloudTag> cloudTags = Collections.emptyList();
		
		//get tags from repository
		List<Object[]> result= tagRepository.getAuthorCloudTags();
				
		int max=0;
		int min=0;
		
		if(result != null){
			cloudTags = new ArrayList<CloudTag>();
			
			//extract ids and get authors from ids to create a CloudTag
			for(Object ids[]:result){
				int aux= ((BigInteger)ids[1]).intValue();
				if(aux>max) max=aux;
				if(min==0 || aux<min) min=aux;
				
				cloudTags.add(new CloudTag(userService.getUser(((Short)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
			}
		}
		//calculate tags's weights and return
		return getCloudTagListNormalized(cloudTags,max,min);
	}

	@Override
	public void addLessonTags(Lesson lesson, List<String> tags) {
		
		if(lesson!=null && lesson.getId()>0 && tags!=null){
			ArrayList<Integer> tagIDs = new ArrayList<Integer>();
			
			//fro each tag string
			for(String tag:tags){
				int tagID = 0;
				
				//check if the tag already exists
				tagID = tagRepository.getTagID(tag.toLowerCase());
				
				//if exists
				if(tagID>0){
					//add to the list of ids to be added
					tagIDs.add(tagID);
					
					//notify tag's followers
					if(!lesson.isDraft()){
						notifyLessonTagged(lesson, tagID);
					}
				}
				else{
					//if not exists, create tag and add to the list of ids to be added
					tagID = tagRepository.createTag(tag.toLowerCase());	
					if(tagID>0){
						tagIDs.add(tagID);
					}
				}
			}
			//add tags to the lesson from list
			if(tagIDs.size()>0){
				tagRepository.addLessonTags(lesson.getId(), tagIDs);
			}
		}

	}

	@Override
	public boolean saveLessonTags(Lesson lesson, List<Tag> oldTags, List<String> newTags) {
		boolean modified = false;
		
		if(lesson!=null && lesson.getId()>0){
		
			//compare lists of tags and get tags to remove
			ArrayList<Integer> removeTagIDs = getTagIDsToRemove(oldTags, newTags);
		
			//remove tags if any
			if(removeTagIDs.size()>0){
				removeLessonTags(lesson, removeTagIDs);
				modified = true;
			}
			
			//if there are new tags
			if(newTags != null && newTags.size() > 0){
				
				//ignore already added tags
				for(Tag tag:oldTags){
					if(newTags.contains(tag.getTag())){
						newTags.remove(tag.getTag());
					}
				}
				
				//add the new tags
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
			//remove tags
			tagRepository.removeLessonTags(lesson.getId(), removeTagIDs);
		}		
	}

	
	@Override
	public List<Tag> getTags(int firstResult) {
		List<Tag> tags = Collections.emptyList();
		
		if(firstResult >= 0){
			//get all tags ids from repo. Pagination
			List<Integer> ids = tagRepository.getTags(firstResult);
			
			if(ids != null){
				tags = new ArrayList<Tag>();
				//get tags from ids 
				for(int id:ids){
					tags.add(getTag(id));
				}
			}
		}
		return tags;
	}

	@Override
	public List<TagFollowed> getTagsFollowed(List<Tag> tags, List<Tag> tagFollowings) {
		List<TagFollowed> retList = Collections.emptyList();
		
		if(tags != null){
			retList = new ArrayList<TagFollowed>();
			//for each tag on the list
			for(Tag tag:tags){
				//Create a followable tag object
				TagFollowed followed = new TagFollowed(tag);
				
				//mark the tag if it's followed
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
		List<Tag> tags = Collections.emptyList();
		
		if(!stringManager.isEmpty(searchQuery) && firstResult >= 0){
			//get tags ids result from query
			List<Integer> ids = tagRepository.searchTag(searchQuery, firstResult);
			
			if(ids != null){
				tags = new ArrayList<Tag>();
				//get tags from ids
				for(int id:ids){
					tags.add(getTag(id));
				}
			}
		}
		return tags;
	}

	@Override
	public boolean notifyLessonTagged(Lesson lesson, int idTag) {		
		boolean success = false;
		
		//if it's a valid lesson and is not a draft
		if(lesson != null && !lesson.isDraft() && idTag > 0){
			//get tag from id
			Tag tag = getTag(idTag);
			
			//get tag followers
			List<User> followers = userService.getTagFollowers(tag);
			
			if(followers != null){
				//get tag's URL
				String url = lesson.getURL();

				//Notify each follower
				for(User follower:followers){
					String subject = textService.getLocalizedText(follower.getLanguage(),TagMessageKey.LESSON_TAGGED_SUBJECT, lesson.getTitle(), tag.getTag());
					String message = textService.getLocalizedText(follower.getLanguage(),TagMessageKey.LESSON_TAGGED_MESSAGE, url, tag.getTag(), lesson.getTitle());
					success = notifyService.info(follower, subject, message);					
				}
			}
		
		}
		
		return success;		
	}

	@Override
	public Tag getTag(String tag) {
		Tag ret = null;
		
		if(!stringManager.isEmpty(tag)){
			//get tag id from tag's text
			int tagId = tagRepository.getTagID(tag);
			//get Tag from id
			ret = getTag(tagId);
		}
		return ret;
	}

	@Override
	public List<CloudTag> getTagSearchCloudTags() {
		List<CloudTag> cloudTags = Collections.emptyList();
		
		//get tags ids from repository
		List<Integer> ids = tagRepository.getTagSearchCloudTags();
		
		int max=0;
		int min=0;
		if(ids != null){
			cloudTags = new ArrayList<CloudTag>();
			
			//for each id, get tag from id and get views count
			for(int id:ids){
				Tag tag = getTag(id);			
				short visits = (short)statsService.getTagViewsCount(tag);
				if(visits > max) max = visits;
				if(min == 0 || visits < min) min = visits;
				
				cloudTags.add(new CloudTag(tag, visits));
			}
		}
		//calculate tags's weights and return
		return getCloudTagListNormalized(cloudTags,max,min);
	}
	
	@Override
	public List<CloudTag> getLessonViewCloudTags() {
		List<CloudTag> cloudTags = Collections.emptyList();
		
		//get tags ids from repository
		List<Integer> ids = tagRepository.getLessonViewCloudTags();
		
		int max=0;
		int min=0;
		if(ids != null){
			cloudTags = new ArrayList<CloudTag>();
			//for each id, get lesson from id and get views count
			for(int id:ids){
				Lesson lesson = lessonService.getLesson(id);			
				short visits = (short)statsService.getLessonViewsCount(lesson);
				if(visits > max) max = visits;
				if(min == 0 || visits < min) min = visits;
				
				cloudTags.add(new CloudTag(lesson, visits));
			}
		}
		//calculate tags's weights and return
		return getCloudTagListNormalized(cloudTags,max,min);
	}

	/**
	 * Normalize tags weights
	 * @param cloudTags list of tags
	 * @param max weight
	 * @param min weight
	 * @return List of tags from a cloud of tags, with normalized weights
	 */
	private List<CloudTag> getCloudTagListNormalized(List<CloudTag> cloudTags,int max, int min){
		final int weightLevel = 6;
		// Normalize tags weights between 1 and weightLevel
		for(CloudTag cloudTag:cloudTags){
			short weight = (short) (Math.floor(((double)((weightLevel-1) * (cloudTag.getWeight() - min)))/(double)(max-min)) + 1);
			cloudTag.setWeight(weight);
		}				
		return cloudTags;
	}
	
	/**
	 * Checks the old and new lists of tags and returns the list of tags to be removed
	 * @param oldTags List of current tags
	 * @param newTags List of new tags to be added
	 * @return list of tags to be removed from current list
	 */
	private ArrayList<Integer> getTagIDsToRemove(List<Tag> oldTags, List<String> newTags){
		ArrayList<Integer> removeTagIDs = new ArrayList<Integer>();

		if(newTags == null){
			newTags = new ArrayList<String>();
		}
		//remove tag if it's not present on the new list
		for(Tag tag:oldTags){
			if(!newTags.contains(tag.getTag())){
				removeTagIDs.add(tag.getId());
			}
		}
		
		return removeTagIDs;
	}
}
