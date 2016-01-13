package com.julvez.pfc.teachonsnap.lang;

import com.julvez.pfc.teachonsnap.lang.impl.LangServiceImpl;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the LangService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class LangServiceFactory {

	/** Singleton reference to the service */
	private static LangService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static LangService getService(){
		if(service==null){
			service = new LangServiceImpl(LangRepositoryFactory.getRepository(),
											StringManagerFactory.getManager());
		}
		return service;
	}
}
