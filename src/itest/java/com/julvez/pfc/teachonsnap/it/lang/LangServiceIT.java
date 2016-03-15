package com.julvez.pfc.teachonsnap.it.lang;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.ut.lang.LangServiceTest;

public class LangServiceIT extends LangServiceTest {

	@Override
	protected LangService getService() {	
		return LangServiceFactory.getService();
	}
}
