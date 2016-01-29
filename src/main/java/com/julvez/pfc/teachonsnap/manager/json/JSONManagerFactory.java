package com.julvez.pfc.teachonsnap.manager.json;

import com.julvez.pfc.teachonsnap.manager.json.jackson.JSONManagerJackson;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link JSONManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class JSONManagerFactory {

	/** Singleton reference to the manager */
	private static JSONManager manager;

	/**
	 * @return a singleton reference to the manager	 
	 */
	public static JSONManager getManager() {
		if(manager==null){
			manager = new JSONManagerJackson(LogManagerFactory.getManager());
		}
		return manager;
	}
	
	
	
}
