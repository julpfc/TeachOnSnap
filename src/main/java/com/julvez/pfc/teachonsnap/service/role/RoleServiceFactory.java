package com.julvez.pfc.teachonsnap.service.role;


public class RoleServiceFactory {

	private static RoleService service;
	
	public static RoleService getService(){
		if(service==null){
			service = new RoleServiceImpl();
		}
		return service;
	}
}
