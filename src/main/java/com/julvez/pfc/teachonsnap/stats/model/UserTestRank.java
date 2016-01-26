package com.julvez.pfc.teachonsnap.stats.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Entity. Describes a user's best result in a lesson's test. 
 * It contains the test and user ids as primary key. Number of 
 * previous attempts user tried this test, and total points 
 * obtained at this attempt.
 */
@Entity
@IdClass(UserTestRank_PK.class)
public class UserTestRank {
	
	/** Test identifier and primary key for the entity. 
	 * Compound primary key (idLessonTest, idUser). */
	@Id
	private int idLessonTest;
	
	/** User identifier and primary key for the entity. 
	 * Compound primary key (idLessonTest, idUser). */
	@Id
	private int idUser;	
	
	/** Visit test id*/
	private int idVisitTest;
	
	/** Number of previous attempts user tried this test */
	private int attempts;
	
	/** Total points obtained at this attempt */
	private int points;
	
	/** Attempt's date */
	private Date date;
	
	/** User who performed the test*/
	@Transient
	private User user;

	/**
	 * @return Test's id
	 */
	public int getIdLessonTest() {
		return idLessonTest;
	}
	
	/**
	 * @return User's id
	 */
	public int getIdUser() {
		return idUser;
	}
	
	/**
	 * @return Visit test id
	 */
	public int getIdVisitTest() {
		return idVisitTest;
	}
	
	/**
	 * @return Number of previous attempts user tried this test
	 */
	public int getAttempts() {
		return attempts;
	}
	
	/**
	 * @return Total points obtained at this attempt
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * @return Attempt's date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return User who performed the test
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sets the User object who performed the test
	 * @param user who performed the test
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "UserTestRank [idLessonTest=" + idLessonTest + ", idUser="
				+ idUser + ", idVisitTest=" + idVisitTest
				+ ", attempts=" + attempts + ", points=" + points + ", date="
				+ date + "]";
	}		
}
