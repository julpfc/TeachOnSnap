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

}
