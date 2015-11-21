package com.julvez.pfc.teachonsnap.user.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.date.DateManagerFactory;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;
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
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.model.UserMessageKey;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryFactory;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository = UserRepositoryFactory.getRepository();
	
	private LangService langService = LangServiceFactory.getService(); 
	private URLService urlService = URLServiceFactory.getService();
	private NotifyService notifyService = NotifyServiceFactory.getService();
	private TextService textService = TextServiceFactory.getService();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	private DateManager dateManager = DateManagerFactory.getManager();
	private PropertyManager properties = PropertyManagerFactory.getManager();		
	
	@Override
	public User getUser(int idUser) {
		User user = userRepository.getUser(idUser);
		user.setLanguage(langService.getLanguage(user.getIdLanguage()));
		user.setMD5(stringManager.generateMD5(user.getEmail()));
		if(user.isBanned()){
			UserBannedInfo bannedInfo = userRepository.getUserBannedInfo(idUser);
			if(bannedInfo != null){
				bannedInfo.setAdmin(getUser(bannedInfo.getIdAdmin()));
			}
			user.setBannedInfo(bannedInfo);
		}
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
						
			String url = urlService.getAbsoluteURL(ControllerURI.CHANGE_PASSWORD + token);
			
			String subject = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_SUBJECT);
			String message = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_MESSAGE, url);
			
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
	public boolean isAllowedForLesson(User user, Lesson lesson) {
		boolean isAllowed = false;
		
		if(user!=null && lesson!=null){			
			
			if(lesson != null){
				if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
					isAllowed = true;
				}
			}
		}
		return isAllowed;
	}

	@Override
	public boolean sendRegister(String email, String firstname, String lastname, Language language) {
		boolean success = false;
		
		if(!stringManager.isEmpty(email) && firstname!=null && lastname!=null && language != null){
						
			boolean verified = verifyEmailDomain(email);
			
			if(verified){
				User user = getUserFromEmail(email);
				
				if(user == null){
					user = createUser(email, firstname, lastname, language);
			
					if(user != null){
						success = sendPasswordRemind(user);
					}				
				}
			}
		}		
		return success;
	}
	
	@Override
	public User createUser(String email, String firstname, String lastname, Language language) {
		User ret = null;
		
		if(!stringManager.isEmpty(email) && firstname!=null && lastname!=null && language != null){
						
			int idUser = userRepository.createUser(email, firstname, lastname, language.getId());
			
			if(idUser > 0){				
				ret = getUser(idUser);
			}
		}
		return ret;
	}

	@Override
	public boolean verifyEmailDomain(String email) {
		boolean verified = false;
		
		if(!stringManager.isEmpty(email)){
			List<String> verifiedDomains = properties.getListProperty(UserPropertyName.VERIFIED_EMAIL_DOMAINS);
		
			for(String domain:verifiedDomains){
				if(email.toLowerCase().endsWith(domain)){
					verified = true;
					break;
				}
			}
		}
		return verified;
	}

	@Override
	public List<User> getUsers(int firstResult) {
		List<User> users = new ArrayList<User>();
		
		List<Short> ids = userRepository.getUsers(firstResult);
		
		for(short id:ids){
			users.add(getUser(id));
		}
		
		return users;
	}

	@Override
	public List<User> searchUsersByEmail(String searchQuery, int firstResult) {
		List<User> users = new ArrayList<User>();
		
		List<Short> ids = userRepository.searchUsersByEmail(searchQuery, firstResult);
		
		for(short id:ids){
			users.add(getUser(id));
		}
		
		return users;
	}

	@Override
	public List<User> searchUsersByName(String searchQuery, int firstResult) {
		List<User> users = new ArrayList<User>();
		
		List<Short> ids = userRepository.searchUsersByName(searchQuery, firstResult);
		
		for(short id:ids){
			users.add(getUser(id));
		}
		
		return users;
	}

	@Override
	public User saveAuthor(User user) {
		User retUser = null;
		
		if(user != null && !user.isAuthor()){
			userRepository.saveAuthor(user.getId(), user.getFullName());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User saveAdmin(User user) {
		User retUser = null;
		
		if(user != null && !user.isAdmin()){
			userRepository.saveAdmin(user.getId());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User removeAdmin(User user) {
		User retUser = null;
		
		if(user != null && user.isAdmin()){
			userRepository.removeAdmin(user.getId());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User removeAuthor(User user) {
		User retUser = null;
		
		if(user != null && user.isAuthor()){
			userRepository.removeAuthor(user.getId());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User blockUser(User user, String reason, User admin) {
		User retUser = null;
		
		if(user != null && admin!=null && admin.isAdmin() && !stringManager.isEmpty(reason)){
			userRepository.blockUser(user.getId(), reason, admin.getId());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User unblockUser(User user, User admin) {
		User retUser = null;
		
		if(user != null && admin!=null && admin.isAdmin()){
			userRepository.unblockUser(user.getId());
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}	

}
