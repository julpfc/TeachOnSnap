package com.julvez.pfc.teachonsnap.comment.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;

/**
 * Factory to abstract the implementation selection for the {@link CommentRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class CommentRepositoryFactory {

	/** Singleton reference to the repository */
	private static CommentRepository repo;
	
	/** Singleton reference to the database repository*/
	private static CommentRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static CommentRepository getRepository(){
		if(repo==null){
			repo = new CommentRepositoryDBCache(getRepositoryDB(), 
											CacheManagerFactory.getManager(), 
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static CommentRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new CommentRepositoryDB(DBManagerFactory.getManager(), 
											StringManagerFactory.getManager(), 
											PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
