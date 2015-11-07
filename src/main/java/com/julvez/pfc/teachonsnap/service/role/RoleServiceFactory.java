package com.julvez.pfc.teachonsnap.service.role;

import com.julvez.pfc.teachonsnap.service.role.impl.RoleServiceImpl;


public class RoleServiceFactory {

	private static RoleService service;
	
	public static RoleService getService(){
		if(service==null){
			service = new RoleServiceImpl();
		}
		return service;
	}
}
