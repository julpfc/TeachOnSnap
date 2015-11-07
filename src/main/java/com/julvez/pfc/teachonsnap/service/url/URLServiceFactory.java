package com.julvez.pfc.teachonsnap.service.url;

import com.julvez.pfc.teachonsnap.service.url.impl.URLServiceImpl;

public class URLServiceFactory {
private static URLService service;
	
	public static URLService getService(){
		if(service==null){
			service = new URLServiceImpl();
		}
		return service;
	}
}
