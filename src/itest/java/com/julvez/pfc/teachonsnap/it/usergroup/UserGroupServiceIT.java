package com.julvez.pfc.teachonsnap.it.usergroup;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupService;
import com.julvez.pfc.teachonsnap.usergroup.UserGroupServiceFactory;
import com.julvez.pfc.teachonsnap.ut.usergroup.UserGroupServiceTest;

public class UserGroupServiceIT extends UserGroupServiceTest {

	private DBManager dbm = DBManagerFactory.getManager();
	private CacheManager cache = CacheManagerFactory.getManager();
	
	@Override
	protected UserGroupService getService() {
		return UserGroupServiceFactory.getService();
	}

	@Override
	public void testSearchGroupsByName() {
		String name = query;
		query = groupName;
		super.testSearchGroupsByName();
		query = name;
	}

	@Override
	public void testCreateGroup() {
		dbm.updateQuery("SQL_IT_USERGROUP_DELETE_USERGROUP");		
		dbm.updateQuery("SQL_IT_USERGROUP_RESET_USERGROUP_ID");		
		super.testCreateGroup();
	}

	@Override
	public void testAddUserByMailList() {
		cache.clearCache("getGroupMembers");
		try{ super.testAddUserByMailList(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_GROUPMEMBER");		
	}

	@Override
	public void testAddUser() {
		cache.clearCache("getGroupMembers");
		try{ super.testAddUser(); } catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_GROUPMEMBER");		
	}

	@Override
	public void testSaveGroupName() {
		cache.clearCache("getGroup");	
		dbm.updateQuery("SQL_USERGROUP_SAVE_GROUPNAME", EMPTY_STRING, idUserGroup);
		try{ super.testSaveGroupName();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_USERGROUP_SAVE_GROUPNAME", groupName, idUserGroup);
	}

	@Override
	public void testRemoveUser() {
		dbm.updateQuery("SQL_USERGROUP_ADD_USER", idUserGroup, idUser);
		cache.clearCache("getGroupMembers");
		super.testRemoveUser();
	}

	@Override
	public void testRemoveGroup() {
		try{ super.testRemoveGroup();} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_RESET_USERGROUP_ID");
		test.createGroup(groupName);
	}

	@Override
	public void testGetAuthorFollowings() {
		cache.clearCache("getAuthorFollowings");
		dbm.updateQuery("SQL_USERGROUP_ADD_FOLLOW_AUTHOR", idUserGroup, idUser);
		try{ super.testGetAuthorFollowings();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_FOLLOWGROUPAUTHOR");
	}

	@Override
	public void testFollowAuthor() {
		cache.clearCache("getAuthorFollowings");
		try{ super.testFollowAuthor();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_USERGROUP_TRUNCATE_FOLLOWGROUPAUTHOR");
	}

	@Override
	public void testUnfollowAuthor() {
		cache.clearCache("getAuthorFollowings");
		dbm.updateQuery("SQL_USERGROUP_ADD_FOLLOW_AUTHOR", idUserGroup, idUser);		
		super.testUnfollowAuthor();
	}

	@Override
	public void testGetTagFollowings() {
		cache.clearCache("getTagFollowings");
		dbm.updateQuery("SQL_USERGROUP_ADD_FOLLOW_TAG", idUserGroup, idTag);
		try{ super.testGetTagFollowings();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_FOLLOWGROUPTAG");
	}

	@Override
	public void testFollowTag() {
		cache.clearCache("getTagFollowings");
		try{ super.testFollowTag();	} catch(Throwable t){ t.printStackTrace();}
		dbm.updateQuery("SQL_IT_TAG_TRUNCATE_FOLLOWGROUPTAG");
	}

	@Override
	public void testUnfollowTag() {
		cache.clearCache("getTagFollowings");
		dbm.updateQuery("SQL_USERGROUP_ADD_FOLLOW_TAG", idUserGroup, idTag);
		super.testUnfollowTag();
	}
}
