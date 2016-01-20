package com.julvez.pfc.teachonsnap.usergroup.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Entity. Describes a group of users to be managed by an administrator. 
 * It's described by a name and contains a list of users.
 */
@Entity
public class UserGroup {

	/**	Group identifier and primary key for the entity */
	@Id
	@Column (name="idUserGroup")
	private short id;
	
	/**	Group's name */
	private String groupName;
	
	/**	Group's users */
	@Transient
	private List<User> users;

	/**
	 * @return Group's id
	 */
	public short getId() {
		return id;
	}

	/**
	 * @return Group's name
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Updates the group name
	 * @param groupName New name
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return List of users, members of this group
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Updates the group's list of users
	 * @param users new list of users
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserGroup [id=" + id + ", groupName=" + groupName + ", users="
				+ users + "]";
	}

}