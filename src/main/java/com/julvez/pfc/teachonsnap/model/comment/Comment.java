package com.julvez.pfc.teachonsnap.model.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.model.user.User;

@Entity
public class Comment {

	@Id
	@Column (name="idComment")
	private int id;
	private int idUser;
	private Date date;
	private int idParentComment;
	
	private String body;
	
	@Transient
	private User user;
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isResponse() {
		return idParentComment>0;
	}
	public void setIdParentComment(int idParentComment) {
		this.idParentComment = idParentComment;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", idUser=" + idUser + ", date=" + date
				+ ", idParentComment=" + idParentComment + ", body=" + body
				+ ", user=" + user + "]";
	}
	
	
}
