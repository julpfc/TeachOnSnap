package com.julvez.pfc.teachonsnap.stats.model;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Domain entity. Describes a visit to the web application.
 * It can contain a User reference if logged in. It contains 
 * lessons and tags viewed during this visit (session).
 */
public class Visit {

	/** Visit's identifier */
	private int id;
	
	/** Visit's language */
	private short idLanguage;
	
	/** Visit's user if logged in, null otherwise */
	private User user;
	
	/** List of viewed lessons during the visit */
	private List<Integer> viewedLessons;
	
	/** List of viewed tags during the visit */
	private List<Integer> viewedTags;
	
	/**
	 * Initializes a visit with default values.
	 * @param idVisit Visit's id
	 */
	public Visit(int idVisit) {
		this.id = idVisit;
		this.idLanguage = -1;
		this.user = null;
		viewedLessons = new ArrayList<Integer>();
		viewedTags = new ArrayList<Integer>();
	}

	/**
	 * @return Visit's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Visit's user if logged in, null otherwise
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets visit's user if logged in
	 * @param user visit's user
	 */
	public void setUser(User user) {
		this.user = user;					
	}

	/**
	 * @return Visit's language
	 */
	public short getIdLanguage() {
		return idLanguage;
	}
	
	/**
	 * Sets visit's language
	 * @param idLanguage New visit's language
	 */
	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}
	
	/**
	 * Checks if a lesson was viewed during this visit
	 * @param idLesson Lesson to be checked
	 * @return true if the lesson was viewed during the visit
	 */
	public boolean isViewedLesson(int idLesson){
		return viewedLessons.contains(idLesson);
	}
	
	/**
	 * Adds a new lesson to the list of viewed lessons
	 * @param idLesson New viewed lesson to add
	 */
	public void addViewedLesson(int idLesson){
		viewedLessons.add(idLesson);
	}

	/**
	 * Checks if a tag was viewed during this visit
	 * @param idTag Tag to be checked
	 * @return true if the tag was viewed during the visit
	 */
	public boolean isViewedTag(int idTag) {
		return viewedTags.contains(idTag);
	}

	/**
	 * Adds a new tag to the list of viewed tags
	 * @param idTag New viewed tag to add
	 */
	public void addViewedTag(int idTag) {
		viewedTags.add(idTag);		
	}	
	
	@Override
	public String toString() {
		return "Visit [id=" + id + ", idLanguage=" + idLanguage 
				+ ", user=" + user	+ ", viewedLessons=" + viewedLessons 
				+ ", viewedTags=" + viewedTags + "]";
	}		
}
