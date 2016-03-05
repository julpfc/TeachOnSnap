package com.julvez.pfc.teachonsnap.usergroup.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.tag.TagService;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;

/**
 * Implementation of the UserGroupService interface, uses an internal {@link UserGroupRepository} 
 * to access/modify the Group of users related data.
 */
public class UserGroupServiceImpl implements UserGroupService {

	/** Repository than provides data access/modification */
	private UserGroupRepository groupRepository;
	
	/** Provides the functionality to work with users. */
	private UserService userService;
	
	/** Provides the functionality to work with tags. */
	private TagService tagService;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
		
	/**
	 * Constructor requires all parameters not to be null
	 * @param groupRepository Repository than provides data access/modification
	 * @param userService Provides the functionality to work with users.
	 * @param tagService Provides the functionality to work with tags.
	 * @param stringManager String manager providing string manipulation utilities
	 */
	public UserGroupServiceImpl(UserGroupRepository groupRepository,
			UserService userService, TagService tagService,
			StringManager stringManager) {
		
		if(groupRepository == null || userService == null || tagService == null
				|| stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.groupRepository = groupRepository;
		this.userService = userService;
		this.tagService = tagService;
		this.stringManager = stringManager;
	}

	@Override
	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult) {
		List<UserGroup> groups = Collections.emptyList();
		
		//Groups ids as results from search
		List<Short> ids = groupRepository.searchGroupsByName(searchQuery, firstResult);
		
		if(ids != null){
			groups = new ArrayList<UserGroup>();
			//Get group objects from ids
			for(short id:ids){
				groups.add(getGroup(id));
			}
		}
		
		return groups;
	}

	@Override
	public List<UserGroup> getGroups(int firstResult) {
		List<UserGroup> groups = Collections.emptyList();
		
		//Get groups from repo (pagination)
		List<Short> ids = groupRepository.getGroups(firstResult);
		
		if(ids != null){
			groups = new ArrayList<UserGroup>();
			//Get group objects from ids
			for(short id:ids){
				groups.add(getGroup(id));
			}
		}
		
		return groups;
	}

	@Override
	public UserGroup getGroup(short idUserGroup) {
		UserGroup group = null;
		
		if(idUserGroup > 0){
			//Get group from id from repository
			group = groupRepository.getGroup(idUserGroup);
		
			if(group !=  null){
				//Get group members ids
				List<Short> userIds = groupRepository.getGroupMembers(idUserGroup);
				
				if(userIds != null){
					List<User> users = new ArrayList<User>();
					//Get users from ids
					for(short id:userIds){
						users.add(userService.getUser(id));
					}
					//Set list of users
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
			//Create group at the repository
			short idUserGroup = groupRepository.createGroup(groupName);
			
			//Get new group from id
			group = getGroup(idUserGroup);
		}
		
		return group;
	}

	@Override
	public UserGroup addUserByMailList(UserGroup group, List<String> emails) {
		
		if(group != null && emails != null && emails.size() > 0){
			
			for(String email:emails){
				//Get user from email (only if already exists at the application)
				User user = userService.getUserFromEmail(email);
				
				if(user != null){
					//Add user to group
					addUser(group, user);
				}				
			}
			//Get modified group
			group = getGroup(group.getId());			
		}
		
		return group;
	}

	@Override
	public UserGroup addUser(UserGroup group, User user) {
		UserGroup retGroup = null;
		
		if(group != null && user != null){
			//Get group's users
			List<User> users = group.getUsers(); 
			
			//If no users, initialize the array
			if(users == null){
				users = new ArrayList<User>();
			}
			
			//If it's a new user then add user to the repo 
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
			//Update group's name
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
			//Get group's users
			List<User> users = group.getUsers(); 
			
			//If it's a member, then remove user
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
			//Remove group from repository
			success = groupRepository.removeGroup(group.getId());
		}
		
		return success;
	}

	@Override
	public List<UserGroup> getGroupsFromUser(User user) {
		List<UserGroup> groups = null;
		
		if(user != null){
			//Get groups ids from user
			List<Short> ids = groupRepository.getGroupsFromUser(user.getId());
		
			if(ids != null){
				groups = new ArrayList<UserGroup>();
				//Get groups from ids
				for(short id:ids){
					groups.add(getGroup(id));
				}
			}
		}
		
		return groups;
	}

	@Override
	public UserGroup followAuthor(UserGroup group, User author) {
		UserGroup retGroup = null;
		
		if(group != null && author != null && author.isAuthor()){
			//Get author's ids followed by this group
			List<Short> ids = groupRepository.getAuthorFollowings(group.getId());
			
			//if it was not followed, do it
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
		List<User> users = Collections.emptyList();
		
		if(group != null){
			List<Short> ids = groupRepository.getAuthorFollowings(group.getId());
			//Get author's ids followed by this group
			if(ids != null){
				users = new ArrayList<User>();
				//Get authors from ids
				for(short id:ids){
					users.add(userService.getUser(id));
				}
			}
		}		
		return users;
	}

	@Override
	public UserGroup unfollowAuthor(UserGroup group, User author) {
		UserGroup retGroup = null;
		
		if(group != null && author != null){
			//unfollow this author
			if(groupRepository.unfollowAuthor(group.getId(), author.getId())){					
				retGroup = group;
			}						
		}		
		return retGroup;
	}

	@Override
	public List<Tag> getTagFollowings(UserGroup group) {
		List<Tag> tags = Collections.emptyList();
		
		if(group != null){
			//Get tags ids followed by this group
			List<Integer> ids = groupRepository.getTagFollowings(group.getId());
			
			if(ids != null){
				tags = new ArrayList<Tag>();
				for(int id:ids){
					//Get tags from ids
					tags.add(tagService.getTag(id));
				}
			}
		}		
		return tags;
	}

	@Override
	public UserGroup followTag(UserGroup group, Tag tag) {
		UserGroup retGroup = null;
		
		if(group != null && tag != null){
			//Get tags ids followed by this group
			List<Integer> ids = groupRepository.getTagFollowings(group.getId());
			//if it was not followed, do it
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
			//unfollow this tag
			if(groupRepository.unfollowTag(group.getId(), tag.getId())){					
				retGroup = group;
			}						
		}		
		return retGroup;
	}

}
