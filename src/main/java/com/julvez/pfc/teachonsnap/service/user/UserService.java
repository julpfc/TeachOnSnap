package com.julvez.pfc.teachonsnap.service.user;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface UserService {
	
	User getUser(int idUser);

	User getUserFromEmail(String email);

	boolean validatePassword(User user, String password);
}
