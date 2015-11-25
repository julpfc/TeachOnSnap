package com.julvez.pfc.teachonsnap.user.group.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;

public interface UserGroupRepository {

	public List<Short> getGroups(int firstResult);

	public List<Short> searchGroupsByName(String searchQuery, int firstResult);

	public UserGroup getGroup(short idUserGroup);

	public List<Short> getGroupMembers(short idUserGroup);

	public short createGroup(String groupName);

	public boolean addUser(short idUserGroup, int idUser);

	public boolean saveGroupName(short idUserGroup, String groupName);

	public boolean removeUser(short idUserGroup, int idUser);

	public boolean removeGroup(short idUserGroup);

	public List<Short> getGroupsFromUser(int idUser);

}
