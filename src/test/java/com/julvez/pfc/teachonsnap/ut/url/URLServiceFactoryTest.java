package com.julvez.pfc.teachonsnap.ut.url;

import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class URLServiceFactoryTest extends ServiceFactoryTest<URLService> {

	@Override
	protected URLService getTestService() {
		return URLServiceFactory.getService();
	}

}
