package com.julvez.pfc.teachonsnap.link;

import com.julvez.pfc.teachonsnap.link.impl.LinkServiceImpl;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the LinkService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class LinkServiceFactory {

	/** Singleton reference to the service */
	private static LinkService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static LinkService getService(){
		if(service==null){
			service = new LinkServiceImpl(LinkRepositoryFactory.getRepository(),
										LogManagerFactory.getManager(),
										StringManagerFactory.getManager());
		}
		return service;
	}
}
