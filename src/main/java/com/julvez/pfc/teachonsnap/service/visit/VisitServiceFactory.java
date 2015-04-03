package com.julvez.pfc.teachonsnap.service.visit;

import com.julvez.pfc.teachonsnap.service.visit.impl.VisitServiceImpl;

public class VisitServiceFactory {
	private static VisitService service;
	
	public static VisitService getService(){
		if(service==null){
			service = new VisitServiceImpl();
		}
		return service;
	}
}
