package com.julvez.pfc.teachonsnap.service.tag;

import com.julvez.pfc.teachonsnap.service.tag.impl.TagServiceImpl;

public class TagServiceFactory {

	private static TagService service;
	
	public static TagService getService(){
		if(service==null){
			service = new TagServiceImpl();
		}
		return service;
	}
}
