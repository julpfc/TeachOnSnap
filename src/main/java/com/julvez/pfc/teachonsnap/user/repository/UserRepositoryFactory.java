package com.julvez.pfc.teachonsnap.user.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link UserRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class UserRepositoryFactory {

	/** Singleton reference to the repository */
	private static UserRepository repo;
	
	/** Singleton reference to the database repository*/
	private static UserRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static UserRepository getRepository() {
		if(repo==null){
			repo = new UserRepositoryDBCache(getRepositoryDB(),
												CacheManagerFactory.getCacheManager(),
												StringManagerFactory.getManager());
		}
		return repo;
	}

	/**
	 * @return a singleton reference to the database repository
	 */
	private static UserRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new UserRepositoryDB(DBManagerFactory.getManager(), 
										StringManagerFactory.getManager(),
										PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
