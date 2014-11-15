package com.julvez.pfc.teachonsnap.model.lesson;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;

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
	private int idLessonTest;

	@Transient
	private User author;
	@Transient
	private Language language;
	
	
	@Override
	public String toString() {
		return "Lesson [id=" + id + ", title=" + title + ", idUser=" + idUser
				+ ", idLanguage=" + idLanguage + ", date=" + date + ", text="
				+ text + ", URIname=" + URIname + ", idLessonTest="
				+ idLessonTest + ", author=" + author + ", language="
				+ language + "]";
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
	
	public String getTestURL() {
		return idLessonTest>0?("/test/"+URIname):"#";
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

	public int getIdLessonTest() {
		return idLessonTest;
	}

	public void setIdLessonTest(int idLessonTest) {
		this.idLessonTest = idLessonTest;
	}
	
	
}
