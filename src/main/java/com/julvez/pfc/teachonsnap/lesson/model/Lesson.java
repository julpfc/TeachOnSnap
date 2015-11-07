package com.julvez.pfc.teachonsnap.lesson.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.lang.model.Language;
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
	private boolean testAvailable;

	@Transient
	private User author;
	@Transient
	private Language language;
	
	
	@Override
	public String toString() {
		return "Lesson [id=" + id + ", title=" + title + ", idUser=" + idUser
				+ ", idLanguage=" + idLanguage + ", date=" + date + ", text="
				+ text + ", URIname=" + URIname + ", idLessonMedia="
				+ idLessonMedia + ", testAvailable=" + testAvailable
				+ ", author=" + author + ", language=" + language + "]";
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
	
	public String getURL() {
		return "/lesson/"+URIname;
	}
	
	public String getEditURL() {
		return "/lesson/edit/"+id;
	}
	
	public String getCommentURL() {
		return "/lesson/comment/"+URIname;
	}
	
	public String getNewTestURL() {
		return "/test/new/"+id;
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
}
