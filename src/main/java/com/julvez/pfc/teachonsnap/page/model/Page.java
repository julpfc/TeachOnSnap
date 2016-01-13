package com.julvez.pfc.teachonsnap.page.model;


/**
 * Domain entity describes a page from the user point of view, with
 * the page's name, a link to the page and and optional extra descriptor. *
 */
public class Page {

	/**	Page's name */
	private String name;
	/**	URL pointing to the page */
	private String link;
	/**	optional descriptor with extra information about the page (nullable) */
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
	
	/**
	 * @return page's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return link to the page
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return optional descriptor with extra information about the page (nullable)
	 */
	public String getExtraName() {
		return extraName;
	}

	/**
	 * @param extraName optional descriptor with extra information about the page (nullable)
	 */
	public void setExtraName(String extraName) {
		this.extraName = extraName;
	}
	
}
