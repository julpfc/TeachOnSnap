package com.julvez.pfc.teachonsnap.service.user.repository;

import com.julvez.pfc.teachonsnap.service.user.repository.db.cache.UserRepositoryDBCache;

public class UserRepositoryFactory {

	private static UserRepository repo;
	
	public static UserRepository getRepository(){
		if(repo==null){
			repo = new UserRepositoryDBCache();
		}
		return repo;
	}
}
