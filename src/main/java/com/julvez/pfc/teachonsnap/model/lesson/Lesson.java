package com.julvez.pfc.teachonsnap.model.lesson;

import java.util.Date;
import java.util.List;

public class Lesson {

	private int id;
	private String title;
	private int idUser;
	private int idLanguage;
	private Date date;
	
	private String text;
	
	private List<Integer> sourceLinks;
	private List<Integer> moreInfoLinks;
	private List<Integer> linkedLessons;
	private List<Integer> tags;
	
	@Override
	public String toString() {
		return "Lesson [id=" + id + ", title=" + title + ", idUser=" + idUser
				+ ", idLanguage=" + idLanguage + ", date=" + date + ", text="
				+ text + ", sourceLinks=" + sourceLinks + ", moreInfoLinks="
				+ moreInfoLinks + ", linkedLessons=" + linkedLessons
				+ ", tags=" + tags + "]";
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Integer> getSourceLinks() {
		return sourceLinks;
	}

	public void setSourceLinks(List<Integer> sourceLinks) {
		this.sourceLinks = sourceLinks;
	}

	public List<Integer> getMoreInfoLinks() {
		return moreInfoLinks;
	}

	public void setMoreInfoLinks(List<Integer> moreInfoLinks) {
		this.moreInfoLinks = moreInfoLinks;
	}

	public List<Integer> getLinkedLessons() {
		return linkedLessons;
	}

	public void setLinkedLessons(List<Integer> linkedLessons) {
		this.linkedLessons = linkedLessons;
	}

	public List<Integer> getTags() {
		return tags;
	}

	public void setTags(List<Integer> tags) {
		this.tags = tags;
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
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
