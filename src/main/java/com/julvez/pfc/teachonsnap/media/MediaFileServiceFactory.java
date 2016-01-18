package com.julvez.pfc.teachonsnap.media;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.media.impl.MediaFileServiceImpl;
import com.julvez.pfc.teachonsnap.media.repository.MediaFileRepositoryFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * Factory to abstract the implementation selection for the MediaFileService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class MediaFileServiceFactory {

	/** Singleton reference to the service */
	private static MediaFileService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static MediaFileService getService(){
		if(service==null){
			service = new MediaFileServiceImpl(MediaFileRepositoryFactory.getRepository(),
												PropertyManagerFactory.getManager(),
												StringManagerFactory.getManager(),
												LogManagerFactory.getManager(),
												URLServiceFactory.getService(),
												UserServiceFactory.getService(),
												TextServiceFactory.getService(),
												NotifyServiceFactory.getService());
		}
		return service;
	}
}
