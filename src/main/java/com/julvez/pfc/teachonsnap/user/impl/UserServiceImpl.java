package com.julvez.pfc.teachonsnap.user.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.manager.date.DateManager;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.notify.NotifyService;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.text.TextService;
import com.julvez.pfc.teachonsnap.url.URLService;
import com.julvez.pfc.teachonsnap.url.model.ControllerURI;
import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.model.UserMessageKey;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.user.repository.UserRepository;

/**
 * Implementation of the UserService interface, uses an internal {@link UserRepository} 
 * to access/modify the users related data.
 */
public class UserServiceImpl implements UserService {

	/** Repository than provides data access/modification */
	private UserRepository userRepository;
	
	/** Provides the functionality to work with different languages to the application */
	private LangService langService; 
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	/** Provides the functionality to work with notifications */
	private NotifyService notifyService;
	
	/** Provides the functionality to work with localized texts */
	private TextService textService;
	
	/** String manager providing string manipulation utilities */
	private StringManager stringManager;
	
	/** Date manager providing date manipulation utilities */
	private DateManager dateManager;
	
	/** Property manager providing access to properties files */
	private PropertyManager properties;		
	
			
	/**
	 * Constructor requires all parameters not to be null
	 * @param userRepository Repository than provides data access/modification
	 * @param langService Provides the functionality to work with different languages to the application
	 * @param urlService Provides the functionality to work with application's URLs
	 * @param notifyService Provides the functionality to work with notifications
	 * @param textService Provides the functionality to work with localized texts
	 * @param stringManager String manager providing string manipulation utilities
	 * @param dateManager Date manager providing date manipulation utilities
	 * @param properties Property manager providing access to properties files
	 */
	public UserServiceImpl(UserRepository userRepository,
			LangService langService, URLService urlService,
			NotifyService notifyService, TextService textService,
			StringManager stringManager, DateManager dateManager,
			PropertyManager properties) {
		
		if(userRepository == null || langService == null || urlService == null
				|| notifyService == null || textService == null || dateManager == null 
				|| properties == null || stringManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		
		this.userRepository = userRepository;
		this.langService = langService;
		this.urlService = urlService;
		this.notifyService = notifyService;
		this.textService = textService;
		this.stringManager = stringManager;
		this.dateManager = dateManager;
		this.properties = properties;
	}

	@Override
	public User getUser(int idUser) {
		User user = null;
		
		if(idUser>0){
			//get user from repository
			user = userRepository.getUser(idUser);
		
			//if valid user
			if(user != null){
				//set language object
				user.setLanguage(langService.getLanguage(user.getIdLanguage()));
				//generate & set MD5 hash from user's email
				user.setMD5(stringManager.generateMD5(user.getEmail()));
				
				//if user is banned, then get ban info
				if(user.isBanned()){
					UserBannedInfo bannedInfo = userRepository.getUserBannedInfo(idUser);
					if(bannedInfo != null){
						bannedInfo.setAdmin(getUser(bannedInfo.getIdAdmin()));
					}
					user.setBannedInfo(bannedInfo);
				}
			
				//get user followings
				Map<String, String> authorFollowed = userRepository.getAuthorFollowed(user.getId());
				user.setAuthorFollowed(authorFollowed);
				Map<String, String> lessonFollowed = userRepository.getLessonFollowed(user.getId());
				user.setLessonFollowed(lessonFollowed);
				
				//get author URLs
				user.setURLs(urlService.getAuthorURL(), urlService.getAuthorDraftsURL());
			}
		}
		return user;
	}

	@Override
	public User getUserFromEmail(String email) {
		User user = null;
		//get user id from email
		int idUser = userRepository.getIdUserFromEmail(email);
		if (idUser>0){
			//get user from id
			user = getUser(idUser);
		}
		return user;
	}

	@Override
	public boolean validatePassword(User user, String password) {
		boolean valid = false;
		if(user!=null && password!=null){
			//check if password is valid
			int validation = userRepository.isValidPassword(user.getId(),password);
			valid = validation>0;
		}
		return valid;
	}

	@Override
	public User saveUserLanguage(User user, Language language) {
		User retUser = null;		
		if(user != null && language != null && user.getLanguage().getId() !=  language.getId()){
			//update user in repo 
			userRepository.saveUserLanguage(user.getId(), language.getId());
			//update user
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
			//update user in repo
			userRepository.saveFirstName(user.getId(), firstname);
			//update user
			user.setFirstName(firstname);
			retUser = user;
		}
		
		return retUser;
	}

	@Override
	public User saveLastName(User user, String lastname) {
		User retUser = null;
		
		if(user != null && !stringManager.isEmpty(lastname)){
			//update user in repo
			userRepository.saveLastName(user.getId(), lastname);
			//update user
			user.setLastName(lastname);
			retUser = user;
		}
		
		return retUser;
	}

	@Override
	public void savePassword(User user, String newPassword) {
			
		if(user != null && !stringManager.isEmpty(newPassword)){
			//update password in repo
			userRepository.savePassword(user.getId(), newPassword);
		}		
	}

	@Override
	public boolean sendPasswordRemind(User user) {
		boolean success = false;
		
		if(user != null){
			//generate token
			String token = savePasswordTemporaryToken(user); 
						
			//get change password url and include the identification token
			String url = urlService.getAbsoluteURL(ControllerURI.CHANGE_PASSWORD + token);
			
			//get localized texts for the user's language
			String subject = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_SUBJECT);
			String message = textService.getLocalizedText(user.getLanguage(),UserMessageKey.CHANGE_PASSWORD_MESSAGE, url);
			
			//send notification
			success = notifyService.info(user, subject, message);
		}
		
		return success;
	}

