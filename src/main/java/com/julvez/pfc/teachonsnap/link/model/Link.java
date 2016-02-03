package com.julvez.pfc.teachonsnap.link.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Entity. Describes a link to further information on a topic or the sources of a lesson. 
 * It contains the URL an a description, a MD5 hash from the URL is optionally provided. 
 */
@Entity
public class Link {

	/** Link identifier and primary key for the entity */
	@Id
	@Column (name="idLink")
	private int id;
	
	/** Link's URL */
	private String URL;
	
	/** Link's description */
	private String desc;
	
	/** MD5 hash from the URL */
	@Transient
	private String MD5;
	
	@Override
	public String toString() {
		return "Link [id=" + id + ", url=" + URL + ", desc=" + desc + "]";
	}
	
	/**
	 * @return Link's identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Link's URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @return Link's description
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * @return MD5 hash fro the URL
	 */
	public String getMD5() {
		return MD5;
	}

	/**
	 * Sets the MD5 from the URL
	 * @param mD5 New MD5 hash value
	 */
	public void setMD5(String mD5) {
		MD5 = mD5;
	}

}
