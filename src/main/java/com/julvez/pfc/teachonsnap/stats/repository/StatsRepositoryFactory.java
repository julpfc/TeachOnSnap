package com.julvez.pfc.teachonsnap.stats.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link StatsRepository} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getRepository() method.
*/
public class StatsRepositoryFactory {
	
	/** Singleton reference to the repository */
	private static StatsRepository repo;
	
	/** Singleton reference to the database repository*/
	private static StatsRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static StatsRepository getRepository(){
		if(repo==null){
			repo = new StatsRepositoryDBCache(getRepositoryDB(),
											CacheManagerFactory.getManager(),
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static StatsRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new StatsRepositoryDB(DBManagerFactory.getManager(),										
										PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
