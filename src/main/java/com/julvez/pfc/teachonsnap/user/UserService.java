package com.julvez.pfc.teachonsnap.user;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.Author;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

/**
 * Provides the functionality to work with application's users.
 */
public interface UserService {
	
	/**
	 * Returns the corresponding User object from the specified id.
	 * @param idUser User's id
	 * @return User object from id if it's a valid id, null otherwise
	 */
	public User getUser(int idUser);

	/**
	 * Returns the corresponding User object from the specified email.
	 * @param email User's email, used as login
	 * @return User object from email if it's an existing email, null otherwise
	 */
	public User getUserFromEmail(String email);

	/**
	 * Verifies if the specified password belongs to the user.
	 * @param user to validate password with
	 * @param password to be validated
	 * @return true if the password is valid and belongs to the user
	 */
	public boolean validatePassword(User user, String password);

	/**
	 * Updates the preferred language for the user.
	 * @param user to change language to
	 * @param language new language
	 * @return Modified user, null otherwise
	 */
	public User saveUserLanguage(User user, Language language);

	/**
	 * Updates the user's first name
	 * @param user to change first name to
	 * @param firstname new first name
	 * @return Modified user, null otherwise
	 */
	public User saveFirstName(User user, String firstname);

	/**
	 * Updates the user's last name
	 * @param user to change last name to
	 * @param lastname new last name
	 * @return Modified user, null otherwise
	 */
	public User saveLastName(User user, String lastname);

	/**
	 * Updates the user's password
	 * @param user to change password to
	 * @param newPassword new password
	 */
	public void savePassword(User user, String newPassword);

	/**
	 * Sends a notification to the user reminding his password
	 * @param user to be notified with the remind
	 * @return true if the notification is successfully sent
	 */
	public boolean sendPasswordRemind(User user);

	/**
	 * Generates, stores and return a token to identify provisionally 
	 * the user in order to create a new password 
	 * @param user to generate the token
	 * @return generated token, null otherwise
	 */
	public String savePasswordTemporaryToken(User user);

	/**
	 * Returns the user identified by the temporary token
	 * @param token to identify a user
	 * @return identified USer, null otherwise
	 */
	public User getUserFromPasswordTemporaryToken(String token);

	/**
	 * Removes the temporary token, so it will no longer 
	 * be able to identify the user.
	 * @param user to delete token to
	 */
	public void deletePasswordTemporaryToken(User user);
	
	/**
	 * Indicates if a user is allowed to edit/modify this lesson
	 * @param user to check permmissions on
	 * @param lesson to be edited/modified
	 * @return true if user is allowed to edit/modify the lesson
	 */
	public boolean isAllowedForLesson(User user, Lesson lesson);

	/**
	 * Registers the user (creating a new user if needed) and sends
	 * a notification to establish a his password.
	 * @param email User's email
	 * @param firstname User's first name
	 * @param lastname User's last name
	 * @param language User's language
	 * @return true if the notification is susccessfully sent
	 */
	public boolean sendRegister(String email, String firstname, String lastname, Language language);

	/**
	 * Creates a new user on the application with the email, firstname, 
	 * lastname and language.
	 * @param email User's email
	 * @param firstname User's first name
	 * @param lastname User's last name
	 * @param language User's language
	 * @return the new created user, null otherwise
	 */
	public User createUser(String email, String firstname, String lastname, Language language);

	/**
	 * Checks if the email belongs to the verified mail server whitelist
	 * @param email to be verified
	 * @return true if the email's domain is on the whitelist
	 */
	public boolean verifyEmailDomain(String email);

	/**
	 * Returns all users. If the users number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first user the pagination should start from.
	 * @return List of Users for this page
	 */
	public List<User> getUsers(int firstResult);

	/**
	 * Returns a list of User found by the searching users by 
	 * matching the searchQuery with the user's email. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the users emails
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User matching the query
	 */
	public List<User> searchUsersByEmail(String searchQuery, int firstResult);

	/**
	 * Returns a list of User found by the searching users by 
	 * matching the searchQuery with the user's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the users names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User matching the query
	 */
	public List<User> searchUsersByName(String searchQuery, int firstResult);

