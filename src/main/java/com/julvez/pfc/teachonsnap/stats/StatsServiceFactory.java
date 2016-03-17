package com.julvez.pfc.teachonsnap.stats;

import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.impl.StatsServiceImpl;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * Factory to abstract the implementation selection for the StatsService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class StatsServiceFactory {
	
	/** Singleton reference to the service */
	private static StatsService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static StatsService getService(){
		if(service==null){
			service = new StatsServiceImpl(StatsRepositoryFactory.getRepository(),
											UserServiceFactory.getService(),
											StringManagerFactory.getManager());
		}
		return service;
	}
}
