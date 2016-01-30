package com.julvez.pfc.teachonsnap.manager.log;

import com.julvez.pfc.teachonsnap.manager.log.log4j2.LogManagerLog4j2;

/**
* Factory to abstract the implementation selection for the {@link LogManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class LogManagerFactory {

	/** Singleton reference to the manager */
	private static LogManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static LogManager getManager(){
		if(manager==null){
			manager = new LogManagerLog4j2();
		}
		return manager;
	}
}

