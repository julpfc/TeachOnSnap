package com.julvez.pfc.teachonsnap.user.group.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.TagServiceFactory;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
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
	private TagService tagService = TagServiceFactory.getService();
	
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

	@Override
	public UserGroup addUserByMailList(UserGroup group, List<String> emails) {
		
		if(group != null && emails != null && emails.size() > 0){
			
			for(String email:emails){
				User user = userService.getUserFromEmail(email);
				
				if(user != null){
					addUser(group, user);
				}				
			}
			
			group = getGroup(group.getId());			
		}
		
		return group;
	}

	@Override
	public UserGroup addUser(UserGroup group, User user) {
		UserGroup retGroup = null;
		
		if(group != null && user != null){
			
			List<User> users = group.getUsers(); 
			
			if(users == null){
				users = new ArrayList<User>();
			}
			
			if(!users.contains(user)){
				if(groupRepository.addUser(group.getId(), user.getId())){
					users.add(user);
					retGroup = group;
				}
			}			
		}
		
		return retGroup;
	}

	@Override
	public UserGroup saveGroupName(UserGroup group, String groupName) {
		UserGroup retGroup = null;
		
		if(group != null && !stringManager.isEmpty(groupName)){
			if(groupRepository.saveGroupName(group.getId(), groupName)){
				group.setGroupName(groupName);
				retGroup = group;
			}						
		}		
		return retGroup;
	}

	@Override
	public UserGroup removeUser(UserGroup group, User user) {
		UserGroup retGroup = null;
		
		if(group != null && user != null){
			
			List<User> users = group.getUsers(); 
			
			if(users != null && users.contains(user)){
				if(groupRepository.removeUser(group.getId(), user.getId())){
					users.remove(user);
					retGroup = group;
				}
			}			
		}
		
		return retGroup;
	}

	@Override
	public boolean removeGroup(UserGroup group) {
		boolean success = false;
		
		if(group != null){
			success = groupRepository.removeGroup(group.getId());
		}
		
		return success;
	}

	@Override
	public List<UserGroup> getGroupsFromUser(User user) {
		List<UserGroup> groups = null;
		
		if(user != null){
			groups = new ArrayList<UserGroup>();
		
			List<Short> ids = groupRepository.getGroupsFromUser(user.getId());
			
			for(short id:ids){
				groups.add(getGroup(id));
			}
		}
		
		return groups;
	}

	@Override
	public UserGroup followAuthor(UserGroup group, User author) {
		UserGroup retGroup = null;
		
		if(group != null && author != null && author.isAuthor()){
			
			List<Short> ids = groupRepository.getAuthorFollowings(group.getId());
						
			if(!ids.contains(author.getId())){
				if(groupRepository.followAuthor(group.getId(), author.getId())){					
					retGroup = group;
				}
			}			
		}		
		return retGroup;
	}

	@Override
	public List<User> getAuthorFollowings(UserGroup group) {
		List<User> users = new ArrayList<User>();
		
		List<Short> ids = groupRepository.getAuthorFollowings(group.getId());
		
		for(short id:ids){
			users.add(userService.getUser(id));
		}
		
		return users;
	}

	@Override
	public UserGroup unfollowAuthor(UserGroup group, User author) {
		UserGroup retGroup = null;
		
		if(group != null && author != null){
			if(groupRepository.unfollowAuthor(group.getId(), author.getId())){					
				retGroup = group;
			}						
		}		
		return retGroup;
	}

	@Override
	public List<Tag> getTagFollowings(UserGroup group) {
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = groupRepository.getTagFollowings(group.getId());
		
		for(int id:ids){
			tags.add(tagService.getTag(id));
		}
		
		return tags;
	}

	@Override
	public UserGroup followTag(UserGroup group, Tag tag) {
		UserGroup retGroup = null;
		
		if(group != null && tag != null){
			
			List<Integer> ids = groupRepository.getTagFollowings(group.getId());
						
			if(!ids.contains(tag.getId())){
				if(groupRepository.followTag(group.getId(), tag.getId())){					
					retGroup = group;
				}
			}			
		}		
		return retGroup;
	}

	@Override
	public UserGroup unfollowTag(UserGroup group, Tag tag) {
		UserGroup retGroup = null;
		
		if(group != null && tag != null){
			if(groupRepository.unfollowTag(group.getId(), tag.getId())){					
				retGroup = group;
			}						
		}		
		return retGroup;
	}

}
