package com.julvez.pfc.teachonsnap.user.group.repository;


public class UserGroupRepositoryFactory {

	private static UserGroupRepository repo;
	
	public static UserGroupRepository getRepository() {
		if(repo==null){
			repo = new UserGroupRepositoryDBCache();
		}
		return repo;
	}

}
