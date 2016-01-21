package com.julvez.pfc.teachonsnap.user.repository;

import java.util.List;
import java.util.Map;

import com.julvez.pfc.teachonsnap.user.UserService;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserBannedInfo;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;

/**
 * Repository to access/modify data related to users.
 * <p>
 * To be used only by the {@link UserService}'s implementation
 */
public interface UserRepository {

	/**
	 * Returns the corresponding User object from the specified id.
	 * @param idUser User's id
	 * @return User object from id if it's a valid id, null otherwise
	 */
	public User getUser(int idUser);

	/**
	 * Returns the corresponding User id from the specified email.
	 * @param email User's email, used as login
	 * @return User id from email if it's an existing email, null otherwise
	 */
	public int getIdUserFromEmail(String email);

	/**
	 * Verifies if the specified password belongs to the user.
	 * @param idUser to validate password with
	 * @param password to be validated
	 * @return number of users matching user & password
	 */
	public int isValidPassword(int idUser, String password);

	/**
	 * Updates the preferred language for the user.
	 * @param idUser to change language to
	 * @param idLanguage new language
	 */
	public void saveUserLanguage(int idUser, short idLanguage);

	/**
	 * Updates the user's first name
	 * @param idUser to change first name to
	 * @param firstname new first name
	 */
	public void saveFirstName(int idUser, String firstname);

	/**
	 * Updates the user's last name
	 * @param idUser to change last name to
	 * @param lastname new last name
	 */
	public void saveLastName(int idUser, String lastname);

	/**
	 * Updates the user's password
	 * @param idUser to change password to
	 * @param newPassword new password
	 */
	public void savePassword(int idUser, String newPassword);

	/**
	 * Saves a token to identify provisionally 
	 * the user in order to create a new password 
	 * @param idUser to be identified by the token
	 * @param token to identify the user
	 */
	public void savePasswordTemporaryToken(int idUser, String token);

	/**
	 * Returns the user identified by the temporary token
	 * @param token to identify a user
	 * @return identified USer id, -1 otherwise
	 */
	public int getIdUserFromPasswordTemporaryToken(String token);

	/**
	 * Removes the temporary token, so it will no longer 
	 * be able to identify the user.
	 * @param idUser to delete token to
	 */
	public void deletePasswordTemporaryToken(int idUser);

	/**
	 * Creates a new user on the application with the email, firstname, 
	 * lastname and language.
	 * @param email User's email
	 * @param firstname User's first name
	 * @param lastname User's last name
	 * @param idLanguage User's language
	 * @return the new created user id, -1 otherwise
	 */
	public int createUser(String email, String firstname, String lastname, short idLanguage);

	/**
	 * Returns all users ids. If the users number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first user the pagination should start from.
	 * @return List of Users ids for this page
	 */
	public List<Short> getUsers(int firstResult);

	/**
	 * Returns a list of User ids found by the searching users by 
	 * matching the searchQuery with the user's email. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the users emails
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User ids matching the query
	 */
	public List<Short> searchUsersByEmail(String searchQuery, int firstResult);

	/**
	 * Returns a list of User ids found by the searching users by 
	 * matching the searchQuery with the user's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the users names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User ids matching the query
	 */
	public List<Short> searchUsersByName(String searchQuery, int firstResult);

	/**
	 * Promotes an user to author 
	 * @param idUser to be promoted
	 * @param fullName to generate the author's URI name
	 */
	public void saveAuthor(int idUser, String fullName);

	/**
	 * Promotes a user to administrator
	 * @param idUser to be promoted
	 */
	public void saveAdmin(int idUser);

	/**
	 * Revokes user's administration permissions
	 * @param idUser to be revoked
	 */
	public void removeAdmin(int idUser);

	/**
	 * Revokes user's author edition permissions
	 * @param idUser to be revoked
	 */
	public void removeAuthor(int idUser);

	/**
	 * Returns user's ban related information
	 * @param idUser banned
	 * @return user's ban related information
	 */
	public UserBannedInfo getUserBannedInfo(int idUser);

	/**
	 * Blocks an user by an administrator, specifying a reason
	 * @param idUser to be banned
	 * @param reason of the ban
	 * @param idAdmin who bans the user
	 */	
	public void blockUser(int idUser, String reason, int idAdmin);

	/**
	 * Unblocks an user
	 * @param idUser to be unblocked
	 */
	public void unblockUser(int idUser);

	/**
	 * Returns all authors ids. If the authors number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first author the pagination should start from.
	 * @return List of authors ids for this page
	 */
	public List<Short> getAuthors(int firstResult);

	/**
	 * Returns a list of User ids found by the searching authors by 
	 * matching the searchQuery with the author's email. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the authors emails
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User ids matching the query
	 */
	public List<Short> searchAuthorsByEmail(String searchQuery, int firstResult);

	/**
	 * Returns a list of User ids found by the searching authors by 
	 * matching the searchQuery with the author's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched against the authors names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of User ids matching the query
	 */
	public List<Short> searchAuthorsByName(String searchQuery, int firstResult);

	/**
	 * Returns a map of authors followed by this user.
	 * @param idUser user who follows these authors
	 * @return authors followed map, <[id],id>
	 */
	public Map<String, String> getAuthorFollowed(int idUser);

	/**
	 * Returns a map of lessons followed by this user.
	 * @param idUser user who follows these lessons
	 * @return lessons followed map, <[id],id>
	 */
	public Map<String, String> getLessonFollowed(int idUser);

	/**
	 * Disables the notifications to this user about this author
	 * @param idUser to disable notifications for this author updates
	 * @param idAuthor which updates will be unfollowed by this user
	 * @return true if modified
	 */
	public boolean unfollowAuthor(int idUser, int idAuthor);

	/**
	 * Enables the notifications to this user about this author
	 * @param idUser to enable notifications for this author updates
	 * @param idAuthor which updates will be followed by this user
	 * @return true if modified
	 */
	public boolean followAuthor(int idUser, int idAuthor);

	/**
	 * Enables the notifications to this user about this lesson
	 * @param idUser to enable notifications for this lesson updates
	 * @param idLesson which updates will be followed by this user
	 * @return true if modified
	 */
	public boolean followLesson(int idUser, int idLesson);

	/**
	 * Disables the notifications to this user about this lesson
	 * @param idUser to disable notifications for this lesson updates
	 * @param idLesson which updates will be unfollowed by this user
	 * @return true if modified
	 */
	public boolean unfollowLesson(int idUser, int idLesson);

	/**
	 * Returns the list of followers ids of this author
	 * @param idUser who is followed by users
	 * @return list of followers ids of this author
	 */
	public List<Short> getAuthorFollowers(int idUser);

	/**
	 * Returns the list of followers ids of this lesson
	 * @param idLesson who is followed by users
	 * @return list of followers ids of this lesson
	 */
	public List<Short> getLessonFollowers(int idLesson);

	/**
	 * Returns the list of followers ids of this tag
	 * @param idTag who is followed by users
	 * @return list of followers ids of this tag
	 */
	public List<Short> getTagFollowers(int idTag);

	/**
	 * Updates additional info about the user
	 * @param idUser with additional information
	 * @param extraInfo for this user
	 */
	public void saveExtraInfo(int idUser, String extraInfo);

	/**
	 * Removes the additional info about the user
	 * @param idUser with additional information
	 */
	public void removeExtraInfo(int idUser);

	/**
	 * Returns all admin users ids. 
	 * @return List of all admins ids
	 */	
	public List<Short> getAdmins();	

}
