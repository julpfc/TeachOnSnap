package com.julvez.pfc.teachonsnap.ut.lang.repository;

import com.julvez.pfc.teachonsnap.lang.repository.LangRepository;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;


public class LangRepositoryFactoryTest extends RepositoryFactoryTest<LangRepository> {

	@Override
	protected LangRepository getTestRepository() {	
		return LangRepositoryFactory.getRepository();
	}
}
