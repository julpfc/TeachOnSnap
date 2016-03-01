package com.julvez.pfc.teachonsnap.ut.link;

import com.julvez.pfc.teachonsnap.link.LinkService;
import com.julvez.pfc.teachonsnap.link.LinkServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class LinkServiceFactoryTest extends ServiceFactoryTest<LinkService> {

	@Override
	protected LinkService getTestService() {
		return LinkServiceFactory.getService();
	}
}
