package com.julvez.pfc.teachonsnap.ut.manager.cache;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.ut.manager.ManagerFactoryTest;

public class CacheManagerFactoryTest extends ManagerFactoryTest<CacheManager> {

	@Override
	protected CacheManager getTestManager() {		
		return CacheManagerFactory.getManager();
	}
}
