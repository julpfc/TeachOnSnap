package com.julvez.pfc.teachonsnap.manager.string;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.impl.StringManagerImpl;

/**
 * Factory to abstract the implementation selection for the {@link StringManager} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getManager() method.
 */
public class StringManagerFactory {

	/** Singleton reference to the manager */
	private static StringManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static StringManager getManager(){
		if(manager==null){
			manager = new StringManagerImpl(LogManagerFactory.getManager());
		}
		return manager;
	}
}
