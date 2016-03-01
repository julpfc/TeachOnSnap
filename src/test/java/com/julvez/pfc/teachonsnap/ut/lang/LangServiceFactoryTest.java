package com.julvez.pfc.teachonsnap.ut.lang;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class LangServiceFactoryTest extends ServiceFactoryTest<LangService> {

	@Override
	protected LangService getTestService() {
		return LangServiceFactory.getService();
	}
}
