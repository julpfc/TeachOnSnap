package com.julvez.pfc.teachonsnap.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class UserBannedInfo {

	@Id
	@Column (name="idUser")
	private int id;
	private String reason;
	private Date date;
	private int idAdmin;
	
	@Transient
	private User admin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserBannedInfo [id=" + id + ", reason=" + reason + ", date=" + date
				+ ", idAdmin=" + idAdmin + "]";
	}
	
}
	