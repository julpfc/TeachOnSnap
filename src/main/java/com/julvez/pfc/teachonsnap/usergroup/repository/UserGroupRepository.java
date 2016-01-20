package com.julvez.pfc.teachonsnap.usergroup.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.model.UserPropertyName;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

public interface UserGroupRepository {

	/**
	 * Returns all groups ids. If the groups number is greater than the 
	 * maximum number of users allowed for a page {@link UserPropertyName}, 
	 * it will paginate them starting by firstResult
	 * @param firstResult first group the pagination should start from.
	 * @return List of UserGroup ids for this page
	 */
	public List<Short> getGroups(int firstResult);

	/**
	 * Returns a list of UserGroup ids found by the searching groups by 
	 * matching the searchQuery with the group's name. If the results 
	 * number is greater than the maximum number of users allowed for 
	 * a page {@link UserPropertyName}, it will paginate them.
	 * @param searchQuery text to be matched  against the groups names
	 * @param firstResult first result the pagination should start from.
	 * @return resulting list of UserGroup ids matching the query
	 */
	public List<Short> searchGroupsByName(String searchQuery, int firstResult);

	/**
	 * Returns the group corresponding to the specified id 
	 * @param idUserGroup UserGroup's id
	 * @return UserGroup object if it's a valid id, null otherwise
	 */
	public UserGroup getGroup(short idUserGroup);

	/**
	 * Returns all users of this group
	 * @param idUserGroup to get the users
	 * @return List of users ids of this group
	 */
	public List<Short> getGroupMembers(short idUserGroup);

	/**
	 * Creates a new group with the specified name.
	 * @param groupName Name for the new group
	 * @return the newly created group id, -1 if error (duplicate name,...)
	 */
	public short createGroup(String groupName);

	/**
	 * Adds a user to a group
	 * @param idUserGroup user will be added to
	 * @param idUser to be added
	 * @return true if success
	 */
	public boolean addUser(short idUserGroup, int idUser);

	/**
	 * Updates group's name
	 * @param idUserGroup which name will be updated
	 * @param groupName new name
	 * @return true if success
	 */
	public boolean saveGroupName(short idUserGroup, String groupName);

	/**
	 * Removes a user from the group
	 * @param idUserGroup user will be removed from, if present
	 * @param idUser to be removed from group, if present
	 * @return true if success
	 */
	public boolean removeUser(short idUserGroup, int idUser);

	/**
	 * Removes a group from the application
	 * @param idUserGroup to be removed
	 * @return true if success
	 */
	public boolean removeGroup(short idUserGroup);

	/**
	 * Returns all groups the user is member
	 * @param idUser to get the groups
	 * @return List of groups ids, user is member of
	 */
	public List<Short> getGroupsFromUser(int idUser);

	/**
	 * Returns all authors this group is following.
	 * @param idUserGroup to get authors followed
	 * @return List of followed authors ids by this group
	 */
	public List<Short> getAuthorFollowings(short idUserGroup);

	/**
	 * Enables the notifications to this group about this author
	 * @param idUserGroup to enable notifications for this author updates
	 * @param idAuthor which updates will be followed by this group
	 * @return true if success
	 */
	public boolean followAuthor(short idUserGroup, int idAuthor);

	/**
	 * Disables the notifications to this group about this author
	 * @param idUserGroup to disable notifications for this author updates
	 * @param idAuthor which updates will be unfollowed by this group
	 * @return true if success
	 */
	public boolean unfollowAuthor(short idUserGroup, int idAuthor);

	/**
	 * Returns all tags this group is following.
	 * @param idUserGroup to get tags followed
	 * @return List of followed tags ids by this group
	 */
	public List<Integer> getTagFollowings(short idUserGroup);

	/**
	 * Enables the notifications to this group about this tag
	 * @param idUserGroup to enable notifications for this tag
	 * @param idTag which will be followed by this group
	 * @return true if success
	 */
	public boolean followTag(short idUserGroup, int idTag);

	/**
	 * Disables the notifications to this group about this tag
	 * @param idUserGroup to disable notifications for this tag
	 * @param idTag which updates will be unfollowed by this group
	 * @return true if success
	 */
	public boolean unfollowTag(short idUserGroup, int idTag);

}
