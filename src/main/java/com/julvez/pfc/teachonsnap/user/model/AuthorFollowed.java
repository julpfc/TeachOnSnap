package com.julvez.pfc.teachonsnap.user.model;


public class AuthorFollowed extends User {

	private boolean followed;

	public AuthorFollowed(User user) {
		super();
		setId(user.getId());
		setEmail(user.getEmail());
		
		setFirstName(user.getFirstName());
		setLastName(user.getLastName());
		setIdLanguage(user.getIdLanguage());
		setAuthor(user.isAuthor());
		setAdmin(user.isAdmin());
		setBanned(user.isBanned());
		setURIName(user.getURIName());
		setLanguage(user.getLanguage());
		setMD5(user.getMD5());
		setBannedInfo(user.getBannedInfo());
	}

	public boolean isFollowed() {
		return followed;
	}

	public void setFollowed(boolean followed) {
		this.followed = followed;
	}	
	
}
