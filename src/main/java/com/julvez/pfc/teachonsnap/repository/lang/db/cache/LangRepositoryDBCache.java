package com.julvez.pfc.teachonsnap.repository.lang.db.cache;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;
import com.julvez.pfc.teachonsnap.repository.lang.db.LangRepositoryDB;

public class LangRepositoryDBCache implements LangRepository {

	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private LangRepositoryDB repoDB = new LangRepositoryDB();
		
	public Language getLanguage(short idLanguage) {
		return (Language)cache.executeImplCached(repoDB, idLanguage);
	}

	@Override
	public short getIdLanguage(String language) {
		return (short)cache.executeImplCached(repoDB, language);
	}
	 			

}
