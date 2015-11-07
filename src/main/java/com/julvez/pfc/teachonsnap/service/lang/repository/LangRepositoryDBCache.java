package com.julvez.pfc.teachonsnap.service.lang.repository;

import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;

public class LangRepositoryDBCache implements LangRepository {

	private CacheManager cache = CacheManagerFactory.getCacheManager();
	private LangRepositoryDB repoDB = new LangRepositoryDB();
		
	public Language getLanguage(short idLanguage) {
		return (Language)cache.executeImplCached(repoDB, idLanguage);
	}

	@Override
	public short getIdLanguage(String language) {
		short idLanguage = -1;
		Short obj = (Short)cache.executeImplCached(repoDB, language);
		if(obj!=null)
			idLanguage = obj.shortValue();
		return idLanguage;
	}

	@Override
	public short getDefaultIdLanguage() {
		short idLanguage = -1;
		Short obj = (Short)cache.executeImplCached(repoDB, new Object[0]);
		if(obj!=null)
			idLanguage = obj.shortValue();
		return idLanguage;		
	}
	 			

}
