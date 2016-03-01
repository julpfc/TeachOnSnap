package com.julvez.pfc.teachonsnap.ut.page;

import com.julvez.pfc.teachonsnap.page.PageService;
import com.julvez.pfc.teachonsnap.page.PageServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class PageServiceFactoryTest extends ServiceFactoryTest<PageService> {

	@Override
	protected PageService getTestService() {
		return PageServiceFactory.getService();
	}

}
