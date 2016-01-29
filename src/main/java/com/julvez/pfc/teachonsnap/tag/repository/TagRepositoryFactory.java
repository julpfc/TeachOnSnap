package com.julvez.pfc.teachonsnap.tag.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link TagRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class TagRepositoryFactory {

	/** Singleton reference to the repository */
	private static TagRepository repo;	
	
	/** Singleton reference to the database repository*/
	private static TagRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static TagRepository getRepository(){
		if(repo==null){
			repo = new TagRepositoryDBCache(getRepositoryDB(),
											CacheManagerFactory.getManager(),
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static TagRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new TagRepositoryDB(DBManagerFactory.getManager(),										
										PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
