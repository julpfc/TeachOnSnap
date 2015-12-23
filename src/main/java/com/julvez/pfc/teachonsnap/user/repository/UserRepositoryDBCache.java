package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;
import java.util.Map;

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
		cache.clearCache("searchUsersByName");
		cache.clearCache("searchAuthorsByName");
	}

	@Override
	public void saveLastName(int idUser, String lastname) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, lastname);	
		cache.clearCache("searchUsersByName");
		cache.clearCache("searchAuthorsByName");
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
		cache.clearCache("getAuthors");
		cache.clearCache("searchUsersByEmail");
		cache.clearCache("searchUsersByName");
		cache.clearCache("searchAuthorsByName");
		cache.clearCache("searchAuthorsByEmail");
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
		cache.clearCache("getAuthors");	
		cache.clearCache("searchAuthorsByEmail");
		cache.clearCache("searchAuthorsByName");
	}

	@Override
	public void saveAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		cache.clearCache("getAdmins");
	}

	@Override
	public void removeAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		cache.clearCache("getAdmins");
	}

	@Override
	public void removeAuthor(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		cache.clearCache("getAuthors");
		cache.clearCache("searchAuthorsByEmail");
		cache.clearCache("searchAuthorsByName");
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

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getAuthors(int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> searchAuthorsByEmail(String searchQuery, int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> searchAuthorsByName(String searchQuery, int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getAuthorFollowed(int idUser) {
		return (Map<String, String>) cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getLessonFollowed(int idUser) {
		return (Map<String, String>) cache.executeImplCached(repoDB, idUser);
	}

	@Override
	public boolean unfollowAuthor(int idUser, int idAuthor) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idAuthor)}, 
				new String[]{"getAuthorFollowed","getAuthorFollowers"}, idUser, idAuthor);
	}

	@Override
	public boolean followAuthor(int idUser, int idAuthor) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idAuthor)}, 
				new String[]{"getAuthorFollowed","getAuthorFollowers"}, idUser, idAuthor);
	}

	@Override
	public boolean followLesson(int idUser, int idLesson) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idLesson)}, 
				new String[]{"getLessonFollowed","getLessonFollowers"}, idUser, idLesson);
	}

	@Override
	public boolean unfollowLesson(int idUser, int idLesson) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser),stringManager.getKey(idLesson)}, 
				new String[]{"getLessonFollowed","getLessonFollowers"}, idUser, idLesson);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getAuthorFollowers(int idUser) {
		return (List<Short>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getLessonFollowers(int idLesson) {
		return (List<Short>)cache.executeImplCached(repoDB, idLesson);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getTagFollowers(int idTag) {
		return (List<Short>)cache.executeImplCached(repoDB, idTag);
	}

	@Override
	public void saveExtraInfo(int idUser, String extraInfo) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)},
				new String[]{"getUser"}, idUser, extraInfo);		
	}

	@Override
	public void removeExtraInfo(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getAdmins() {
		return (List<Short>) cache.executeImplCached(repoDB);
	}

}
