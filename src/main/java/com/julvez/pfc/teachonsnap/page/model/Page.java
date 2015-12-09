package com.julvez.pfc.teachonsnap.page.model;


public class Page {

	private String name;
	private String link;
	private String extraName;
	
	public Page(PageNameKey name, String link) {
		this(name, null, link);
	}

	public Page(PageNameKey name, String extraName, String link) {
		super();
		this.name = name.toString();
		this.link = link;
		this.extraName = extraName; 
	}
	
	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public String getExtraName() {
		return extraName;
	}

	public void setExtraName(String extraName) {
		this.extraName = extraName;
	}
	
}
