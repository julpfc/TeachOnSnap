package com.julvez.pfc.teachonsnap.repository.user.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;

public class UserRepositoryDB implements UserRepository {

	DBManager dbm = DBManagerFactory.getDBManager();
	
	@Override
	public User getUser(int idUser) {
		return (User)dbm.getQueryResultUnique("SQL_USER_GET_USER", User.class, idUser);
	}

}
