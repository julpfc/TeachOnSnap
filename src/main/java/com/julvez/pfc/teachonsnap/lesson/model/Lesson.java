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

@Entity
public class Lesson {

	@Id
	@Column (name="idLesson")
	private int id;
	private String title;
	private int idUser;
	private short idLanguage;
	private Date date;
	private String text;
	private String URIname;	
	private int idLessonMedia;
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;
	private boolean testAvailable;
	private boolean draft;	
	
	@Transient
	private User author;
	@Transient
	private Language language;
	
	@Transient
	private String url;
	@Transient
	private String previewURL;
	@Transient
	private String editURL;
	@Transient
	private String commentURL;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public short getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getURIname() {
		return URIname;
	}

	public void setURIname(String uRIname) {
		URIname = uRIname;
	}
	
	public void setURLs(String lessonURL, String previewURL, String editURL, String commentURL, String newTestURL){
		this.url = lessonURL;
		this.previewURL = previewURL;
		this.editURL = editURL;
		this.commentURL = commentURL;
		this.newTestURL = newTestURL;
	}
	
	public String getURL() {
		if(draft){
			return previewURL + id;
		}
		else return url + URIname;
	}
	
	public String getEditURL() {
		return editURL + id;
	}
	
	public String getCommentURL() {
		return commentURL + URIname;
	}
	
	public String getNewTestURL() {
		return newTestURL + id;
	}
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getIdLessonMedia() {
		return idLessonMedia;
	}

	public void setIdLessonMedia(int idLessonMedia) {
		this.idLessonMedia = idLessonMedia;
	}
	
	public boolean isTestAvailable() {
		return testAvailable;
	}

	public void setTestAvailable(boolean testAvailable) {
		this.testAvailable = testAvailable;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}
		
}
