package com.julvez.pfc.teachonsnap.ut.manager.json;

import com.julvez.pfc.teachonsnap.manager.json.JSONManager;
import com.julvez.pfc.teachonsnap.manager.json.JSONManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class JSONManagerFactoryTest extends ManagerFactoryTest<JSONManager> {

	@Override
	protected JSONManager getTestManager() {		
		return JSONManagerFactory.getManager();
	}
}
