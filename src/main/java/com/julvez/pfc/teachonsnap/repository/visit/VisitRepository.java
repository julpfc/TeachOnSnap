package com.julvez.pfc.teachonsnap.repository.visit;

public interface VisitRepository {

	public int createVisit(String ip);

	public boolean saveUser(int idVisit, int idUser);

}
