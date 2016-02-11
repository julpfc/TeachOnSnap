package com.julvez.pfc.teachonsnap.ut.manager.date;

import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.date.DateManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class DateManagerFactoryTest extends ManagerFactoryTest<DateManager> {

	@Override
	protected DateManager getTestManager() {
		return DateManagerFactory.getManager();
	}
}
