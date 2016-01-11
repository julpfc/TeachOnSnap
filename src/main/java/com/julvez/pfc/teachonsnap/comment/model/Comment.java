package com.julvez.pfc.teachonsnap.comment.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Entity Comment. Describes a comment wrote by an user in a lesson. 
 * It can be as a response to a previous comment (parent comment).
 * Author can edit the comment, or can be blocked/banned by an administrator.
 * 
 */
@Entity
public class Comment {

	@Id
	@Column (name="idComment")
	private int id;
	private int idUser;
	private Date date;
	private int idParentComment;
	private boolean edited;
	private boolean banned;
	
	private String body;
	
	@Transient
	private User user;
	
	/**
	 * @return Comment ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * For completition purpose only. Not to be used to modify the object. 
	 * @param id Comment identifier
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return Comment's author ID
	 */
	public int getIdUser() {
		return idUser;
	}
	
	/**
	 * For completition purpose only. Not to be used to modify the object.
	 * @param idUser Comment's auhtor ID
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * @return Last modification date for the comment
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * For completition purpose only. Not to be used to modify the object.
	 * @param date Last modification date for the comment
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * @return true if the comment is a response to a previous comment
	 */
	public boolean isResponse() {
		return idParentComment>0;
	}
	
	/**
	 * For completition purpose only. Not to be used to modify the object.	 
	 * @param idParentComment which this comment answers to
	 */
	public void setIdParentComment(int idParentComment) {
		this.idParentComment = idParentComment;
	}

	/**
	 * @return Parent comment ID which this comment answers to
	 */
	public int getIdParentComment() {
		return idParentComment;
	}

	/**
	 * @return comment's body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * Modifies the comment's body
	 * @param body new comment's body
	 */
	public void setBody(String body) {
		this.body = body;
	}
	
	/**
	 * @return comment's author
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the Comment's author object
	 * @param user Comment's author
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return true if the comment was edited
	 */
	public boolean isEdited() {
		return edited;
	}
	
	/**
	 * Sets if the comment was edited since it was created
	 * @param edited true if the comment was edited
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return true if the comment is blocked/banned by an administrator
	 */
	public boolean isBanned() {
		return banned;
	}
	/**
	 * Sets if the comment is blocked/banned by an administrator
	 * @param banned true if banned/blocked
	 */
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", idUser=" + idUser + ", date=" + date
				+ ", idParentComment=" + idParentComment + ", edited=" + edited
				+ ", body=" + body + ", user=" + user + "]";
	}
}
