package com.julvez.pfc.teachonsnap.tag;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;

public interface TagService {

	public List<Lesson> getLessonsFromTag(Tag tag, int firstResult);
	
	public List<Tag> getLessonTags(int idLesson);
	
	public Tag getTag(int idTag);
	public Tag getTag(String tag);
	
	public List<CloudTag> getTagUseCloudTags();
	public List<CloudTag> getTagSearchCloudTags();
	
	public List<CloudTag> getAuthorCloudTags();
	
	public void addLessonTags(Lesson lesson, List<String> tags);

	public boolean saveLessonTags(Lesson lesson, List<Tag> oldTags,	List<String> newTags);

	public void removeLessonTags(Lesson lesson, ArrayList<Integer> tagIDs);

	public List<Tag> getTags(int firstResult);

	public List<TagFollowed> getTagsFollowed(List<Tag> tags, List<Tag> tagFollowings);

	public List<Tag> searchTag(String searchQuery, int firstResult);

	public boolean notifyLessonTagged(Lesson lesson, int idTag);


}
