package com.julvez.pfc.teachonsnap.user.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.lang.model.Language;

/**
 * Entity. Describes an user of the application. Besides regular users,
 * they can be "authors" if are allowed to create and edit lessons, 
 * or "administrators" if they can manage the application and other users. 
 * It contains an email, fullname and the preferred language. 
 * The followed lessons and authors, and several usefull related URLs 
 * are also stored at the object.
 * <p>
 * A user can be banned by an administrator.
 */
@Entity
public class User {

	/**	User identifier and primary key for the entity */
	@Id
	@Column (name="idUser")
	private int id;
	
	/** User's email, used as login for the application */
	private String email;
	
	/** User's first name */
	private String firstName;
	
	/** User's last name */
	private String lastName;
	
	/** User's preferred language id */
	private short idLanguage;
	
	/** User's additional information */
	private String extraInfo;
	
	/** Indicates if the user is an author, who can create and edit lessons */
	private boolean author;
	
	/** Indicates if the user is an administrator, who can manage the applications and other users  */
	private boolean admin;
	
	/** Indicates if the user is banned  */
	private boolean banned;
	
	/** Author's URI name: full name without special characters (only for active authors, null otherwise) */
	private String URIName;
	
	/** User's language */
	@Transient
	private Language language;
	
	/** User's MD5 hash based on his email */
	@Transient
	private String MD5;
	
	/** User's ban related information */
	@Transient
	private UserBannedInfo bannedInfo;
	
	/** Authors followed by user */
	@Transient
	private Map<String,String> authorFollowed;
	
	/** Lessons followed by user  */
	@Transient
	private Map<String,String> lessonFollowed;
	
	/** Author's URL */
	@Transient
	private String authorURL;
	
	/** Author's drafts URL  */
	@Transient
	private String draftsURL;
		
	
	/**
	 * @return authors followed by this user <[id],id>
	 */
	public Map<String, String> getAuthorFollowed() {
		return authorFollowed;
	}

	/**
	 * Sets authors followed by this user 
	 * @param authorFollowed <[id],id>
	 */
	public void setAuthorFollowed(Map<String, String> authorFollowed) {
		this.authorFollowed = authorFollowed;
	}

	/**
	 * @return lessons followed by this user <[id],id>
	 */
	public Map<String, String> getLessonFollowed() {
		return lessonFollowed;
	}

	/**
	 * Sets lessons followed by this user
	 * @param lessonFollowed <[id],id>
	 */
	public void setLessonFollowed(Map<String, String> lessonFollowed) {
		this.lessonFollowed = lessonFollowed;
	}

	/**
	 * @return User's MD5 hash based on his email 
	 */
	public String getMD5() {
		return MD5;
	}

	/**
	 * Sets user's MD5 hash based on his email
	 * @param mD5 hash based on his email
	 */
	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	/**
	 * @return User's preferred language id
	 */
	public short getIdLanguage() {
		return idLanguage;
	}

	/**
	 * Sets user's preferred language id
	 * @param idLanguage New preferred language id
	 */
	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}

	/**
	 * @return User's language 
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets user's language
	 * @param language new user's language
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * @return Author's URI name: full name without special 
	 * characters (only for active authors, null otherwise)
	 */
	public String getURIName() {
		return URIName;
	}

	/**
	 * Sets author's URI name
	 * @param uRIName full name without special characters
	 */
	public void setURIName(String uRIName) {
		URIName = uRIName;
	}

	/**
	 * @return if user is an author
	 */
	public boolean isAuthor() {
		return author;
	}

	/**
	 * Sets if user is an author
	 * @param author or not
	 */
	public void setAuthor(boolean author) {
		this.author = author;
	}

	/**
	 * @return User's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets user's id
	 * @param id of the user
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return User's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's email
	 * @param email new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name
	 * @param firstName new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user's last name
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return user's full name
	 */
	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	/**
	 * Sets author related URLs to access lessons or drafts
	 * @param authorURL author's lessons URL
	 * @param draftsURL author's drafts URL
	 */
	public void setURLs(String authorURL, String draftsURL){
		this.authorURL = authorURL;
		this.draftsURL = draftsURL;
	}
	
	/**
	 * @return author's lessons URL
	 */
	public String getURL() {
		if(URIName!=null)
			return authorURL + URIName;
		else return "#";		
	}
	
	/**
	 * @return author's drafts URL
	 */
	public String getDraftsURL() {
		return draftsURL + id;				
	}

	/**
	 * @return if the user is an administrator
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Sets if the user is an administrator
	 * @param admin or not
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return if user is banned
	 */
	public boolean isBanned() {
		return banned;
	}

	/**
	 * Sets if the user is banned
	 * @param banned or not
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	/**
	 * @return user's ban related information
	 */
	public UserBannedInfo getBannedInfo() {
		return bannedInfo;
	}

	/**
	 * Sets user's ban related information
	 * @param bannedInfo user's ban information
	 */
	public void setBannedInfo(UserBannedInfo bannedInfo) {
		this.bannedInfo = bannedInfo;
	}

	/**
	 * @return additional information about the user
	 */
	public String getExtraInfo() {
		return extraInfo;
	}

	/**
	 * Sets additional information about the user
	 * @param extraInfo additional information about the user
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", idLanguage="
				+ idLanguage + ", author=" + author + ", admin=" + admin
				+ ", URIName=" + URIName + ", language=" + language 
				+ ", bannedInfo=" + bannedInfo + ", authorFollowed=" + authorFollowed
				+ ", lessonFollowed=" + lessonFollowed + ", extraInfo=" + extraInfo+"]";
	}
}
