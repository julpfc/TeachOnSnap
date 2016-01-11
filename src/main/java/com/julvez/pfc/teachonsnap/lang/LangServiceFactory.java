package com.julvez.pfc.teachonsnap.lang;

import com.julvez.pfc.teachonsnap.lang.impl.LangServiceImpl;

/**
 * Factory to abstract the implementation selection for the LangService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class LangServiceFactory {

	private static LangService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static LangService getService(){
		if(service==null){
			service = new LangServiceImpl();
		}
		return service;
	}
}
