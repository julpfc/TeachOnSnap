package com.julvez.pfc.teachonsnap.tag.model;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Domain entity. Describes a tag to be part of a cloud of tags.
 * Can be created from a Tag, an author (User) or a Lesson.
 * It contains the tag's text, the relative weight at the cloud
 * and a URL.
 */
public class CloudTag {
	
	/** Tag's text */
	private String tag;
	
	/** Tag's URL */
	private String URL;
	
	/** Tag's relative weight at the cloud */
	private short weight;

	/** 
	 * Creates a new CloudTag from a Tag with this relative 
	 * weight at the cloud.
	 * @param tag CloudTag will be created from this Tag
	 * @param weight Tag's relative weight at the cloud
	 */
	public CloudTag(Tag tag, short weight){
		this.tag = tag.getTag();
		this.URL = tag.getURL();
		this.weight=weight;
	}
	
	/** 
	 * Creates a new CloudTag from a User (author) with 
	 * this relative weight at the cloud.
	 * @param author CloudTag will be created from this User
	 * @param weight Tag's relative weight at the cloud
	 */
	public CloudTag(User author, short weight){
		this.tag = author.getFullName();
		this.URL = author.getURL();
		this.weight=weight;
	}
	
	/** 
	 * Creates a new CloudTag from a Lesson with this relative 
	 * weight at the cloud.
	 * @param lesson CloudTag will be created from this Lesson
	 * @param weight Tag's relative weight at the cloud
	 */
	public CloudTag(Lesson lesson, short weight){
		this.tag = lesson.getTitle();
		this.URL = lesson.getURL();
		this.weight = weight;
	}
	
	/**
	 * @return tag's text
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * @return tag's relative weight at the cloud.
	 */
	public short getWeight() {
		return weight;
	}
	
	/**
	 * @return tag's URL
	 */
	public String getURL() {
		return URL;
	}

	@Override
	public String toString() {
		return "CloudTag [tag=" + tag + ", URL=" + URL + ", weight=" + weight
				+ "]";
	}
}
