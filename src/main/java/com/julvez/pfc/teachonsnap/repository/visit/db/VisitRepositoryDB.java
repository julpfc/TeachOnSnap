package com.julvez.pfc.teachonsnap.repository.visit.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepository;

public class VisitRepositoryDB implements VisitRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public int createVisit(String ip) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_VISIT_CREATE", ip);
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		int affectedRows = -1;
		
		affectedRows = dbm.updateQuery("SQL_VISIT_SAVE_USER", idVisit, idUser);
		
		return affectedRows>-1;
	}

	
}
