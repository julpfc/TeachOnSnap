package com.julvez.pfc.teachonsnap.user.group;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface UserGroupService {

	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult);

	public List<UserGroup> getGroups(int firstResult);

	public UserGroup getGroup(short idUserGroup);

	public UserGroup createGroup(String groupName);

	public UserGroup addUserByMailList(UserGroup group, List<String> emails);

	public UserGroup addUser(UserGroup group, User user);

	public UserGroup saveGroupName(UserGroup group, String groupName);

	public UserGroup removeUser(UserGroup group, User user);

	public boolean removeGroup(UserGroup group);

	public List<UserGroup> getGroupsFromUser(User user);
	
}
