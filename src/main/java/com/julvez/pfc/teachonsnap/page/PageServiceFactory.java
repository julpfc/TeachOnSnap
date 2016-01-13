package com.julvez.pfc.teachonsnap.page;

import com.julvez.pfc.teachonsnap.page.impl.PageServiceImpl;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;

/**
 * Factory to abstract the implementation selection for the PageService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class PageServiceFactory {
	/** Singleton reference to the service */
	private static PageService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static PageService getService(){
		if(service==null){
			service = new PageServiceImpl(URLServiceFactory.getService());
		}
		return service;
	}
}
