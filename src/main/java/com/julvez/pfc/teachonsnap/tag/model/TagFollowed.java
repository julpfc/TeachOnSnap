package com.julvez.pfc.teachonsnap.tag.model;

/**
 * Domain entity. Describes a followable tag, subtype of Tag 
 * that can be followed by users or groups.
 */
public class TagFollowed extends Tag {

	/** Indicates if the tag is followed */
	private boolean followed;
	
	/**
	 * Constructs an TagFollowed object from a Tag object
	 * @param tag which TagFollowed is copied from.
	 */
	public TagFollowed(Tag tag) {	
		super();
		setId(tag.getId());
		setTag(tag.getTag());
		setMD5(tag.getMD5());
	}

	/**
	 * @return if the tag is followed
	 */
	public boolean isFollowed() {
		return followed;
	}

	/**
	 * Sets if the tag is followed
	 * @param followed or not
	 */
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}
	
}
