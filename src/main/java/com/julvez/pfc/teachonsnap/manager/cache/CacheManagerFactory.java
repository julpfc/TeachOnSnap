package com.julvez.pfc.teachonsnap.manager.cache;

import com.julvez.pfc.teachonsnap.manager.cache.map.CacheManagerMap;

public class CacheManagerFactory {

	private static CacheManager cache;
	
	public static CacheManager getCacheManager(){
		if(cache==null){
			cache = new CacheManagerMap();
		}
		return cache;
	}
}
