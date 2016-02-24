package com.julvez.pfc.teachonsnap.ut.manager.mail;

import com.julvez.pfc.teachonsnap.manager.mail.MailManager;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class MailManagerFactoryTest extends ManagerFactoryTest<MailManager> {

	@Override
	protected MailManager getTestManager() {
		return MailManagerFactory.getManager();
	}

	
}
