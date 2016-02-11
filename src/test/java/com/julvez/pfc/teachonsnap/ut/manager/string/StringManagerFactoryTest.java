package com.julvez.pfc.teachonsnap.ut.manager.string;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class StringManagerFactoryTest extends ManagerFactoryTest<StringManager>{

	@Override
	protected StringManager getTestManager() {
		return StringManagerFactory.getManager();
	}	
}
