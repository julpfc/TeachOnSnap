package com.julvez.pfc.teachonsnap.manager.file;

import com.julvez.pfc.teachonsnap.manager.file.impl.FileManagerImpl;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link FileManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class FileManagerFactory {

	/** Singleton reference to the manager */
	private static FileManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static FileManager getManager(){
		if(manager==null){
			manager = new FileManagerImpl(LogManagerFactory.getManager());
		}
		return manager;
	}
}
