package com.julvez.pfc.teachonsnap.ut.stats.repository;

import com.julvez.pfc.teachonsnap.stats.repository.StatsRepository;
import com.julvez.pfc.teachonsnap.stats.repository.StatsRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class StatsRepositoryFactoryTest extends RepositoryFactoryTest<StatsRepository> {

	@Override
	protected StatsRepository getTestRepository() {
		return StatsRepositoryFactory.getRepository();
	}
}
