package com.julvez.pfc.teachonsnap.user;

import com.julvez.pfc.teachonsnap.user.impl.UserServiceImpl;


public class UserServiceFactory {

	private static UserService service;
	
	public static UserService getService(){
		if(service==null){
			service = new UserServiceImpl();
		}
		return service;
	}
}
