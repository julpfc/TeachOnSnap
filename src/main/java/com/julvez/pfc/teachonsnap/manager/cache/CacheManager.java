package com.julvez.pfc.teachonsnap.manager.cache;


public interface CacheManager {

	//TODO Se puede ampliar con otros metodos que permitan elegir el nombre en lugar del nombre del metodo y las keys
	/**
	 * Ejecuta y cachea
	 * 
	 * Este método sólo debe utilizarse para cachear la ejecución de un método de la implementación de un interfaz
	 * desde otra implementación diferente de esa misma interfaz.
	 * @param impl Implementación del interfaz sin caché, a la que deseamos incorporarla. 
	 * @param params Parametros de entrada del metodo de la interfaz.
	 * @return obj
	 */
	public Object executeImplCached(Object impl, Object... params);

	public Object updateImplCached(Object impl, String[] cacheKeys, String[] cacheNames, Object... params);

	public void clearCache(String cacheName);
	
	public void incCacheValue(String cacheName, String cacheKey);
}
