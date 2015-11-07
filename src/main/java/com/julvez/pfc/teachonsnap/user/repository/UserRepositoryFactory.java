package com.julvez.pfc.teachonsnap.user.repository;


public class UserRepositoryFactory {

	private static UserRepository repo;
	
	public static UserRepository getRepository(){
		if(repo==null){
			repo = new UserRepositoryDBCache();
		}
		return repo;
	}
}
