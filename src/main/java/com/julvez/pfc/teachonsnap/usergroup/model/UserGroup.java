package com.julvez.pfc.teachonsnap.usergroup.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.julvez.pfc.teachonsnap.user.model.User;

@Entity
public class UserGroup {

	@Id
	@Column (name="idUserGroup")
	private short id;
	private String groupName;
		
	@Transient
	private List<User> users;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserGroup [id=" + id + ", groupName=" + groupName + ", users="
				+ users + "]";
	}
	
	
	
}