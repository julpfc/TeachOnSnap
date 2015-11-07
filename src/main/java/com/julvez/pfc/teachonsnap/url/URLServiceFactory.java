package com.julvez.pfc.teachonsnap.url;

import com.julvez.pfc.teachonsnap.url.impl.URLServiceImpl;

public class URLServiceFactory {
private static URLService service;
	
	public static URLService getService(){
		if(service==null){
			service = new URLServiceImpl();
		}
		return service;
	}
}
