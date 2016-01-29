package com.julvez.pfc.teachonsnap.manager.cache;

import com.julvez.pfc.teachonsnap.manager.cache.map.CacheManagerMap;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link CacheManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class CacheManagerFactory {

	/** Singleton reference to the manager */
	private static CacheManager cache;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static CacheManager getManager(){
		if(cache==null){
			cache = new CacheManagerMap(StringManagerFactory.getManager(),
										LogManagerFactory.getManager());
		}
		return cache;
	}
}
