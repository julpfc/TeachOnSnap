package com.julvez.pfc.teachonsnap.manager.request;

import com.julvez.pfc.teachonsnap.manager.request.impl.RequestManagerImpl;

public class RequestManagerFactory {

	private static RequestManager manager;
	
	public static RequestManager getManager(){
		if(manager==null){
			manager = new RequestManagerImpl();
		}
		return manager;
	}
}
