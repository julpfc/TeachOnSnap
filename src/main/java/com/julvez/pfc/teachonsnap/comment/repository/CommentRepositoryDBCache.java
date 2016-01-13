package com.julvez.pfc.teachonsnap.comment.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.comment.model.Comment;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link CommentRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class CommentRepositoryDBCache implements CommentRepository {

	/** Database repository providing data access and modification capabilities */
	private CommentRepositoryDB repoDB;
	
	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public CommentRepositoryDBCache(CommentRepositoryDB repoDB,	CacheManager cache, StringManager stringManager) {
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getCommentIDs(int idLesson, int firstResult) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson, firstResult);
	}

	@Override
	public Comment getComment(int idComment) {
		return (Comment)cache.executeImplCached(repoDB, idComment);
	}

	@Override
	public int createComment(int idLesson, int idUser, String commentBody) {
		return (int)cache.updateImplCached(repoDB,new String[]{stringManager.getKey(idLesson)}, 
				new String[]{"getCommentIDs"}, idLesson, idUser, commentBody);
	}

	@Override
	public void saveCommentParent(int idComment, int idParentComment) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idComment)}, 
				new String[]{"getComment"}, idComment, idParentComment);		
	}
	
	@Override
	public void saveCommentBody(int idComment, String commentBody) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idComment)}, 
				new String[]{"getComment"}, idComment, commentBody);		
	}

	@Override
	public void blockComment(int idComment, int idAdmin, String reason) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idComment)}, 
				new String[]{"getComment"}, idComment, idAdmin, reason);		
	}

	@Override
	public void unblockComment(int idComment) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idComment)}, 
				new String[]{"getComment"}, idComment);		
	}

}
