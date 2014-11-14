package com.julvez.pfc.teachonsnap.service.user;

import com.julvez.pfc.teachonsnap.service.user.impl.UserServiceImpl;


public class UserServiceFactory {

	private static UserService service;
	
	public static UserService getService(){
		if(service==null){
			service = new UserServiceImpl();
		}
		return service;
	}
}
