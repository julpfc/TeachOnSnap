package com.julvez.pfc.teachonsnap.usergroup;

import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.usergroup.impl.UserGroupServiceImpl;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepositoryFactory;

/**
 * Factory to abstract the implementation selection for the UserGroupService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class UserGroupServiceFactory {

	/** Singleton reference to the service */
	private static UserGroupService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static UserGroupService getService() {
		if(service==null){
			service = new UserGroupServiceImpl(UserGroupRepositoryFactory.getRepository(),
												UserServiceFactory.getService(),
												TagServiceFactory.getService(),
												StringManagerFactory.getManager());
		}
		return service;
	}

}
