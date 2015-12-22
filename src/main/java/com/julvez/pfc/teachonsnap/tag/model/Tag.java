package com.julvez.pfc.teachonsnap.tag.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Tag {
	
	@Id
	@Column (name="idTag")
	private int id;
	private String tag;
	@Transient
	private String MD5;
	
	@Transient
	private String url;

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag=" + tag + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}	
	
	public void setURLs(String url){
		this.url = url;
	}
	
	public String getURL() {
		return url + tag;
	}
	
	public void setMD5(String md5) {
		MD5 = md5;
	}
	
	public String getMD5() {		
		return MD5;
	}
	
}
