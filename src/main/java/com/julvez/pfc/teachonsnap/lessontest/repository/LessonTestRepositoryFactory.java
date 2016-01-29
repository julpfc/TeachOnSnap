package com.julvez.pfc.teachonsnap.lessontest.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;


/**
 * Factory to abstract the implementation selection for the {@link LessonTestRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class LessonTestRepositoryFactory {

	/** Singleton reference to the repository */
	private static LessonTestRepository repo;
	
	/** Singleton reference to the database repository*/
	private static LessonTestRepositoryDB repoDB;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static LessonTestRepository getRepository(){
		if(repo==null){
			repo = new LessonTestRepositoryDBCache(getRepositoryDB(), 
											CacheManagerFactory.getCacheManager(), 
											StringManagerFactory.getManager());
		}
		return repo;
	}
	
	/**
	 * @return a singleton reference to the database repository
	 */
	private static LessonTestRepositoryDB getRepositoryDB(){
		if(repoDB==null){
			repoDB = new LessonTestRepositoryDB(DBManagerFactory.getManager());
		}
		return repoDB;
	}
}
