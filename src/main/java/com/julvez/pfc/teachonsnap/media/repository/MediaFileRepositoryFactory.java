package com.julvez.pfc.teachonsnap.media.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.file.FileManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link MediaFileRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class MediaFileRepositoryFactory {
	/** Singleton reference to the repository*/
	private static MediaFileRepository repo;
	
	/** Singleton reference to the database repository*/
	private static MediaFileRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static MediaFileRepository getRepository(){
		if(repo==null){
			repo = new MediaFileRepositoryDBCache(getRepositoryDB(),
													CacheManagerFactory.getManager(),
													StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static MediaFileRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new MediaFileRepositoryDB(DBManagerFactory.getManager(),
												FileManagerFactory.getManager(),
												PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
