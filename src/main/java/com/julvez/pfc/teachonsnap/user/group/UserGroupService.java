package com.julvez.pfc.teachonsnap.user.group;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;

public interface UserGroupService {

	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult);

	public List<UserGroup> getGroups(int firstResult);

	public UserGroup getGroup(short idUserGroup);

	public UserGroup createGroup(String groupName);
	
}
