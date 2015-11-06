package com.julvez.pfc.teachonsnap.service.text;

import com.julvez.pfc.teachonsnap.service.text.impl.TextServiceImpl;

public class TextServiceFactory {
	private static TextService service;
	
	public static TextService getService(){
		if(service==null){
			service = new TextServiceImpl();
		}
		return service;
	}
}
