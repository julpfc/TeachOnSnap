package com.julvez.pfc.teachonsnap.repository.user.db.cache;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;
import com.julvez.pfc.teachonsnap.repository.user.db.UserRepositoryDB;

public class UserRepositoryDBCache implements UserRepository {

	private UserRepositoryDB repoDB = new UserRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	
	@Override
	public User getUser(int idUser) {
		return (User)cache.executeImplCached(repoDB, idUser);
	}

}
