package com.julvez.pfc.teachonsnap.page.model;


public class Page {

	private String name;
	private String link;
	
	public Page(PageNameKey name, String link) {
		super();
		this.name = name.toString();
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}
	
}
