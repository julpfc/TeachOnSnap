package com.julvez.pfc.teachonsnap.lesson.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;


/**
 * Factory to abstract the implementation selection for the {@link LessonRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class LessonRepositoryFactory {

	/** Singleton reference to the repository */
	private static LessonRepository repo;
	
	/** Singleton reference to the database repository*/
	private static LessonRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static LessonRepository getRepository(){
		if(repo==null){
			repo = new LessonRepositoryDBCache(getRepositoryDB(), 
											CacheManagerFactory.getManager(), 
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static LessonRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new LessonRepositoryDB(DBManagerFactory.getManager(), 
											PropertyManagerFactory.getManager());
		}
		return repoDB;
	}
}