	@Override
	public String savePasswordTemporaryToken(User user) {
		String token = null;
		
		if(user != null){
			//generate token
			token = generatePasswordTemporaryToken(user);
		
			//save token
			userRepository.savePasswordTemporaryToken(user.getId(), token);
		}
		
		return token;		
	}

	@Override
	public User getUserFromPasswordTemporaryToken(String token) {
		User user = null;
		//get user id from token
		int idUser = userRepository.getIdUserFromPasswordTemporaryToken(token);
		if (idUser>0){
			//get user from id
			user = getUser(idUser);
		}
		return user;
	}

	@Override
	public void deletePasswordTemporaryToken(User user) {
		if(user != null){
			//delete temporary token
			userRepository.deletePasswordTemporaryToken(user.getId());
		}
		
	}

	@Override
	public boolean isAllowedForLesson(User user, Lesson lesson) {
		boolean isAllowed = false;
		
		if(user!=null && lesson!=null){
			//User is allowed to edit the lesson only if user is an administrator, the author
			// of the lesson and is currently active as an author
			if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
				isAllowed = true;
			}
		}
		return isAllowed;
	}

	@Override
	public boolean sendRegister(String email, String firstname, String lastname, Language language) {
		boolean success = false;
		
		if(!stringManager.isEmpty(email) && firstname!=null && lastname!=null && language != null){
			
			//check if the email is verified and can be automatically registered
			boolean verified = verifyEmailDomain(email);
			
			if(verified){
				//get user from email, in case it's already used 
				User user = getUserFromEmail(email);
				
				//if it'snt used
				if(user == null){
					//create user
					user = createUser(email, firstname, lastname, language);
			
					//if created
					if(user != null){
						//send password link
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
			
			//create new user and get id
			int idUser = userRepository.createUser(email, firstname, lastname, language.getId());
			
			if(idUser > 0){				
				//get user from id
				ret = getUser(idUser);
			}
		}
		return ret;
	}

	@Override
	public boolean verifyEmailDomain(String email) {
		boolean verified = false;
		
		if(!stringManager.isEmpty(email)){
			//get verified domans whitelist
			List<String> verifiedDomains = properties.getListProperty(UserPropertyName.VERIFIED_EMAIL_DOMAINS);
		
			//verify email
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
		List<User> users = Collections.emptyList();
		
		//gets users's ids from repo. Pagination
		List<Short> ids = userRepository.getUsers(firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
		
			//get users from ids
			for(short id:ids){
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public List<User> searchUsersByEmail(String searchQuery, int firstResult) {
		List<User> users = Collections.emptyList();
		
		//gets users's ids from repo's search. Pagination
		List<Short> ids = userRepository.searchUsersByEmail(searchQuery, firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public List<User> searchUsersByName(String searchQuery, int firstResult) {
		List<User> users = Collections.emptyList();
		
		//gets users's ids from repo's search. Pagination
		List<Short> ids = userRepository.searchUsersByName(searchQuery, firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public User saveAuthor(User user) {
		User retUser = null;
		
		if(user != null && !user.isAuthor()){
			//Grant author permissions to user
			userRepository.saveAuthor(user.getId(), user.getFullName());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User saveAdmin(User user) {
		User retUser = null;
		
		if(user != null && !user.isAdmin()){
			//Grant administrator permissions to user
			userRepository.saveAdmin(user.getId());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User removeAdmin(User user) {
		User retUser = null;
		
		if(user != null && user.isAdmin()){
			//Revoke administrator permissions from user
			userRepository.removeAdmin(user.getId());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User removeAuthor(User user) {
		User retUser = null;
		
		if(user != null && user.isAuthor()){
			//Revoke author permissions from user
			userRepository.removeAuthor(user.getId());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User blockUser(User user, String reason, User admin) {
		User retUser = null;
		
		if(user != null && admin!=null && admin.isAdmin() && !stringManager.isEmpty(reason)){
			//block user
			userRepository.blockUser(user.getId(), reason, admin.getId());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public User unblockUser(User user, User admin) {
		User retUser = null;
		
		if(user != null && admin!=null && admin.isAdmin()){
			//unblock user
			userRepository.unblockUser(user.getId());
			//update user
			retUser = getUser(user.getId());
		}
		
		return retUser;
	}

	@Override
	public List<User> getAuthors(int firstResult) {
		List<User> users = Collections.emptyList();
		
		//get authors ids from repo. Pagination
		List<Short> ids = userRepository.getAuthors(firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
			
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public List<User> searchAuthorsByEmail(String searchQuery, int firstResult) {
		List<User> users = Collections.emptyList();
		
		//Get authors ids from repo's search. Pagination
		List<Short> ids = userRepository.searchAuthorsByEmail(searchQuery, firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public List<User> searchAuthorsByName(String searchQuery, int firstResult) {
		List<User> users = Collections.emptyList();
		
		//Get authors ids from repo's search. Pagination
		List<Short> ids = userRepository.searchAuthorsByName(searchQuery, firstResult);
		
		if(ids != null){
			users = new ArrayList<User>();
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}

	@Override
	public List<AuthorFollowed> getAuthorsFollowed(List<User> authors, List<User> authorFollowings) {
		List<AuthorFollowed> retList = Collections.emptyList();
		
		if(authors != null){
			retList = new ArrayList<AuthorFollowed>();
			for(User author:authors){
				//Get author object from user
				AuthorFollowed followed = new AuthorFollowed(author);
				
				//If this author is followed by the user is in his followings list
				if(authorFollowings != null && authorFollowings.contains(author)){
					//Mark author as followed
					followed.setFollowed(true);
				}
				
				retList.add(followed);				
			}			
		}
		
		return retList;
	}

	@Override
	public User followAuthor(User user, User author) {
		User retUser = null;
		
		if(user != null && author != null && author.isAuthor()){						
			if(user.getAuthorFollowed() != null && !user.getAuthorFollowed().containsKey("["+author.getId()+"]")){
				//follow user
				if(userRepository.followAuthor(user.getId(), author.getId())){	
					//update user
					retUser = getUser(user.getId());
				}
			}			
		}		
		return retUser;
	}

	@Override
	public User unfollowAuthor(User user, User author) {
		User retUser = null;
		
		if(user != null && author != null){
			//unfollow user
			if(userRepository.unfollowAuthor(user.getId(), author.getId())){	
				//update user
				retUser = getUser(user.getId());
			}						
		}		
		return retUser;
	}

	@Override
	public User followLesson(User user, Lesson lesson) {
		User retUser = null;
		
		if(user != null && lesson != null){
						
			if(user.getLessonFollowed() != null && !user.getLessonFollowed().containsKey("["+lesson.getId()+"]")){
				//follow lesson
				if(userRepository.followLesson(user.getId(), lesson.getId())){	
					//update user
					retUser = getUser(user.getId());
				}
			}			
		}		
		return retUser;
	}

	@Override
	public User unfollowLesson(User user, Lesson lesson) {
		User retUser = null;
		
		if(user != null && lesson != null){
			//unfollow lesson
			if(userRepository.unfollowLesson(user.getId(), lesson.getId())){	
				//update user
				retUser = getUser(user.getId());
			}						
		}		
		return retUser;
	}

	@Override
	public List<User> getUsersFromIDs(Map<String, String> authorFollowed) {
		List<User> users = null;
		
		if(authorFollowed != null){
			users = new ArrayList<User>();
			//Extract ids from map, and get users from ids
			for(String id:authorFollowed.values()){
				if(stringManager.isNumeric(id)){
					int idUser = Integer.parseInt(id);
					//get user from id
					User user = getUser(idUser);
					users.add(user);
				}
			}
		}
		return users;
	}

	@Override
	public List<User> getAuthorFollowers(User author) {
		List<User> users = Collections.emptyList();
		
		if(author != null){
			//get followers ids from repo		
			List<Short> ids = userRepository.getAuthorFollowers(author.getId());
			
			if(ids != null){
				users = new ArrayList<User>();
				for(short id:ids){
					//get users from ids
					users.add(getUser(id));
				}
			}
		}			
		return users;
	}

	@Override
	public List<User> getLessonFollowers(Lesson lesson) {
		List<User> users = Collections.emptyList();
		
		//If valid lesson and it's published
		if(lesson != null && !lesson.isDraft()){
			//get lesson followers from repo
			List<Short> ids = userRepository.getLessonFollowers(lesson.getId());
		
			//fill in followers(User) list if no repo error from IDs list
			if(ids != null){
				users = new ArrayList<User>();
				for(short id:ids){
					//get users from ids
					users.add(getUser(id));
				}
			}
		}
		
		return users;
	}

	@Override
	public List<User> getTagFollowers(Tag tag) {
		List<User> users = Collections.emptyList();
		if(tag != null){
			//get followers ids from repo
			List<Short> ids = userRepository.getTagFollowers(tag.getId());
		
			if(ids != null){
				users = new ArrayList<User>();
				for(short id:ids){
					//get users from ids
					users.add(getUser(id));
				}
			}
		}
		
		return users;
	}

	@Override
	public User saveExtraInfo(User user, String extraInfo) {		
		User retUser = null;
			
		if(user != null && extraInfo != null){
			if(!stringManager.isEmpty(extraInfo)){
				//save extra info
				userRepository.saveExtraInfo(user.getId(), extraInfo);
				//update user
				user.setExtraInfo(extraInfo);
				retUser = user;
			}
			else{
				//if extra info is empty -> delete from repo
				userRepository.removeExtraInfo(user.getId());
				//update user
				user.setExtraInfo(null);
				retUser = user;
			}
		}
		return retUser;
	}

	@Override
	public List<User> getAdmins() {
		List<User> users = Collections.emptyList();

		//get admin  ids from repo
		List<Short> ids = userRepository.getAdmins();
		
		if(ids != null){
			users = new ArrayList<User>();
			
			for(short id:ids){
				//get users from ids
				users.add(getUser(id));
			}
		}
		
		return users;
	}		

	/**
	 * Generates a temporary token for identifying the user
	 * @param user to generate token
	 * @return temporary token
	 */
	private String generatePasswordTemporaryToken(User user) {
		return stringManager.generateMD5(user.toString() + dateManager.getCurrentDate());
	}

}
