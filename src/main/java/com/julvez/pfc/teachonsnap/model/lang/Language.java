package com.julvez.pfc.teachonsnap.model.lang;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Language {
	@Id
	@Column(name = "idLanguage")
	private short id;	
	private String language;
	
	
	@Override
	public String toString() {
		return "Language [id=" + id + ", lang=" + language + "]";
	}


	public short getId() {
		return id;
	}


	public void setId(short id) {
		this.id = id;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String lang) {
		this.language = lang;
	}
	
	
}
