package com.julvez.pfc.teachonsnap.user;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.user.model.User;

public interface UserService {
	
	public User getUser(int idUser);

	public User getUserFromEmail(String email);

	public boolean validatePassword(User user, String password);

	public User saveUserLanguage(User user, Language language);

	public User saveFirstName(User user, String firstname);

	public User saveLastName(User user, String lastname);

	public void savePassword(User user, String newPassword);

	public boolean sendPasswordRemind(User user);

	public String savePasswordTemporaryToken(User user);

	public User getUserFromPasswordTemporaryToken(String token);

	public void deletePasswordTemporaryToken(User user);
	
	public boolean isAllowedForLesson(User user, Lesson lesson);

	public boolean sendRegister(String email, String firstname, String lastname, Language language);

	User createUser(String email, String firstname, String lastname, Language language);

	boolean verifyEmailDomain(String email);

	public List<User> getUsers(int firstResult);

	public List<User> searchUsersByEmail(String searchQuery, int firstResult);

	public List<User> searchUsersByName(String searchQuery, int firstResult);

	public User saveAuthor(User user);
	
	public User saveAdmin(User user);
	
}
