package com.julvez.pfc.teachonsnap.link.repository;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.model.Link;

/**
 * Repository to access/modify data related to lesson's links.
 * <p>
 * To be used only by the {@link LinkService}'s implementation
 */
public interface LinkRepository {

	/**
	 * Returns Lesson's more info links identifiers
	 * @param idLesson Lesson id
	 * @return List of link identifiers to more info resources for this lesson
	 */
	public List<Integer> getMoreInfoLinkIDs(int idLesson);

	/**
	 * Returns Lesson's sources links identifiers
	 * @param idLesson Lesson id
	 * @return List of link identifiers to sources for this lesson
	 */
	public List<Integer> getSourceLinkIDs(int idLesson);

	/**
	 * Get Link object from id
	 * @param idLink Link id
	 * @return Link corresponding to this id if exists, null otherwise
	 */
	public Link getLink(int idLink);

	/**
	 * Returns the link id from a URL if already exists on the repository
	 * @param url to get the link from
	 * @return link id if the URL belongs to an existing link in the repository
	 */
	public int getLinkID(String url);

	/**
	 * Creates a new link in the repository
	 * @param url for the new link
	 * @param desc for the new link
	 * @return new link's id if created, -1 otherwise
	 */
	public int createLink(String url, String desc);

	/**
	 * Adds the list of sources links ids to this lesson
	 * @param idLesson Lesson to add links to
	 * @param sourceLinkIDs List of ids to be added
	 */
	public void addLessonSources(int idLesson, ArrayList<Integer> sourceLinkIDs);

	/**
	 * Adds the list of more info links ids to this lesson
	 * @param idLesson Lesson to add links to
	 * @param moreInfoLinkIDs List of ids to be added
	 */
	public void addLessonMoreInfos(int idLesson, ArrayList<Integer> moreInfoLinkIDs);

	/**
	 * Removes the list of sources from this lesson
	 * @param idLesson Lesson to remove links from
	 * @param removeLinkIDs List of ids to be removed
	 */
	public void removeLessonSources(int idLesson, ArrayList<Integer> removeLinkIDs);

	/**
	 * Removes the list of more info links from this lesson
	 * @param idLesson Lesson to remove links from
	 * @param removeLinkIDs List of ids to be removed
	 */
	public void removeLessonMoreInfos(int idLesson, ArrayList<Integer> removeLinkIDs);

}
