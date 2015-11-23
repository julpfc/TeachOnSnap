package com.julvez.pfc.teachonsnap.user.group.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;


public class UserGroupRepositoryDBCache implements UserGroupRepository {
	
	private UserGroupRepositoryDB repoDB = new UserGroupRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();

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

}
