package com.julvez.pfc.teachonsnap.service.comment.repository;


public class CommentRepositoryFactory {

	private static CommentRepository repo;
	
	public static CommentRepository getRepository(){
		if(repo==null){
			repo = new CommentRepositoryDBCache();
		}
		return repo;
	}
}
