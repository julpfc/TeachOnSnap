package com.julvez.pfc.teachonsnap.service.user.repository;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserRepository {

	public User getUser(int idUser);

	public int getIdUserFromEmail(String email);

	public int isValidPassword(int idUser, String password);

	public void saveUserLanguage(int idUser, short idLanguage);

	public void saveFirstName(int idUser, String firstname);

	public void saveLastName(int idUser, String lastname);

	public void savePassword(int idUser, String newPassword);

	public void savePasswordTemporaryToken(int idUser, String token);

	public int getIdUserFromPasswordTemporaryToken(String token);

	public void deletePasswordTemporaryToken(int idUser);

}
