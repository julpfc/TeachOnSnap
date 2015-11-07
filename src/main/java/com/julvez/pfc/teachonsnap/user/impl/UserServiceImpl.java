package com.julvez.pfc.teachonsnap.user.impl;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.date.DateManagerFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserMessageKey;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryFactory;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository = UserRepositoryFactory.getRepository();
	
	private LangService langService = LangServiceFactory.getService(); 
	private URLService requestService = URLServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	private LessonService lessonService = LessonServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private DateManager dateManager = DateManagerFactory.getManager();
		
	
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

	@Override
	public User saveFirstName(User user, String firstname) {
		User retUser = null;
		
		if(user != null && !stringManager.isEmpty(firstname)){
			userRepository.saveFirstName(user.getId(), firstname);
			user.setFirstName(firstname);
			retUser = user;
		}
		
		return retUser;
	}

	@Override
	public User saveLastName(User user, String lastname) {
		User retUser = null;
		
		if(user != null && !stringManager.isEmpty(lastname)){
			userRepository.saveLastName(user.getId(), lastname);
			user.setLastName(lastname);
			retUser = user;
		}
		
		return retUser;
	}

	@Override
	public void savePassword(User user, String newPassword) {
			
		if(user != null && !stringManager.isEmpty(newPassword)){
			userRepository.savePassword(user.getId(), newPassword);
		}		
	}

	@Override
	public boolean sendPasswordRemind(User user) {
		boolean success = false;
		
		if(user != null){
			String token = savePasswordTemporaryToken(user); 
						
			String url = requestService.getAbsoluteURL(ControllerURI.CHANGE_PASSWORD + token);
			
			String subject = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_SUBJECT);
			String message = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_SUBJECT_MESSAGE, url);
			
			success = notifyService.info(user, subject, message, url);
		}
		
		return success;
	}

	@Override
	public String savePasswordTemporaryToken(User user) {
		String token = null;
		
		if(user != null){
			token = generatePasswordTemporaryToken(user);
		
			userRepository.savePasswordTemporaryToken(user.getId(), token);
		}
		
		return token;		
	}

	private String generatePasswordTemporaryToken(User user) {
		return stringManager.generateMD5(user.toString() + dateManager.getCurrentDate());
	}

	@Override
	public User getUserFromPasswordTemporaryToken(String token) {
		User user = null;
		int idUser = userRepository.getIdUserFromPasswordTemporaryToken(token);
		if (idUser>0){
			user = getUser(idUser);
		}
		return user;
	}

	@Override
	public void deletePasswordTemporaryToken(User user) {
		if(user != null){
			userRepository.deletePasswordTemporaryToken(user.getId());
		}
		
	}

	@Override
	public boolean isAllowedForLesson(User user, int idLesson) {
		boolean isAllowed = false;
		
		if(user!=null && idLesson>0){
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson != null){
				if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
					isAllowed = true;
				}
			}
		}
		return isAllowed;
	}	

}
