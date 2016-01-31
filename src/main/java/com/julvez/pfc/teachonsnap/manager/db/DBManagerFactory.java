package com.julvez.pfc.teachonsnap.manager.db;

import com.julvez.pfc.teachonsnap.manager.db.hibernate.DBManagerHibernate;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link DBManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class DBManagerFactory {

	/** Singleton reference to the manager */
	private static DBManager dbm;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static DBManager getManager(){
		if(dbm==null){
			dbm = new DBManagerHibernate(LogManagerFactory.getManager(),
										PropertyManagerFactory.getManager());
		}
		return dbm;
	}
}
