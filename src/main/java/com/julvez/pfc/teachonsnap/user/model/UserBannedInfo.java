package com.julvez.pfc.teachonsnap.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Entity. Describes an user ban related information.  
 * It contains the reason and date of the ban, and the administrator who
 * blocked the user. 
 */
@Entity
public class UserBannedInfo {

	/**	User identifier and primary key for the entity */
	@Id
	@Column (name="idUser")
	private int id;
		
	/** Reason specified by the adminsitrator when banned the user */
	private String reason;
	
	/** Ban's date */
	private Date date;
	
	/** Administrator's id*/
	private int idAdmin;
	
	/** Administrator who banned the user*/
	@Transient
	private User admin;

	/**
	 * @return User's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return Adminsitrator's reason to ban the user
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return Ban's date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return Administrator's id, who banned the user
	 */
	public int getIdAdmin() {
		return idAdmin;
	}

	/**
	 * @return Administrator who banned the user
	 */
	public User getAdmin() {
		return admin;
	}

	/**
	 * Sets the administrator who banned the user
	 * @param admin who banned the user
	 */
	public void setAdmin(User admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserBannedInfo [id=" + id + ", reason=" + reason + ", date=" + date
				+ ", idAdmin=" + idAdmin + "]";
	}
}
	