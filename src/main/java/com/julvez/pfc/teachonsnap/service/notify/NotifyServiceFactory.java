package com.julvez.pfc.teachonsnap.service.notify;

import com.julvez.pfc.teachonsnap.service.notify.mail.NotifyServiceMail;

public class NotifyServiceFactory {
	private static NotifyService service;
	
	public static NotifyService getService(){
		if(service==null){
			service = new NotifyServiceMail();
		}
		return service;
	}
}