	/**
	 * Promotes an user to author 
	 * @param user to be promoted
	 * @return Modified user, null otherwise
	 */
	public User saveAuthor(User user);
	
	/**
	 * Promotes a user to administrator
	 * @param user to be promoted
	 * @return Modified user, null otherwise
	 */
	public User saveAdmin(User user);

	/**
	 * Revokes user's administration permissions
	 * @param user to be revoked
	 * @return Modified user, null otherwise
	 */
	public User removeAdmin(User user);

	/**
	 * Revokes user's author edition permissions
	 * @param user to be revoked
	 * @return Modified user, null otherwise
	 */
	public User removeAuthor(User user);

	/**
	 * Blocks an user by an administrator, specifying a reason
	 * @param user to be banned
	 * @param reason of the ban
	 * @param admin who bans the user
	 * @return Modified user, null otherwise
	 */
	public User blockUser(User user, String reason, User admin);

	/**
	 * Unblocks an user by an administrator
	 * @param user to be unblocked
	 * @param admin who unblocks the user
	 * @return Modified user, null otherwise
	 */
	public User unblockUser(User user, User admin);

	/**
	 * Returns all authors. If the authors number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first author the pagination should start from.
	 * @return List of authors for this page
	 */
	public List<User> getAuthors(int firstResult);

	/**
	 * Returns a list of User found by the searching authors by 
	 * matching the searchQuery with the author's email. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the authors emails
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User matching the query
	 */
	public List<User> searchAuthorsByEmail(String searchQuery, int firstResult);

	/**
	 * Returns a list of User found by the searching authors by 
	 * matching the searchQuery with the author's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the authors names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User matching the query
	 */
	public List<User> searchAuthorsByName(String searchQuery, int firstResult);

	/**
	 * Returns the list of authors, with the ones followed by an user marked 
	 * (it uses the authorFollowings list to mark the followed authors).
	 * @param authors list
	 * @param authorFollowings authors followed
	 * @return authors list, with the folloeed authors marked
	 */
	public List<Author> getAuthorsFollowed(List<User> authors, List<User> authorFollowings);

	/**
	 * Enables the notifications to this user about this author
	 * @param user to enable notifications for this author updates
	 * @param author which updates will be followed by this user
	 * @return Modified user, null otherwise
	 */
	public User followAuthor(User user, User author);

	/**
	 * Disables the notifications to this user about this author
	 * @param user to disable notifications for this author updates
	 * @param author which updates will be unfollowed by this user
	 * @return Modified user, null otherwise
	 */
	public User unfollowAuthor(User user, User author);

	/**
	 * Enables the notifications to this user about this lesson
	 * @param user to enable notifications for this lesson updates
	 * @param lesson which updates will be followed by this user
	 * @return Modified user, null otherwise
	 */
	public User followLesson(User user, Lesson lesson);

	/**
	 * Disables the notifications to this user about this lesson
	 * @param user to disable notifications for this lesson updates
	 * @param lesson which updates will be unfollowed by this user
	 * @return Modified user, null otherwise
	 */
	public User unfollowLesson(User user, Lesson lesson);

	/**
	 * Returns a list of Users getting the ids from the Map of followed authors
	 * @param authorFollowed map
	 * @return list of users extracted from the map's ids
	 */
	public List<User> getUsersFromIDs(Map<String, String> authorFollowed);

	/**
	 * Returns the list of followers of this author
	 * @param author who is followed by users
	 * @return list of followers of this author
	 */
	public List<User> getAuthorFollowers(User author);

	/**
	 * Returns the list of followers of this lesson
	 * @param lesson who is followed by users
	 * @return list of followers of this lesson
	 */
	public List<User> getLessonFollowers(Lesson lesson);

	/**
	 * Returns the list of followers of this tag
	 * @param tag who is followed by users
	 * @return list of followers of this tag
	 */
	public List<User> getTagFollowers(Tag tag);

	/**
	 * Updates additional info about the user
	 * @param user with additional information
	 * @param extraInfo for this user
	 * @return Modified user, null otherwise
	 */
	public User saveExtraInfo(User user, String extraInfo);

	/**
	 * Returns all admin users. 
	 * @return List of all admins
	 */	
	public List<User> getAdmins();		
}
