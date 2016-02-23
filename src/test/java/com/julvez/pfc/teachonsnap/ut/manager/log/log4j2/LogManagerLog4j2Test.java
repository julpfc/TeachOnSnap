package com.julvez.pfc.teachonsnap.ut.manager.log.log4j2;

import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.manager.log.log4j2.LogManagerLog4j2;
import com.julvez.pfc.teachonsnap.ut.manager.log.LogManagerTest;

public class LogManagerLog4j2Test extends LogManagerTest {

	@Override
	protected LogManager getManager() {
		return new LogManagerLog4j2();
	}	
}
