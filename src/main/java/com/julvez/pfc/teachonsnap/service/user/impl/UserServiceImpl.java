package com.julvez.pfc.teachonsnap.service.user.impl;

import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;
import com.julvez.pfc.teachonsnap.repository.user.UserRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;

public class UserServiceImpl implements UserService {

	UserRepository userRepository = UserRepositoryFactory.getRepository();
	LangService langService = LangServiceFactory.getService(); 
	
	@Override
	public User getUser(int idUser) {
		User user = userRepository.getUser(idUser);
		user.setLanguage(langService.getLanguage(user.getIdLanguage()));		
		return user;
	}

	@Override
	public User getUserFromEmail(String email) {
		User user = null;
		int idUser = userRepository.getIdUserFromEmail(email);
		if (idUser>0){
			user = getUser(idUser);
		}
		return user;
	}

	@Override
	public boolean validatePassword(User user, String password) {
		boolean valid = false;
		if(user!=null && password!=null){
			valid = userRepository.isValidPassword(user.getId(),password);
		}
		return valid;
	}

}