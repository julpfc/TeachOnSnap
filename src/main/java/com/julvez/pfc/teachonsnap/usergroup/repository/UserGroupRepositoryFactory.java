package com.julvez.pfc.teachonsnap.usergroup.repository;


public class UserGroupRepositoryFactory {

	private static UserGroupRepository repo;
	
	public static UserGroupRepository getRepository() {
		if(repo==null){
			repo = new UserGroupRepositoryDBCache();
		}
		return repo;
	}

}
