package com.julvez.pfc.teachonsnap.service.user.impl;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.user.UserRepository;
import com.julvez.pfc.teachonsnap.repository.user.UserRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.user.UserService;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository = UserRepositoryFactory.getRepository();
	private LangService langService = LangServiceFactory.getService(); 

	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public User getUser(int idUser) {
		User user = userRepository.getUser(idUser);
		user.setLanguage(langService.getLanguage(user.getIdLanguage()));
		user.setMD5(stringManager.generateMD5(user.getEmail()));
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
			int validation = userRepository.isValidPassword(user.getId(),password);
			valid = validation>0;
		}
		return valid;
	}

	@Override
	public User saveUserLanguage(User user, Language language) {
		User retUser = null;
		
		if(user != null && language != null && user.getLanguage().getId() !=  language.getId()){
			userRepository.saveUserLanguage(user.getId(), language.getId());
			user.setIdLanguage(language.getId());
			user.setLanguage(language);
			retUser = user;
		}
		
		return retUser;
	}


}
