package com.julvez.pfc.teachonsnap.ut.text;

import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class TextServiceFactoryTest extends ServiceFactoryTest<TextService> {

	@Override
	protected TextService getTestService() {	
		return TextServiceFactory.getService();
	}
}
