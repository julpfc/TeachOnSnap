package com.julvez.pfc.teachonsnap.repository.user;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserRepository {

	public User getUser(int idUser);

	public int getIdUserFromEmail(String email);

	public int isValidPassword(int idUser, String password);

	public void saveUserLanguage(int idUser, short idLanguage);

	public void saveFirstName(int idUser, String firstname);

	public void saveLastName(int idUser, String lastname);

	public void savePassword(int idUser, String newPassword);

}
