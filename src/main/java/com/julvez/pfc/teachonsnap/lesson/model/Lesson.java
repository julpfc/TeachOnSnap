package com.julvez.pfc.teachonsnap.lesson.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.media.model.MediaType;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Entity. Describes a lesson. Lesson is created by an author as
 * a draft than can be published and will be visible to other users. *  
 * It contains a title and optionally an extra text, a media file,
 * an evaluation test. 
 * <p>
 * A lesson can be followed by other users
 */
@Entity
public class Lesson {

	/**	Lesson identifier and primary key for the entity */
	@Id
	@Column (name="idLesson")
	private int id;
	
	/** Lesson's title */
	private String title;
	
	/** Lesson's author id*/
	private int idUser;
	
	/** Lesson's language id */
	private short idLanguage;
	
	/** Lesson's last modification date*/
	private Date date;
	
	/** Lesson's optional text, nullable*/
	private String text;
	
	/** Lesson's URI name: title without special characters (used in URLs) */
	private String URIname;	
	
	/** Lesson's media id if present, -1 otherwise*/
	private int idLessonMedia;
	
	/** Lesson's media type (enum)*/
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;
	
	/** Indicates if there is an evaluation test for this lesson */
	private boolean testAvailable;
	
	/** Indicates if this lesson is a draft */
	private boolean draft;	
	
	/** Lesson's author */
	@Transient
	private User author;
	
	/** Lesson's language */
	@Transient
	private Language language;
	
	/** Lesson's URL */
	@Transient
	private String url;
	
	/** Lesson's preview URL */
	@Transient
	private String previewURL;
	
	/** Lesson's edit URL */
	@Transient
	private String editURL;
	
	/** Lesson's comment URL */
	@Transient
	private String commentURL;
	
	/** Lesson's new test URL */
	@Transient
	private String newTestURL;
	
	
	@Override
	public String toString() {
		return "Lesson [id=" + id + ", title=" + title + ", idUser=" + idUser
				+ ", idLanguage=" + idLanguage + ", date=" + date + ", text="
				+ text + ", URIname=" + URIname + ", idLessonMedia="
				+ idLessonMedia + ", mediaType=" + mediaType
				+ ", testAvailable=" + testAvailable + ", draft=" + draft
				+ ", author=" + author + "]";
	}

	/**
	 * @return Lesson's optional text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets lesson's optional text
	 * @param text optional text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return Lesson's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets lesson's id
	 * @param id new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return lesson's title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets lesson's title
	 * @param title new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/** 
	 * @return Lesson's author id
	 */
	public int getIdUser() {
		return idUser;
	}
	
	/**
	 * Sets author id
	 * @param idUser Author's id
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * @return Lesson's language id
	 */
	public short getIdLanguage() {
		return idLanguage;
	}
	
	/**
	 * Sets lesson's language id
	 * @param idLanguage language's id
	 */
	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}
	
	/**
	 * @return lesson's last modification date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return Lesson's URI name: title without special characters (used in URLs)
	 */
	public String getURIname() {
		return URIname;
	}

	/**
	 * Sets lesson's URI name
	 * @param uRIname title without special characters
	 */
	public void setURIname(String uRIname) {
		URIname = uRIname;
	}
	
	/**
	 * Sets lesson's URLs
	 * @param lessonURL Lesson's URL
	 * @param previewURL preview URL
	 * @param editURL edit URL
	 * @param commentURL comment URL
	 * @param newTestURL new test URL
	 */
	public void setURLs(String lessonURL, String previewURL, String editURL, String commentURL, String newTestURL){
		this.url = lessonURL;
		this.previewURL = previewURL;
		this.editURL = editURL;
		this.commentURL = commentURL;
		this.newTestURL = newTestURL;
	}
	
	/**
	 * @return Lesson's URL
	 */
	public String getURL() {
		if(draft){
			return previewURL + id;
		}
		else return url + URIname;
	}
	
	/**
	 * @return Lesson's edit URL
	 */
	public String getEditURL() {
		return editURL + id;
	}
	
	/**
	 * @return Lesson's comment URL
	 */
	public String getCommentURL() {
		return commentURL + URIname;
	}
	
	/**
	 * @return Lesson's new test URL
	 */
	public String getNewTestURL() {
		return newTestURL + id;
	}
	
	/**
	 * @return Lesson's Auhtor
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Sets Lesson's author
	 * @param author Author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return Lesson's language
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets lesson's language
	 * @param language new language
	 */
	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * @return Lesson's media id
	 */
	public int getIdLessonMedia() {
		return idLessonMedia;
	}

	/**
	 * Sets lesson's media id
	 * @param idLessonMedia media's id
	 */
	public void setIdLessonMedia(int idLessonMedia) {
		this.idLessonMedia = idLessonMedia;
	}
	
	/**
	 * @return true if there is an evaluation test for this lesson
	 */
	public boolean isTestAvailable() {
		return testAvailable;
	}

	/**
	 * @return Lesson's media type
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * Sets lesson's media type
	 * @param mediaType Media type
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return true if this lesson is a draft
	 */
	public boolean isDraft() {
		return draft;
	}
}
