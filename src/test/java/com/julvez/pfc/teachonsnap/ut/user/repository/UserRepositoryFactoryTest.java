package com.julvez.pfc.teachonsnap.ut.user.repository;

import com.julvez.pfc.teachonsnap.user.repository.UserRepository;
import com.julvez.pfc.teachonsnap.user.repository.UserRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class UserRepositoryFactoryTest extends RepositoryFactoryTest<UserRepository> {

	@Override
	protected UserRepository getTestRepository() {
		return UserRepositoryFactory.getRepository();
	}
}
