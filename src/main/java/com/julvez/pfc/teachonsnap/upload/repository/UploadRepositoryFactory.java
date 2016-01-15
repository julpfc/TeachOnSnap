package com.julvez.pfc.teachonsnap.upload.repository;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link UploadRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class UploadRepositoryFactory {

	/** Singleton reference to the repository */
	private static UploadRepository repo;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static UploadRepository getRepository(){
		if(repo==null){
			repo = new UploadRepositoryMap(LogManagerFactory.getManager());
		}
		return repo;
	}
}
