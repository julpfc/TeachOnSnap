package com.julvez.pfc.teachonsnap.model.lesson;

public class Link {

	private int id;
	private String url;	
	private String desc;
	
	@Override
	public String toString() {
		return "Link [id=" + id + ", url=" + url + ", desc=" + desc + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
