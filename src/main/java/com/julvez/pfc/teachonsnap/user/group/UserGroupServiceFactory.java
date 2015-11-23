package com.julvez.pfc.teachonsnap.user.group;

import com.julvez.pfc.teachonsnap.user.group.impl.UserGroupServiceImpl;


public class UserGroupServiceFactory {

	private static UserGroupService service;
	
	public static UserGroupService getService() {
		if(service==null){
			service = new UserGroupServiceImpl();
		}
		return service;
	}

}
