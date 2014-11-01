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
	 * @return
	 */
	public Object executeImplCached(Object impl,Object... params);
}
