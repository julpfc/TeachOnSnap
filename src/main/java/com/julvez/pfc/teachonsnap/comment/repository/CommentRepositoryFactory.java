package com.julvez.pfc.teachonsnap.comment.repository;

/**
 * Factory to abstract the implementation selection for the {@link CommentRepository} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getRepository() method.
 */
public class CommentRepositoryFactory {

	private static CommentRepository repo;
	
	/**
	 * @return a singleton reference to the repository 
	 */
	public static CommentRepository getRepository(){
		if(repo==null){
			repo = new CommentRepositoryDBCache();
		}
		return repo;
	}
}
