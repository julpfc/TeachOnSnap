package com.julvez.pfc.teachonsnap.stats;

import com.julvez.pfc.teachonsnap.stats.impl.StatsServiceImpl;

public class StatsServiceFactory {
	private static StatsService service;
	
	public static StatsService getService(){
		if(service==null){
			service = new StatsServiceImpl();
		}
		return service;
	}
}
