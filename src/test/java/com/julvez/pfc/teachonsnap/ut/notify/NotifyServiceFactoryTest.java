package com.julvez.pfc.teachonsnap.ut.notify;

import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class NotifyServiceFactoryTest extends ServiceFactoryTest<NotifyService> {

	@Override
	protected NotifyService getTestService() {
		return NotifyServiceFactory.getService();
	}

}
