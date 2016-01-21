package com.julvez.pfc.teachonsnap.user;

import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.date.DateManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.impl.UserServiceImpl;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryFactory;

/**
 * Factory to abstract the implementation selection for the UserService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class UserServiceFactory {

	/** Singleton reference to the service */
	private static UserService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static UserService getService(){
		if(service==null){
			service = new UserServiceImpl(UserRepositoryFactory.getRepository(),
										LangServiceFactory.getService(),
										URLServiceFactory.getService(),
										NotifyServiceFactory.getService(),
										TextServiceFactory.getService(),
										StringManagerFactory.getManager(),
										DateManagerFactory.getManager(),
										PropertyManagerFactory.getManager());
		}
		return service;
	}
}
