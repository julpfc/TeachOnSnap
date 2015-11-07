package com.julvez.pfc.teachonsnap.user;

import com.julvez.pfc.teachonsnap.lang.model.Language;
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
	
	public boolean isAllowedForLesson(User user, int idLesson);
	
}
