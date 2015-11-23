package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

public class UserRepositoryDB implements UserRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();
	private StringManager stringManager = StringManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();
	
	@Override
	public User getUser(int idUser) {
		return (User)dbm.getQueryResultUnique("SQL_USER_GET_USER", User.class, idUser);
	}

	@Override
	public int getIdUserFromEmail(String email) {
		int id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_USER_GET_IDUSER_FROM_EMAIL", null, email);
		if(obj!=null)
			id = Integer.parseInt(obj.toString());
		return id;
	}

	@Override
	public int isValidPassword(int idUser, String password) {
		int isValid = -1;
		Object obj = dbm.getQueryResultUnique("SQL_USER_GET_ISVALIDPASSWORD", null, password,idUser);
		if(obj!=null)
			isValid = Integer.parseInt(obj.toString());
		return isValid;
	}

	@Override
	public void saveUserLanguage(int idUser, short idLanguage) {
		dbm.updateQuery("SQL_USER_SAVE_LANGUAGE", idLanguage, idUser);		
	}

	@Override
	public void saveFirstName(int idUser, String firstname) {
		dbm.updateQuery("SQL_USER_SAVE_FIRSTNAME", firstname, idUser);		
	}

	@Override
	public void saveLastName(int idUser, String lastname) {
		dbm.updateQuery("SQL_USER_SAVE_LASTNAME", lastname, idUser);		
	}

	@Override
	public void savePassword(int idUser, String newPassword) {
		dbm.updateQuery("SQL_USER_SAVE_PASSWORD", newPassword, idUser);		
	}

	@Override
	public void savePasswordTemporaryToken(int idUser, String token) {
		dbm.updateQuery("SQL_USER_SAVE_TMP_TOKEN", idUser, token, token);
		
	}

	@Override
	public int getIdUserFromPasswordTemporaryToken(String token) {
		int id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_USER_GET_IDUSER_FROM_TMP_TOKEN", null, token);
		if(obj!=null)
			id = Integer.parseInt(obj.toString());
		return id;
	}

	@Override
	public void deletePasswordTemporaryToken(int idUser) {
		dbm.updateQuery("SQL_USER_DELETE_TMP_TOKEN", idUser);
		
	}

	@Override
	public int createUser(String email, String firstname, String lastname, short idLanguage) {
		return (int)dbm.insertQueryAndGetLastInserID("SQL_USER_CREATE_USER", email, firstname, lastname, idLanguage);
	}

	@Override
	public List<Short> getUsers(int firstResult) {
		int maxResults = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_USER_GET_USERIDS", Short.class, firstResult, maxResults + 1);
	}

	@Override
	public List<Short> searchUsersByEmail(String searchQuery, int firstResult) {
		int maxResults = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_USER_SEARCH_USERIDS_BY_EMAIL", Short.class, searchQuery, firstResult, maxResults + 1);
	}

	@Override
	public List<Short> searchUsersByName(String searchQuery, int firstResult) {
		int maxResults = properties.getNumericProperty(UserPropertyName.MAX_PAGE_RESULTS);
		return dbm.getQueryResultList("SQL_USER_SEARCH_USERIDS_BY_NAME", Short.class, searchQuery, searchQuery, firstResult, maxResults + 1);
	}

	@Override
	public void saveAuthor(int idUser, String fullName) {
		String URI = stringManager.generateURIname(fullName);		
		dbm.updateQuery("SQL_USER_SAVE_AUTHOR", idUser);
		dbm.updateQuery("SQL_USER_SAVE_AUTHOR_URI", idUser, URI, URI);		
	}

	@Override
	public void saveAdmin(int idUser) {
		dbm.updateQuery("SQL_USER_SAVE_ADMIN", idUser);		
	}

	@Override
	public void removeAdmin(int idUser) {
		dbm.updateQuery("SQL_USER_DELETE_ADMIN", idUser);		
	}

	@Override
	public void removeAuthor(int idUser) {
		dbm.updateQuery("SQL_USER_DELETE_AUTHOR", idUser);		
	}

	@Override
	public UserBannedInfo getUserBannedInfo(int idUser) {
		return (UserBannedInfo)dbm.getQueryResultUnique("SQL_USER_GET_USER_BANNED_INFO", UserBannedInfo.class, idUser);
	}

	@Override
	public void blockUser(int idUser, String reason, int idAdmin) {
		dbm.updateQuery("SQL_USER_BLOCK", idUser, reason, idAdmin, reason, idAdmin);
		
	}

	@Override
	public void unblockUser(int idUser) {
		dbm.updateQuery("SQL_USER_UNBLOCK", idUser);
		
	}

}
