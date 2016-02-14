package com.julvez.pfc.teachonsnap.ut.manager.request;

import com.julvez.pfc.teachonsnap.manager.request.RequestManager;
import com.julvez.pfc.teachonsnap.manager.request.RequestManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class RequestManagerFactoryTest extends ManagerFactoryTest<RequestManager> {

	@Override
	protected RequestManager getTestManager() {		
		return RequestManagerFactory.getManager();
	}
}
