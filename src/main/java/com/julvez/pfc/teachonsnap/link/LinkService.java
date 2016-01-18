package com.julvez.pfc.teachonsnap.link;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.link.model.Link;

/**
 * Provides the functionality to work with lesson's links.
 */
public interface LinkService {

	/**
	 * Get Link object from id
	 * @param idLink Link id
	 * @return Link corresponding to this id if exists, null otherwise
	 */	
	public Link getLink(int idLink);
	
	/**
	 * Returns Lesson's more info links
	 * @param idLesson Lesson id
	 * @return List of links to more info resources for this lesson
	 */
	public List<Link> getMoreInfoLinks(int idLesson);
	
	/**
	 * Returns Lesson's sources links
	 * @param idLesson Lesson id
	 * @return List of links to sources for this lesson
	 */
	public List<Link> getSourceLinks(int idLesson);
	
	/**
	 * Adds the list of sources links urls to this lesson
	 * @param lesson to add urls to
	 * @param sources List of urls to be added
	 */
	public void addLessonSources(Lesson lesson, List<String> sources);

	/**
	 * Creates a new link in the repository
	 * @param URL for the new link
	 * @return new link's id if created, -1 otherwise
	 */
	public int createLink(String URL);

	/**
	 * Adds the list of more info link urls to this lesson
	 * @param lesson to add urls to
	 * @param moreInfos List of urls to be added
	 */
	public void addLessonMoreInfo(Lesson lesson, List<String> moreInfos);

	/**
	 * Compares the old and new list of links/urls to remove the links no longer needed 
	 * and to add the new urls required from/to the lesson.
	 * @param lesson to add/remove urls to/from
	 * @param oldLinks current links in the lesson
	 * @param newLinks new links for the lesson to be saved
	 * @return true if the lesson is modified to add or remove any link
	 */
	public boolean saveLessonMoreInfo(Lesson lesson, List<Link> oldLinks, List<String> newLinks);
	
	
	/**
	 * Compares the old and new list of links/urls to remove the links no longer needed 
	 * and to add the new urls required from/to the lesson.
	 * @param lesson to add/remove urls to/from
	 * @param oldLinks current links in the lesson
	 * @param newLinks new links for the lesson to be saved
	 * @return true if the lesson is modified to add or remove any link
	 */
	public boolean saveLessonSources(Lesson lesson, List<Link> oldLinks, List<String> newLinks);

	/**
	 * Removes the list of sources from this lesson
	 * @param lesson to remove links from
	 * @param removeLinkIDs List of ids to be removed
	 */
	public void removeLessonSources(Lesson lesson, ArrayList<Integer> removeLinkIDs);
	
	/**
	 * Removes the list of more info links from this lesson
	 * @param lesson to remove links from
	 * @param removeLinkIDs List of ids to be removed
	 */
	public void removeLessonMoreInfos(Lesson lesson, ArrayList<Integer> removeLinkIDs);

}
