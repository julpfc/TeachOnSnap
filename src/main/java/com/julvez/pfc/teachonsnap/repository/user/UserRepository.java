package com.julvez.pfc.teachonsnap.repository.user;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserRepository {

	public User getUser(int idUser);

	public int getIdUserFromEmail(String email);

	public int isValidPassword(int idUser, String password);

	public void saveUserLanguage(int idUser, short idLanguage);

}
