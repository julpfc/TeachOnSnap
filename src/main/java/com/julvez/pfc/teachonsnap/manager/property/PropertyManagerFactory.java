package com.julvez.pfc.teachonsnap.manager.property;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.impl.PropertyManagerImpl;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link PropertyManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class PropertyManagerFactory {
	
	/** Singleton reference to the manager */
	private static PropertyManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static PropertyManager getManager(){
		if(manager==null){
			manager = new PropertyManagerImpl(StringManagerFactory.getManager(),
											LogManagerFactory.getManager());
		}
		return manager;
	}
}
