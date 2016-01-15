package com.julvez.pfc.teachonsnap.notify;

import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.manager.mail.MailManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.mail.NotifyServiceMail;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;

/**
 * Factory to abstract the implementation selection for the NotifyService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class NotifyServiceFactory {
	
	/** Singleton reference to the service */
	private static NotifyService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static NotifyService getService(){
		if(service==null){
			service = new NotifyServiceMail(MailManagerFactory.getManager(),
											StringManagerFactory.getManager(),
											URLServiceFactory.getService(),
											TextServiceFactory.getService(),
											LangServiceFactory.getService());
		}
		return service;
	}
}
