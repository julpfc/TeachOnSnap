package com.julvez.pfc.teachonsnap.stats.model;

import java.io.Serializable;

/**
 * Primary key for the {@link UserTestRank} class. Overrides
 * hashCode() and equals() for persistence.
 */
public class UserTestRank_PK implements Serializable{

	private static final long serialVersionUID = 8950267547725284709L;

	/** Test's id */
	int idLessonTest;
	/** User's id */
	int idUser;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLessonTest;
		result = prime * result + idUser;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTestRank_PK other = (UserTestRank_PK) obj;
		if (idLessonTest != other.idLessonTest)
			return false;
		if (idUser != other.idUser)
			return false;
		return true;
	}	
}
