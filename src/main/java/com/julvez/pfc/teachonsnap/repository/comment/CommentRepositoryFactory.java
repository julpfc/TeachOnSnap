package com.julvez.pfc.teachonsnap.repository.comment;

import com.julvez.pfc.teachonsnap.repository.comment.db.cache.CommentRepositoryDBCache;

public class CommentRepositoryFactory {
private static CommentRepository repo;
	
	public static CommentRepository getRepository(){
		if(repo==null){
			repo = new CommentRepositoryDBCache();
		}
		return repo;
	}
}
