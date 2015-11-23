package com.julvez.pfc.teachonsnap.user.group.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;
import com.julvez.pfc.teachonsnap.user.group.UserGroupService;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.group.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.user.group.repository.UserGroupRepositoryFactory;
import com.julvez.pfc.teachonsnap.user.model.User;

public class UserGroupServiceImpl implements UserGroupService {

	private UserGroupRepository groupRepository = UserGroupRepositoryFactory.getRepository();
	
	private UserService userService = UserServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult) {
		List<UserGroup> groups = new ArrayList<UserGroup>();
		
		List<Short> ids = groupRepository.searchGroupsByName(searchQuery, firstResult);
		
		for(short id:ids){
			groups.add(getGroup(id));
		}
		
		return groups;
	}

	@Override
	public List<UserGroup> getGroups(int firstResult) {
		List<UserGroup> groups = new ArrayList<UserGroup>();
		
		List<Short> ids = groupRepository.getGroups(firstResult);
		
		for(short id:ids){
			groups.add(getGroup(id));
		}
		
		return groups;
	}

	@Override
	public UserGroup getGroup(short idUserGroup) {
		UserGroup group = null;
		
		if(idUserGroup > 0){
			group = groupRepository.getGroup(idUserGroup);
		
			if(group !=  null){
				
				List<Short> userIds = groupRepository.getGroupMembers(idUserGroup);
				
				if(userIds != null){
					List<User> users = new ArrayList<User>();
					
					for(short id:userIds){
						users.add(userService.getUser(id));
					}
					
					group.setUsers(users);
				}			
			}
		}
		return group;
	}

	@Override
	public UserGroup createGroup(String groupName) {
		UserGroup group = null;
		
		if(!stringManager.isEmpty(groupName)){
			short idUserGroup = groupRepository.createGroup(groupName);
			
			group = getGroup(idUserGroup);
		}
		
		return group;
	}

}
