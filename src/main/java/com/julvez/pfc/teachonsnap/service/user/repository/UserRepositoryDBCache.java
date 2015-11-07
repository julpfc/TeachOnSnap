package com.julvez.pfc.teachonsnap.service.user.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.user.User;

public class UserRepositoryDBCache implements UserRepository {

	private UserRepositoryDB repoDB = new UserRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public User getUser(int idUser) {
		return (User)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	public int getIdUserFromEmail(String email) {
		return (int)cache.executeImplCached(repoDB, email);
	}

	@Override
	public int isValidPassword(int idUser, String password) {
		return (int)cache.executeImplCached(repoDB, idUser,password);
	}

	@Override
	public void saveUserLanguage(int idUser, short idLanguage) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, idLanguage);	
		
	}

	@Override
	public void saveFirstName(int idUser, String firstname) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, firstname);		
	}

	@Override
	public void saveLastName(int idUser, String lastname) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, lastname);		
	}

	@Override
	public void savePassword(int idUser, String newPassword) {
		cache.updateImplCached(repoDB, null, null, idUser, newPassword);
		cache.clearCache("isValidPassword");
	}

	@Override
	public void savePasswordTemporaryToken(int idUser, String token) {
		cache.updateImplCached(repoDB, null, null, idUser, token);
		cache.clearCache("getIdUserFromPasswordTemporaryToken");
	}

	@Override
	public int getIdUserFromPasswordTemporaryToken(String token) {
		return (int)cache.executeImplCached(repoDB, token);
	}

	@Override
	public void deletePasswordTemporaryToken(int idUser) {
		cache.updateImplCached(repoDB, null, null, idUser);
		cache.clearCache("getIdUserFromPasswordTemporaryToken");		
	}

}
