package com.julvez.pfc.teachonsnap.lang.repository;

/**
 * Factory to abstract the implementation selection for the {@link LangRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class LangRepositoryFactory {

	private static LangRepository repo;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static LangRepository getRepository(){
		if(repo==null){
			repo = new LangRepositoryDBCache();
		}
		return repo;
	}
}
