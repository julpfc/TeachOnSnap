package com.julvez.pfc.teachonsnap.tag;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.tag.model.CloudTag;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagFollowed;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;

/** Provides the functionality to work with tags. */
public interface TagService {

	/**
	 * Returns lessons tagged by the specified tag. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param tag lesson's are tagged with.
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons for this page
	 */
	public List<Lesson> getLessonsFromTag(Tag tag, int firstResult);
	
	/**
	 * Returns lesson's tags.
	 * @param idLesson Lesson.
	 * @return List of tags for this lesson.
	 */
	public List<Tag> getLessonTags(int idLesson);
	
	/**
	 * Returns the corresponding Tag object from the specified id.
	 * @param idTag Tag's id
	 * @return Tag object from id if it's a valid id, null otherwise
	 */
	public Tag getTag(int idTag);
	
	/**
	 * Returns the corresponding Tag object from the specified tag's text.
	 * @param tag Tag's text
	 * @return Tag object from tag's text if it's an existing tag, null otherwise
	 */
	public Tag getTag(String tag);
	
	/**
	 * Returns the cloud of tags for: Most used tags.
	 * @return list with the cloud's tag to create a cloud of tags
	 */
	public List<CloudTag> getTagUseCloudTags();
	
	/**
	 * Returns the cloud of tags for: Most searched tags.
	 * @return list with the cloud's tag to create a cloud of tags
	 */
	public List<CloudTag> getTagSearchCloudTags();
	
	/**
	 * Returns the cloud of tags for: Most prolific authors.
	 * @return list with the cloud's tag to create a cloud of tags
	 */
	public List<CloudTag> getAuthorCloudTags();
	
	/**
	 * Returns the cloud of tags for: Most viewed lessons.
	 * @return list with the cloud's tag to create a cloud of tags
	 */
	public List<CloudTag> getLessonViewCloudTags();
	
	/**
	 * Adds the list of tags's texts to this lesson
	 * @param lesson to add tags to
	 * @param tags List of tags's texts to be added
	 */
	public void addLessonTags(Lesson lesson, List<String> tags);

	/**
	 * Compares the old and new list of tags, to remove (no longer needed),
	 * and to add the new tags required from/to the lesson.
	 * @param lesson to add/remove tags to/from
	 * @param oldTags current tags in the lesson
	 * @param newTags new tags for the lesson to be saved
	 * @return true if the lesson is modified to add or remove any tag
	 */
	public boolean saveLessonTags(Lesson lesson, List<Tag> oldTags,	List<String> newTags);

	/**
	 * Removes the list of tags from this lesson
	 * @param lesson to remove tags from
	 * @param tagIDs List of ids to be removed
	 */
	public void removeLessonTags(Lesson lesson, ArrayList<Integer> tagIDs);

	/**
	 * Returns all tags. If the tags number is greater than the 
	 * maximum number of tags allowed for a cloud {@link TagPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first tag the pagination should start from.
	 * @return List of tags for this page
	 */
	public List<Tag> getTags(int firstResult);

	/**
	 * Returns the list of TagFollowed, with the ones followed marked 
	 * (it uses the tagFollowings list to mark the followed tags).
	 * @param tags list of tags to be marked
	 * @param tagFollowings tags followed
	 * @return tags list, with the folloewed tags marked
	 */
	public List<TagFollowed> getTagsFollowed(List<Tag> tags, List<Tag> tagFollowings);

	/**
	 * Returns a list of Tag found by the searching tags by 
	 * matching the searchQuery with the tags's text. If the results 
	 * number is greater than the maximum number of tags allowed for 
	 * a cloud {@link TagPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the tags texts
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of Tag matching the query
	 */
	public List<Tag> searchTag(String searchQuery, int firstResult);

	/**
	 * Notifies a lesson has been tagged with a tag to the tag's followers.
	 * @param lesson tagged
	 * @param idTag Lesson's new tag
	 * @return true if the notification is successfully sent.
	 */
	public boolean notifyLessonTagged(Lesson lesson, int idTag);
}
