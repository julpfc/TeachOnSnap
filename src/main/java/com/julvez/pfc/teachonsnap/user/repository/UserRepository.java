package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;

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

	public void removeAdmin(int idUser);

	public void removeAuthor(int idUser);

	public UserBannedInfo getUserBannedInfo(int idUser);

	public void blockUser(int idUser, String reason, int idAdmin);

	public void unblockUser(int idUser);

	public List<Short> getAuthors(int firstResult);

	public List<Short> searchAuthorsByEmail(String searchQuery, int firstResult);

	public List<Short> searchAuthorsByName(String searchQuery, int firstResult);

	public Map<String, String> getAuthorFollowed(int idUser);

	public Map<String, String> getLessonFollowed(int idUser);

	public boolean unfollowAuthor(int idUser, int idAuthor);

	public boolean followAuthor(int idUser, int idAuthor);

	public boolean followLesson(int idUser, int idLesson);

	public boolean unfollowLesson(int idUser, int idLesson);

	public List<Short> getAuthorFollowers(int idUser);

	public List<Short> getLessonFollowers(int idLesson);

	public List<Short> getTagFollowers(int idTag);

	public void saveExtraInfo(int idUser, String extraInfo);

	public void removeExtraInfo(int idUser);

	public List<Short> getAdmins();	

}
