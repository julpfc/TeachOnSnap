package com.julvez.pfc.teachonsnap.model.visit;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.user.User;

public class Visit {

	private int id;
	private int idVisitUser;
	private short idLanguage;
	
	private User user;
	
	private List<Integer> viewedLessons;
	
	public Visit(int idVisit) {
		this.id = idVisit;
		this.idVisitUser= -1;
		this.idLanguage = -1;
		this.user = null;
		viewedLessons = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;					
	}

	public short getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}

	public boolean isViewedLesson(int idLesson){
		return viewedLessons.contains(idLesson);
	}
	
	public void addViewedLesson(int idLesson){
		viewedLessons.add(idLesson);
	}

	public int getIdVisitUser() {
		return idVisitUser;
	}

	public void setIdVisitUser(int idVisitUser) {
		this.idVisitUser = idVisitUser;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", idVisitUser=" + idVisitUser
				+ ", idLanguage=" + idLanguage + ", user=" + user
				+ ", viewedLessons=" + viewedLessons + "]";
	}	
	
	
}
