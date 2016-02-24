package com.julvez.pfc.teachonsnap.ut.tag.repository;

import com.julvez.pfc.teachonsnap.tag.repository.TagRepository;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class TagRepositoryFactoryTest extends RepositoryFactoryTest<TagRepository> {

	@Override
	protected TagRepository getTestRepository() {
		return TagRepositoryFactory.getRepository();
	}

}
