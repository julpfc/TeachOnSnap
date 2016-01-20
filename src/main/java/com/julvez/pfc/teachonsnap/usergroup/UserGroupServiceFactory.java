package com.julvez.pfc.teachonsnap.usergroup;

import com.julvez.pfc.teachonsnap.usergroup.impl.UserGroupServiceImpl;


public class UserGroupServiceFactory {

	private static UserGroupService service;
	
	public static UserGroupService getService() {
		if(service==null){
			service = new UserGroupServiceImpl();
		}
		return service;
	}

}
