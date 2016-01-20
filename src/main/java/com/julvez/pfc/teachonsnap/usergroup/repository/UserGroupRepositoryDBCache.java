package com.julvez.pfc.teachonsnap.usergroup.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link UserGroupRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class UserGroupRepositoryDBCache implements UserGroupRepository {
	
	/** Database repository providing data access and modification capabilities */
	private UserGroupRepositoryDB repoDB;
	
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
	public UserGroupRepositoryDBCache(UserGroupRepositoryDB repoDB,
			CacheManager cache, StringManager stringManager) {
		
		if(repoDB == null || stringManager == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.repoDB = repoDB;
		this.cache = cache;
		this.stringManager = stringManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getGroups(int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, firstResult);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> searchGroupsByName(String searchQuery, int firstResult) {
		return (List<Short>)cache.executeImplCached(repoDB, searchQuery, firstResult);
	}

	@Override
	public UserGroup getGroup(short idUserGroup) {
		return (UserGroup)cache.executeImplCached(repoDB, idUserGroup);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getGroupMembers(short idUserGroup) {
		return (List<Short>)cache.executeImplCached(repoDB, idUserGroup);
	}

	@Override
	public short createGroup(String groupName) {
		short id = (short)cache.updateImplCached(repoDB, null, null, groupName);
		//clear caches related
		if(id > 0){
			cache.clearCache("searchGroupsByName");
			cache.clearCache("getGroups");
		}
				
		return id;
	}

	@Override
	public boolean addUser(short idUserGroup, int idUser) {		
		return (boolean)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idUser)}, 
				new String[]{"getGroupMembers","getGroupsFromUser"}, idUserGroup, idUser);
	}

	@Override
	public boolean saveGroupName(short idUserGroup, String groupName) {
		boolean ret = (boolean)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(idUserGroup)}, 
				new String[]{"getGroup"}, idUserGroup, groupName);
		//clear caches related
		if(ret){
			cache.clearCache("searchGroupsByName");
		}
		
		return ret;
	}

	@Override
	public boolean removeUser(short idUserGroup, int idUser) {
		return (boolean)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idUser)}, 
				new String[]{"getGroupMembers","getGroupsFromUser"}, idUserGroup, idUser);
	}

	@Override
	public boolean removeGroup(short idUserGroup) {
		boolean ret = (boolean)cache.updateImplCached(repoDB, 
				new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idUserGroup)}, 
				new String[]{"getGroup","getGroupMembers"}, idUserGroup);
		//clear caches related
		if(ret){
			cache.clearCache("searchGroupsByName");
			cache.clearCache("getGroups");
			cache.clearCache("getGroupsFromUser");
		}
		
		return ret;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getGroupsFromUser(int idUser) {
		return (List<Short>)cache.executeImplCached(repoDB, idUser);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Short> getAuthorFollowings(short idUserGroup) {
		return (List<Short>)cache.executeImplCached(repoDB, idUserGroup);
	}

	@Override
	public boolean followAuthor(short idUserGroup, int idAuthor) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idAuthor)}, 
				new String[]{"getAuthorFollowings","getAuthorFollowers"}, idUserGroup, idAuthor);
	}

	@Override
	public boolean unfollowAuthor(short idUserGroup, int idAuthor) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idAuthor)}, 
				new String[]{"getAuthorFollowings","getAuthorFollowers"}, idUserGroup, idAuthor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getTagFollowings(short idUserGroup) {
		return (List<Integer>)cache.executeImplCached(repoDB, idUserGroup);
	}

	@Override
	public boolean followTag(short idUserGroup, int idTag) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idTag)}, 
				new String[]{"getTagFollowings","getTagFollowers"}, idUserGroup, idTag);
	}

	@Override
	public boolean unfollowTag(short idUserGroup, int idTag) {
		return (boolean)cache.updateImplCached(repoDB, new String[]{stringManager.getKey(idUserGroup),stringManager.getKey(idTag)}, 
				new String[]{"getTagFollowings","getTagFollowers"}, idUserGroup, idTag);
	}

}
