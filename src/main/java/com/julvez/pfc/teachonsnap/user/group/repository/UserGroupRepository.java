package com.julvez.pfc.teachonsnap.user.group.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;

public interface UserGroupRepository {

	public List<Short> getGroups(int firstResult);

	public List<Short> searchGroupsByName(String searchQuery, int firstResult);

	public UserGroup getGroup(short idUserGroup);

	public List<Short> getGroupMembers(short idUserGroup);

	public short createGroup(String groupName);

}
