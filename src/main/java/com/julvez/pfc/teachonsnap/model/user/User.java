package com.julvez.pfc.teachonsnap.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.model.lang.Language;

@Entity
public class User {

	@Id
	@Column (name="idUser")
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private short idLanguage;
	
	private boolean author;
	
	private String URIName;
	
	@Transient
	private Language language;
	
	
	public short getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(short idLanguage) {
		this.idLanguage = idLanguage;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getURIName() {
		return URIName;
	}

	public void setURIName(String uRIName) {
		URIName = uRIName;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", author=" + author
				+ ", URIName=" + URIName + ", language=" + language + "]";
	}

	public boolean isAuthor() {
		return author;
	}

	public void setAuthor(boolean author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	public String getURL() {
		if(URIName!=null)
			return "/author/"+URIName;
		else return "#";		
	}
	
}
