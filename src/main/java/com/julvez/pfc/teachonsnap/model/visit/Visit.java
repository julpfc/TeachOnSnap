package com.julvez.pfc.teachonsnap.model.visit;

import com.julvez.pfc.teachonsnap.model.user.User;

public class Visit {

	private int id;
	private int idUser;
	private short idLanguage;
	
	private User user;
	
	public Visit(int idVisit) {
		this.id = idVisit;
		this.idUser = -1;
		this.idLanguage = -1;
		this.user = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if(user!=null){
			idUser = user.getId();
		}			
	}

	public short getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", idLanguage=" + idLanguage + ", user="
				+ user + "]";
	}
	
	
}
