package com.julvez.pfc.teachonsnap.repository.comment.db.cache;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.comment.Comment;
import com.julvez.pfc.teachonsnap.repository.comment.CommentRepository;
import com.julvez.pfc.teachonsnap.repository.comment.db.CommentRepositoryDB;

public class CommentRepositoryDBCache implements CommentRepository {

	private CommentRepositoryDB repoDB = new CommentRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getCommentIDs(int idLesson) {
		return (List<Integer>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	public Comment getComment(int idComment) {
		return (Comment)cache.executeImplCached(repoDB, idComment);
	}

}
