package com.julvez.pfc.teachonsnap.link.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link LinkRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class LinkRepositoryFactory {

	/** Singleton reference to the repository */
	private static LinkRepository repo;
	
	/** Singleton reference to the database repository*/
	private static LinkRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static LinkRepository getRepository(){
		if(repo==null){
			repo = new LinkRepositoryDBCache(getRepositoryDB(),
											CacheManagerFactory.getManager(),
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static LinkRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new LinkRepositoryDB(DBManagerFactory.getManager());
		}
		return repoDB;
	}
}
