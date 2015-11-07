package com.julvez.pfc.teachonsnap.link.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Link {

	@Id
	@Column (name="idLink")
	private int id;
	private String URL;	
	private String desc;
	@Transient
	private String MD5;
	
	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

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
