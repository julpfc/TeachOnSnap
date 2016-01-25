package com.julvez.pfc.teachonsnap.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.LessonPropertyName;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.tag.model.TagPropertyName;

/**
 * Repository to access/modify data related to tags.
 * <p>
 * To be used only by the {@link TagService}'s implementation
 */
public interface TagRepository {

	/**
	 * Returns the corresponding Tag object from the specified id.
	 * @param idTag Tag's id
	 * @return Tag object from id if it's a valid id, null otherwise
	 */
	public Tag getTag(int idTag);
	
	/**
	 * Returns lessons ids tagged by the specified tag. If the lessons number is 
	 * greater than the maximum number of lessons allowed for a page 
	 * {@link LessonPropertyName}, it will paginate them starting by firstResult.
	 * @param tag lesson's are tagged with
	 * @param firstResult first lesson the pagination should start from.
	 * @return List of lessons ids for this page
	 */
	public List<Integer> getLessonIDsFromTag(String tag, int firstResult);
	
	/**
	 * Returns lesson's tags ids.
	 * @param idLesson Lesson.
	 * @return List of tags ids for this lesson.
	 */
	public List<Integer> getLessonTagIDs(int idLesson);
	
	/**
	 * Returns the cloud of tags ids and weights for: Most used tags.
	 * @return list with the cloud's tag ids and weights to create a cloud of tags
	 */
	public List<Object[]> getTagUseCloudTags();
	
	/**
	 * Returns the cloud of tags ids and weights for: Most prolific authors.
	 * @return list with the cloud's tag ids and weights to create a cloud of tags
	 */
	public List<Object[]> getAuthorCloudTags();
	
	/**
	 * Adds the list of tags to this lesson
	 * @param idLesson to add tags to
	 * @param tagIDs List of tags's ids to be added
	 */
	public void addLessonTags(int idLesson, ArrayList<Integer> tagIDs);

	/**
	 * Returns the corresponding Tag id from the specified tag's text.
	 * @param tag Tag's text
	 * @return Tag id from tag's text if it's an existing tag, -1 otherwise
	 */
	public int getTagID(String tag);

	/**
	 * Creates a new tag
	 * @param tag 's text
	 * @return newly created tag's id, -1 otherwise
	 */
	public int createTag(String tag);

	/**
	 * Removes the list of tags from this lesson
	 * @param idLesson to remove tags from
	 * @param removeTagIDs List of ids to be removed
	 */
	public void removeLessonTags(int idLesson, ArrayList<Integer> removeTagIDs);

	/**
	 * Returns a list of Tag ids found by the searching tags by 
	 * matching the searchQuery with the tags's text. If the results 
	 * number is greater than the maximum number of tags allowed for 
	 * a cloud {@link TagPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the tags texts
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of Tag ids matching the query
	 */
	public List<Integer> searchTag(String searchQuery, int firstResult);

	/**
	 * Returns all tags ids. If the tags number is greater than the 
	 * maximum number of tags allowed for a cloud {@link TagPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first tag the pagination should start from.
	 * @return List of tags ids for this page
	 */
	public List<Integer> getTags(int firstResult);

	/**
	 * Returns the cloud of tags ids for: Most searched tags.
	 * @return list with the cloud's tag ids to create a cloud of tags
	 */
	public List<Integer> getTagSearchCloudTags();

	/**
	 * Returns the cloud of tags ids  for: Most viewed lessons.
	 * @return list with the cloud's tag ids to create a cloud of tags
	 */
	public List<Integer> getLessonViewCloudTags();

}
