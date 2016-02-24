package com.julvez.pfc.teachonsnap.ut.link.repository;

import com.julvez.pfc.teachonsnap.link.repository.LinkRepository;
import com.julvez.pfc.teachonsnap.link.repository.LinkRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class LinkRepositoryFactoryTest extends RepositoryFactoryTest<LinkRepository> {

	@Override
	protected LinkRepository getTestRepository() {
		return LinkRepositoryFactory.getRepository();
	}

}
