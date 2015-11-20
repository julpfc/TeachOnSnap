package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.model.User;

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

	public int createUser(String email, String firstname, String lastname, short idLanguage);

	public List<Short> getUsers(int firstResult);

	public List<Short> searchUsersByEmail(String searchQuery, int firstResult);

	public List<Short> searchUsersByName(String searchQuery, int firstResult);

	public void saveAuthor(int idUser, String fullName);

	public void saveAdmin(int idUser);	

}
