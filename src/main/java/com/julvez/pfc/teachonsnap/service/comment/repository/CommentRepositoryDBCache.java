package com.julvez.pfc.teachonsnap.service.comment.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.comment.Comment;

public class CommentRepositoryDBCache implements CommentRepository {

	private CommentRepositoryDB repoDB = new CommentRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
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
