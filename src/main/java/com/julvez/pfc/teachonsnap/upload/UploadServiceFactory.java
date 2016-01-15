package com.julvez.pfc.teachonsnap.upload;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.upload.impl.UploadServiceImpl;
import com.julvez.pfc.teachonsnap.upload.repository.UploadRepositoryFactory;

/**
 * Factory to abstract the implementation selection for the UploadService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class UploadServiceFactory {

	/** Singleton reference to the service */
	private static UploadService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static UploadService getService(){
		if(service==null){
			service = new UploadServiceImpl(UploadRepositoryFactory.getRepository(),
											LogManagerFactory.getManager());
		}
		return service;
	}
}
