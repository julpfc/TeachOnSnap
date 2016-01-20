package com.julvez.pfc.teachonsnap.usergroup;

import java.util.List;

import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

/**
 * Provides the functionality to work with application's group of users.
 */
public interface UserGroupService {

	/**
	 * Returns a list of UserGroup found by the searching groups by 
	 * matching the searchQuery with the group's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched  against the groups names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of UserGroup matching the query
	 */
	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult);

	/**
	 * Returns all groups. If the groups number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first group the pagination should start from.
	 * @return List of UserGroup for this page
	 */
	public List<UserGroup> getGroups(int firstResult);

	/**
	 * Returns the group corresponding to the specified id 
	 * @param idUserGroup UserGroup's id
	 * @return UserGroup object if it's a valid id, null otherwise
	 */
	public UserGroup getGroup(short idUserGroup);

	/**
	 * Creates a new group with the specified name.
	 * @param groupName Name for the new group
	 * @return the newly created group, null if error (duplicate name,...)
	 */
	public UserGroup createGroup(String groupName);

	/**
	 * Checks the mail list and adds all the already created users to the group
	 * @param group users will be added to
	 * @param emails identifying users to be added
	 * @return Modified group, null otherwise
	 */
	public UserGroup addUserByMailList(UserGroup group, List<String> emails);

	/**
	 * Adds a user to a group
	 * @param group user will be added to
	 * @param user to be added
	 * @return Modified group, null otherwise
	 */
	public UserGroup addUser(UserGroup group, User user);

	/**
	 * Updates group's name
	 * @param group which name will be updated
	 * @param groupName new name
	 * @return Modified group, null otherwise
	 */
	public UserGroup saveGroupName(UserGroup group, String groupName);

	/**
	 * Removes a user from the group
	 * @param group user will be removed from, if present
	 * @param user to be removed from group, if present
	 * @return Modified group, null otherwise
	 */
	public UserGroup removeUser(UserGroup group, User user);

	/**
	 * Removes a group from the application
	 * @param group to be removed
	 * @return true if success
	 */
	public boolean removeGroup(UserGroup group);

	/**
	 * Returns all groups the user is member
	 * @param user to get the groups
	 * @return List of groups user is member of
	 */
	public List<UserGroup> getGroupsFromUser(User user);

	/**
	 * Enables the notifications to this group about this author
	 * @param group to enable notifications for this author updates
	 * @param author which updates will be followed by this group
	 * @return Modified group, null otherwise
	 */
	public UserGroup followAuthor(UserGroup group, User author);

	/**
	 * Returns all authors this group is following.
	 * @param group to get authors followed
	 * @return List of followed authors by this group
	 */
	public List<User> getAuthorFollowings(UserGroup group);

	/**
	 * Disables the notifications to this group about this author
	 * @param group to disable notifications for this author updates
	 * @param author which updates will be unfollowed by this group
	 * @return Modified group, null otherwise
	 */
	public UserGroup unfollowAuthor(UserGroup group, User author);

	/**
	 * Returns all tags this group is following.
	 * @param group to get tags followed
	 * @return List of followed tags by this group
	 */
	public List<Tag> getTagFollowings(UserGroup group);

	/**
	 * Enables the notifications to this group about this tag
	 * @param group to enable notifications for this tag
	 * @param tag which will be followed by this group
	 * @return Modified group, null otherwise
	 */
	public UserGroup followTag(UserGroup group, Tag tag);

	/**
	 * Disables the notifications to this group about this tag
	 * @param group to disable notifications for this tag
	 * @param tag which updates will be unfollowed by this group
	 * @return Modified group, null otherwise
	 */
	public UserGroup unfollowTag(UserGroup group, Tag tag);	
}
