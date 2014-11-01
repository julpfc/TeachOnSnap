package com.julvez.pfc.teachonsnap.manager.cache;

import com.julvez.pfc.teachonsnap.manager.cache.impl.CacheManagerImpl;

public class CacheManagerFactory {

	private static CacheManager cache;
	
	public static CacheManager getCacheManager(){
		if(cache==null){
			cache = new CacheManagerImpl();
		}
		return cache;
	}
}
