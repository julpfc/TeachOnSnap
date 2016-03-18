package com.julvez.pfc.teachonsnap.it.url;

import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.ut.url.URLServiceTest;

public class URLServiceIT extends URLServiceTest {

	@Override
	protected URLService getService() {		
		return URLServiceFactory.getService();
	}
}
