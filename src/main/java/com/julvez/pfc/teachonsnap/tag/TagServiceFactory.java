package com.julvez.pfc.teachonsnap.tag;

import com.julvez.pfc.teachonsnap.tag.impl.TagServiceImpl;

public class TagServiceFactory {

	private static TagService service;
	
	public static TagService getService(){
		if(service==null){
			service = new TagServiceImpl();
		}
		return service;
	}
}
