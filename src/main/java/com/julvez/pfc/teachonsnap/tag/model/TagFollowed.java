package com.julvez.pfc.teachonsnap.tag.model;

public class TagFollowed extends Tag {

	private boolean followed;
	
	
	public TagFollowed(Tag tag) {	
		super();
		setId(tag.getId());
		setTag(tag.getTag());
		setMD5(tag.getMD5());
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}	
	
}
