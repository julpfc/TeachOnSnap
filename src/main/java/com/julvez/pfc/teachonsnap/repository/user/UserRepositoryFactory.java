package com.julvez.pfc.teachonsnap.repository.user;

import com.julvez.pfc.teachonsnap.repository.user.db.cache.UserRepositoryDBCache;

public class UserRepositoryFactory {

	private static UserRepository repo;
	
	public static UserRepository getRepository(){
		if(repo==null){
			repo = new UserRepositoryDBCache();
		}
		return repo;
	}
}
