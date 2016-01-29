package com.julvez.pfc.teachonsnap.manager.cache;

/** Cache manager providing access/modification capabilities to the cache system */
public interface CacheManager {

	/**
	 * Tries to get the result from the cache, otherwise executes the overriden method of 
	 * an implementation from another implementation of the same interface. It caches the
	 * result object in a cache named with caller's method's name. 
	 * This method can only be called from an overriden method of an interface.	 
	 * @param impl Interface's implementation without cache. 
	 * @param params Method params.
	 * @return object result
	 */
	public Object executeImplCached(Object impl, Object... params);


	/**
	 * Executes the overriden method of an implementation from another implementation of 
	 * the same interface. It also clears the specified keys in the specified caches.  
	 * This method can only be called from an overriden method of an interface.	 
	 * @param impl Interface's implementation without cache. 
	 * @param cacheKeys List of keys to be cleared
	 * @param cacheNames Caches where the keys will be cleared
	 * @param params Method params.
	 * @return object result
	 */
	public Object updateImplCached(Object impl, String[] cacheKeys, String[] cacheNames, Object... params);

	/**
	 * Clears the specified cache
	 * @param cacheName Name of the cache
	 */
	public void clearCache(String cacheName);
	
	/**
	 * Increments the cache value by 1
	 * @param cacheName Cache name
	 * @param cacheKey Cache key
	 */
	public void incCacheValue(String cacheName, String cacheKey);
}
