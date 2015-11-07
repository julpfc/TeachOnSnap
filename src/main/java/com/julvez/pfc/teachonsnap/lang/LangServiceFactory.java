package com.julvez.pfc.teachonsnap.lang;

import com.julvez.pfc.teachonsnap.lang.impl.LangServiceImpl;


public class LangServiceFactory {

	private static LangService service;
	
	public static LangService getService(){
		if(service==null){
			service = new LangServiceImpl();
		}
		return service;
	}
}
