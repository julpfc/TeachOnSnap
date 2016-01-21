package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link UserRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class UserRepositoryDBCache implements UserRepository {

	/** Database repository providing data access and modification capabilities */
	private UserRepositoryDB repoDB;
	
	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public UserRepositoryDBCache(UserRepositoryDB repoDB, CacheManager cache,
			StringManager stringManager) {
		
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

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
		//clear user name related caches
		cache.clearCache("searchUsersByName");
		cache.clearCache("searchAuthorsByName");
	}

	@Override
	public void saveLastName(int idUser, String lastname) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser, lastname);	
		//clear user name related caches
		cache.clearCache("searchUsersByName");
		cache.clearCache("searchAuthorsByName");
	}

	@Override
	public void savePassword(int idUser, String newPassword) {
		cache.updateImplCached(repoDB, null, null, idUser, newPassword);
		//clear password related caches
		cache.clearCache("isValidPassword");
	}

	@Override
	public void savePasswordTemporaryToken(int idUser, String token) {
		cache.updateImplCached(repoDB, null, null, idUser, token);
		//clear token related caches
		cache.clearCache("getIdUserFromPasswordTemporaryToken");
	}

	@Override
	public int getIdUserFromPasswordTemporaryToken(String token) {
		return (int)cache.executeImplCached(repoDB, token);
	}

	@Override
	public void deletePasswordTemporaryToken(int idUser) {
		cache.updateImplCached(repoDB, null, null, idUser);
		//clear token related caches
		cache.clearCache("getIdUserFromPasswordTemporaryToken");		
	}

	@Override
	public int createUser(String email, String firstname, String lastname, short idLanguage) {
		//clear user related caches
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
		//clear user related caches
		cache.clearCache("getAuthors");	
		cache.clearCache("searchAuthorsByEmail");
		cache.clearCache("searchAuthorsByName");
	}

	@Override
	public void saveAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		//clear admin related caches
		cache.clearCache("getAdmins");
	}

	@Override
	public void removeAdmin(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		//clear admin related caches
		cache.clearCache("getAdmins");
	}

	@Override
	public void removeAuthor(int idUser) {
		cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUser)}, 
				new String[]{"getUser"}, idUser);
		//clear author related caches
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
