package com.julvez.pfc.teachonsnap.ut.manager.date.joda;

import org.mockito.Mock;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.date.joda.DateManagerJoda;
import com.julvez.pfc.teachonsnap.manager.log.LogManager;
import com.julvez.pfc.teachonsnap.ut.manager.date.DateManagerTest;

public class DateManagerJodaTest extends DateManagerTest {

	@Mock
	private LogManager logger;
	
	@Override
	protected DateManager getManager() {		
		return new DateManagerJoda(logger);
	}

}
