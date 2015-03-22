package com.julvez.pfc.teachonsnap.service.user;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserService {
	
	public User getUser(int idUser);

	public User getUserFromEmail(String email);

	public boolean validatePassword(User user, String password);

	public User saveUserLanguage(User user, Language language);
	
}
