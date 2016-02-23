package com.julvez.pfc.teachonsnap.ut.manager.log;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class LogManagerFactoryTest extends ManagerFactoryTest<LogManager> {

	@Override
	protected LogManager getTestManager() {
		return LogManagerFactory.getManager();
	}
}
