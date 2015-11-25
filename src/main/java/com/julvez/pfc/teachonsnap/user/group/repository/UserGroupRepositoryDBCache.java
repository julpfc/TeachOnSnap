package com.julvez.pfc.teachonsnap.user.group.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;


public class UserGroupRepositoryDBCache implements UserGroupRepository {
	
	private UserGroupRepositoryDB repoDB = new UserGroupRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private StringManager stringManager = StringManagerFactory.getManager();

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

}
