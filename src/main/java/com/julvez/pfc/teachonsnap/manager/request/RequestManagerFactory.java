package com.julvez.pfc.teachonsnap.manager.request;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.request.impl.RequestManagerImpl;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link RequestManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class RequestManagerFactory {

	/** Singleton reference to the manager */
	private static RequestManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static RequestManager getManager(){
		if(manager==null){
			manager = new RequestManagerImpl(StringManagerFactory.getManager(),
											LogManagerFactory.getManager());
		}
		return manager;
	}
}
