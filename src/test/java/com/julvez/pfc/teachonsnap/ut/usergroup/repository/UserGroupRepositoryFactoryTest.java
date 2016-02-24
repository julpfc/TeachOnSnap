package com.julvez.pfc.teachonsnap.ut.usergroup.repository;

import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepository;
import com.julvez.pfc.teachonsnap.usergroup.repository.UserGroupRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class UserGroupRepositoryFactoryTest extends RepositoryFactoryTest<UserGroupRepository> {

	@Override
	protected UserGroupRepository getTestRepository() {	
		return UserGroupRepositoryFactory.getRepository();
	}
}
