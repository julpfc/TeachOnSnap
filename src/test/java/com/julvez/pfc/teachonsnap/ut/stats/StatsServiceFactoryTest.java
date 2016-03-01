package com.julvez.pfc.teachonsnap.ut.stats;

import com.julvez.pfc.teachonsnap.stats.StatsService;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class StatsServiceFactoryTest extends ServiceFactoryTest<StatsService> {

	@Override
	protected StatsService getTestService() {
		return StatsServiceFactory.getService();
	}
}
