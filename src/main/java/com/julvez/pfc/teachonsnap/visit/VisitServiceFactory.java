package com.julvez.pfc.teachonsnap.visit;

import com.julvez.pfc.teachonsnap.visit.impl.VisitServiceImpl;

public class VisitServiceFactory {
	private static VisitService service;
	
	public static VisitService getService(){
		if(service==null){
			service = new VisitServiceImpl();
		}
		return service;
	}
}
