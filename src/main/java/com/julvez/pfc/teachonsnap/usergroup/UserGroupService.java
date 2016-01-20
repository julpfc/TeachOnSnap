package com.julvez.pfc.teachonsnap.usergroup;

import java.util.List;

import com.julvez.pfc.teachonsnap.tag.model.Tag;
import com.julvez.pfc.teachonsnap.user.model.User;
import com.julvez.pfc.teachonsnap.usergroup.model.UserGroup;

public interface UserGroupService {

	public List<UserGroup> searchGroupsByName(String searchQuery, int firstResult);

	public List<UserGroup> getGroups(int firstResult);

	public UserGroup getGroup(short idUserGroup);

	public UserGroup createGroup(String groupName);

	public UserGroup addUserByMailList(UserGroup group, List<String> emails);

	public UserGroup addUser(UserGroup group, User user);

	public UserGroup saveGroupName(UserGroup group, String groupName);

	public UserGroup removeUser(UserGroup group, User user);

	public boolean removeGroup(UserGroup group);

	public List<UserGroup> getGroupsFromUser(User user);

	public UserGroup followAuthor(UserGroup group, User author);

	public List<User> getAuthorFollowings(UserGroup group);

	public UserGroup unfollowAuthor(UserGroup group, User author);

	public List<Tag> getTagFollowings(UserGroup group);

	public UserGroup followTag(UserGroup group, Tag tag);

	public UserGroup unfollowTag(UserGroup group, Tag tag);
	
}
