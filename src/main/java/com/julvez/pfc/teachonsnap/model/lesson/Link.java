package com.julvez.pfc.teachonsnap.model.lesson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Link {

	@Id
	@Column (name="idLink")
	private int id;
	private String URL;	
	private String desc;
	
	@Override
	public String toString() {
		return "Link [id=" + id + ", url=" + URL + ", desc=" + desc + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getURL() {
		return URL;
	}

	public void setUrl(String url) {
		this.URL = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
