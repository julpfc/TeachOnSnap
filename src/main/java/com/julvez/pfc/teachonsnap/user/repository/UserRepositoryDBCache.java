package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;

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

	@Override
	public int createUser(String email, String firstname, String lastname, short idLanguage) {
		cache.clearCache("getUsers");
		cache.clearCache("searchUsersByEmail");
		cache.clearCache("searchUsersByName");
		return (int)cache.updateImplCached(repoDB, null, null, email, firstname, lastname, idLanguage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getUsers(int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> searchUsersByEmail(String searchQuery, int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> searchUsersByName(String searchQuery, int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	public void saveAuthor(int idUser, String fullName) {
		cache.updateImplCached(repoDB,new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, fullName);		
		
	}

	@Override
	public void saveAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);				
	}

	@Override
	public void removeAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);		
	}

	@Override
	public void removeAuthor(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);		
	}

	@Override
	public UserBannedInfo getUserBannedInfo(int idUser) {
		return (UserBannedInfo)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	public void blockUser(int idUser, String reason, int idAdmin) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idUser)}, 
				new String[]{"getUser","getUserBannedInfo"}, idUser, reason, idAdmin);
		
	}

	@Override
	public void unblockUser(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idUser)}, 
				new String[]{"getUser","getUserBannedInfo"}, idUser);
	}

}
