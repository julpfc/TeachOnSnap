package com.julvez.pfc.teachonsnap.lang.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link LangRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class LangRepositoryFactory {

	/** Singleton reference to the repository */
	private static LangRepository repo;

	/** Singleton reference to the database repository */
	private static LangRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDBCache(getRepositoryDB(),
											CacheManagerFactory.getCacheManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static LangRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new LangRepositoryDB(DBManagerFactory.getDBManager());
		}
		return repoDB;
	}
}
