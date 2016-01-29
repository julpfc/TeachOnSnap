package com.julvez.pfc.teachonsnap.usergroup.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link UserGroupRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class UserGroupRepositoryFactory {
	
	/** Singleton reference to the repository */
	private static UserGroupRepository repo;
	
	/** Singleton reference to the database repository*/
	private static UserGroupRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static UserGroupRepository getRepository() {
		if(repo==null){
			repo = new UserGroupRepositoryDBCache(getRepositoryDB(),
												CacheManagerFactory.getManager(),
												StringManagerFactory.getManager());
		}
		return repo;
	}

	/**
	 * @return a singleton reference to the database repository
	 */
	private static UserGroupRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new UserGroupRepositoryDB(DBManagerFactory.getManager(), 
											PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
