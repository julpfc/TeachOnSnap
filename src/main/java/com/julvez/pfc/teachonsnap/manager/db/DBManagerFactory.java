package com.julvez.pfc.teachonsnap.manager.db;

import com.julvez.pfc.teachonsnap.manager.db.hibernate.DBManagerHibernate;

public class DBManagerFactory {

	private static DBManager dbm;
	
	public static DBManager getDBManager(){
		if(dbm==null){
			dbm = new DBManagerHibernate();
		}
		return dbm;
	}
}
