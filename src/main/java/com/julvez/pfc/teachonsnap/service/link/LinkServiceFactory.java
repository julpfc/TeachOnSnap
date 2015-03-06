package com.julvez.pfc.teachonsnap.service.link;

import com.julvez.pfc.teachonsnap.service.link.impl.LinkServiceImpl;

public class LinkServiceFactory {

	private static LinkService service;
	
	public static LinkService getService(){
		if(service==null){
			service = new LinkServiceImpl();
		}
		return service;
	}
}
