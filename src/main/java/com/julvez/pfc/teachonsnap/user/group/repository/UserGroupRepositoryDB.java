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

	@Override
	public boolean addUser(short idUserGroup, int idUser) {
		return dbm.updateQuery("SQL_USERGROUP_ADD_USER", idUserGroup, idUser) >= 0;		
	}

	@Override
	public boolean saveGroupName(short idUserGroup, String groupName) {
		return dbm.updateQuery("SQL_USERGROUP_SAVE_GROUPNAME", groupName, idUserGroup) >= 0;	
	}

	@Override
	public boolean removeUser(short idUserGroup, int idUser) {
		return dbm.updateQuery("SQL_USERGROUP_REMOVE_USER", idUserGroup, idUser) >= 0;
	}

	@Override
	public boolean removeGroup(short idUserGroup) {
		boolean success = false;
		
		Object session = dbm.beginTransaction();
		
		success = removeAllUsers(session, idUserGroup);
		
		if(success){			
			success = dbm.updateQuery_NoCommit(session, "SQL_USERGROUP_DELETE_GROUP", idUserGroup) >= 0;			
		}
		
		dbm.endTransaction(success, session);
		
		return success;
	}

	private boolean removeAllUsers(Object session, short idUserGroup) {
		return dbm.updateQuery_NoCommit(session, "SQL_USERGROUP_DELETE_ALL_USERS", idUserGroup) >= 0;		
	}

	@Override
	public List<Short> getGroupsFromUser(int idUser) {
		return dbm.getQueryResultList("SQL_USERGROUP_GET_GROUPIDS_FROM_USER", Short.class, idUser);
	}

}
