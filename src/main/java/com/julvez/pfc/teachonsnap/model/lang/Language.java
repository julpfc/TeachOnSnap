package com.julvez.pfc.teachonsnap.model.lang;

public class Language {
	private int id;
	private String lang;
	
	
	@Override
	public String toString() {
		return "Language [id=" + id + ", lang=" + lang + "]";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}
	
	
}
