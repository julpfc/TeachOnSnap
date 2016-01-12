package com.julvez.pfc.teachonsnap.url;

import com.julvez.pfc.teachonsnap.url.impl.URLServiceImpl;

/**
 * Factory to abstract the implementation selection for the URLService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class URLServiceFactory {
	private static URLService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static URLService getService(){
		if(service==null){
			service = new URLServiceImpl();
		}
		return service;
	}
}
