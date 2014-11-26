package com.julvez.pfc.teachonsnap.repository.user.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;

public class UserRepositoryDB implements UserRepository {

	DBManager dbm = DBManagerFactory.getDBManager();
	StringManager stringManager = StringManagerFactory.getManager();
	
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
	public boolean isValidPassword(int idUser, String password) {
		boolean isValid = false;
		Object obj = dbm.getQueryResultUnique("SQL_USER_GET_ISVALIDPASSWORD", null, password,idUser);
		if(obj!=null)
			isValid = stringManager.isTrue(obj.toString());
		return isValid;
	}

}
