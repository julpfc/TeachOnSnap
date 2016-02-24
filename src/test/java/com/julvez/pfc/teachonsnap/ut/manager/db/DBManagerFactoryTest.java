package com.julvez.pfc.teachonsnap.ut.manager.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class DBManagerFactoryTest extends ManagerFactoryTest<DBManager> {

	@Override
	protected DBManager getTestManager() {
		return DBManagerFactory.getManager();
	}

}
