package com.julvez.pfc.teachonsnap.model.visit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.model.user.User;

@Entity
@IdClass(UserTestRank_PK.class)
public class UserTestRank {
	@Id
	private int idLessonTest;
	@Id
	private int idUser;	
	private int idVisitUserTest;
	private int attempts;
	private int points;
	private Date date;
	
	@Transient
	private User user;

	public int getIdLessonTest() {
		return idLessonTest;
	}
	public void setIdLessonTest(int idLessonTest) {
		this.idLessonTest = idLessonTest;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdVisitUserTest() {
		return idVisitUserTest;
	}
	public void setIdVisitUserTest(int idVisitUserTest) {
		this.idVisitUserTest = idVisitUserTest;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "UserTestRank [idLessonTest=" + idLessonTest + ", idUser="
				+ idUser + ", idVisitUserTest=" + idVisitUserTest
				+ ", attempts=" + attempts + ", points=" + points + ", date="
				+ date + "]";
	}
	
		
}
