package com.julvez.pfc.teachonsnap.repository.visit.db.cache;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.repository.visit.VisitRepository;
import com.julvez.pfc.teachonsnap.repository.visit.db.VisitRepositoryDB;

public class VisitRepositoryDBCache implements VisitRepository {

	private VisitRepositoryDB repoDB = new VisitRepositoryDB();
	private CacheManager cache = CacheManagerFactory.getCacheManager();

	@Override
	public int createVisit(String ip) {
		return (int)cache.updateImplCached(repoDB, null, null, ip);		
	}

	@Override
	public boolean saveUser(int idVisit, int idUser) {
		return (boolean)cache.updateImplCached(repoDB, null, null, idVisit, idUser);
	}

	

}
