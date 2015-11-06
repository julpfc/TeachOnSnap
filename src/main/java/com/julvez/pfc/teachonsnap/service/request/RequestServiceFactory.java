package com.julvez.pfc.teachonsnap.service.request;

import com.julvez.pfc.teachonsnap.service.request.impl.RequestServiceImpl;

public class RequestServiceFactory {
private static RequestService service;
	
	public static RequestService getService(){
		if(service==null){
			service = new RequestServiceImpl();
		}
		return service;
	}
}
