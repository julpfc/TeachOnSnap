package com.julvez.pfc.teachonsnap.lang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity. Describes a language used by an user in our application, 
 * contains an ID and the 2 letter ISO code for this language.
 */
@Entity
public class Language {
	/**
	 * Language identifier and primary key for the entity
	 */
	@Id
	@Column(name = "idLanguage")
	private short id;	
	
	/** 2 letter ISO code string for the language */
	private String language;
		
	@Override
	public String toString() {
		return "Language [id=" + id + ", lang=" + language + "]";
	}

	/**
	 * @return Language's ID
	 */
	public short getId() {
		return id;
	}

	/**
	 * For completition purpose only. Not to be used to modify the object.
	 * @param id Language identifier
	 */
	public void setId(short id) {
		this.id = id;
	}

	/**
	 * @return 2 letter ISO code string for the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * For completition purpose only. Not to be used to modify the object.
	 * @param lang 2 letter ISO code string for the language
	 */
	public void setLanguage(String lang) {
		this.language = lang;
	}		
}
