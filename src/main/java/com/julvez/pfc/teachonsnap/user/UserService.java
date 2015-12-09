package com.julvez.pfc.teachonsnap.user;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.AuthorFollowed;
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

	public User createUser(String email, String firstname, String lastname, Language language);

	boolean verifyEmailDomain(String email);

	public List<User> getUsers(int firstResult);

	public List<User> searchUsersByEmail(String searchQuery, int firstResult);

	public List<User> searchUsersByName(String searchQuery, int firstResult);

	public User saveAuthor(User user);
	
	public User saveAdmin(User user);

	public User removeAdmin(User user);

	public User removeAuthor(User user);

	public User blockUser(User user, String reason, User admin);

	public User unblockUser(User user, User admin);

	public List<User> getAuthors(int firstResult);

	public List<User> searchAuthorsByEmail(String searchQuery, int firstResult);

	public List<User> searchAuthorsByName(String searchQuery, int firstResult);

	public List<AuthorFollowed> getAuthorsFollowed(List<User> authors, List<User> authorFollowings);

	public User followAuthor(User user, User author);

	public User unfollowAuthor(User user, User author);

	public User followLesson(User user, Lesson lesson);

	public User unfollowLesson(User user, Lesson lesson);

	public List<User> getUsersFromIDs(Map<String, String> authorFollowed);

	public List<User> getAuthorFollowers(User author);

	public List<User> getLessonFollowers(Lesson lesson);

	public List<User> getTagFollowers(Tag tag);

	public User saveExtraInfo(User user, String extraInfo);
		
}
