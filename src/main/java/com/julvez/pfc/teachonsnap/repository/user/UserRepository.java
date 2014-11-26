package com.julvez.pfc.teachonsnap.repository.user;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserRepository {

	User getUser(int idUser);

	int getIdUserFromEmail(String email);

	boolean isValidPassword(int idUser, String password);

}
