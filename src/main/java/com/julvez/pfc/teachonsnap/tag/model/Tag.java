package com.julvez.pfc.teachonsnap.tag.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Entity. Describes a lesson tag so the lesson can be searched by. 
 * It contains the tag text, an MD5 hash and an URL pointing to the lessons. 
 * tagged with it.
 */
@Entity
public class Tag {
	
	/** Tag identifier and primary key for the entity */
	@Id
	@Column (name="idTag")
	private int id;
	
	/** Tag text */
	private String tag;
	
	/** Tag's MD5 hash */
	@Transient
	private String MD5;
	
	/** Lessons tagged with this tag's URL*/
	@Transient
	private String url;

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag=" + tag + "]";
	}

	/**
	 * @return tag's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return tag's text
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets tag's URL to access tagged lessons
	 * @param url tagged lessons URL
	 */
	public void setURLs(String url){
		this.url = url;
	}
	
	/**
	 * @return tag's URL pointing to the tagged leesons
	 */
	public String getURL() {
		return url + tag;
	}
	
	/**
	 * Sets tag's MD5 hash
	 * @param mD5 hash
	 */
	public void setMD5(String md5) {
		MD5 = md5;
	}
	
	/**
	 * @return tag's MD5 hash
	 */
	public String getMD5() {		
		return MD5;
	}
	
	/**
	 * Sets tag's id
	 * @param id new tag's id
	 */
	public void setId(int id) {
		this.id = id;
		
	}
	/**
	 * Sets tag's text
	 * @param tag New text
	 */
	public void setTag(String tag) {
		this.tag = tag;		
	}
}
