package com.julvez.pfc.teachonsnap.service.page;

import com.julvez.pfc.teachonsnap.service.page.impl.PageServiceImpl;

public class PageServiceFactory {
	private static PageService service;
	
	public static PageService getService(){
		if(service==null){
			service = new PageServiceImpl();
		}
		return service;
	}
}
