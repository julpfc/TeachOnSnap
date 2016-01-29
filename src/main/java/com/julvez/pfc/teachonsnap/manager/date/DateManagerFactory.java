package com.julvez.pfc.teachonsnap.manager.date;

import com.julvez.pfc.teachonsnap.manager.date.joda.DateManagerJoda;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link DateManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class DateManagerFactory {
	
	/** Singleton reference to the manager */
	private static DateManager manager;

	/**
	 * @return a singleton reference to the manager	 
	 */
	public static DateManager getManager() {
		if(manager==null){
			manager = new DateManagerJoda(LogManagerFactory.getManager());
		}
		return manager;
	}
}
