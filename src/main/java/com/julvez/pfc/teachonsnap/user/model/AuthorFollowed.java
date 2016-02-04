package com.julvez.pfc.teachonsnap.user.model;

/**
 * Domain entity. Describes an Author, subtype of User 
 * that can be followed by other users or groups.
 */
public class AuthorFollowed extends User {

	/** Indicates if the author is followed */
	private boolean followed;

	/**
	 * Constructs an Author object from an User object
	 * @param user whom Author is copied from.
	 */
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

	/**
	 * @return if the author is followed
	 */
	public boolean isFollowed() {
		return followed;
	}

	/**
	 * Sets if the author is followed
	 * @param followed or not
	 */
	public void setFollowed(boolean followed) {
		this.followed = followed;
	}	
	
}
