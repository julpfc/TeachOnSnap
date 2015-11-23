package com.julvez.pfc.teachonsnap.user.group.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.user.group.model.UserGroup;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

public class UserGroupRepositoryDB implements UserGroupRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public List<Short> getGroups(int firstResult) {
		int maxResults = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_USERGROUP_GET_GROUPIDS", Short.class, firstResult, maxResults + 1);
	}

	@Override
	public List<Short> searchGroupsByName(String searchQuery, int firstResult) {
		int maxResults = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_USERGROUP_SEARCH_GROUPIDS_BY_NAME", Short.class, searchQuery, firstResult, maxResults + 1);
	}

	@Override
	public UserGroup getGroup(short idUserGroup) {
		return (UserGroup)dbm.getQueryResultUnique("SQL_USERGROUP_GET_GROUP", UserGroup.class, idUserGroup);
	}

	@Override
	public List<Short> getGroupMembers(short idUserGroup) {
		return dbm.getQueryResultList("SQL_USERGROUP_GET_GROUP_MEMBERIDS", Short.class, idUserGroup);
	}

	@Override
	public short createGroup(String groupName) {
		return (short)dbm.insertQueryAndGetLastInserID("SQL_USERGROUP_CREATE_GROUP", groupName);
	}

}
