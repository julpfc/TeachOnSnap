package com.julvez.pfc.teachonsnap.model.lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {
	
	@Id
	@Column (name="idTag")
	private int id;
	private String tag;
	
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
	
	public String getURL() {
		return "/tag/" + tag;
	}
	
}
