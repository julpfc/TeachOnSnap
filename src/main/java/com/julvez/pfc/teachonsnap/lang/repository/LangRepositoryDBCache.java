package com.julvez.pfc.teachonsnap.lang.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.cache.CacheManager;

/**
 * Repository implementation to access/modify data from a Database through a cache.
 * <p>
 * A repository database implementation ({@link LangRepositoryDB}) is used to provide the database layer under the cache.
 * <p>
 * {@link CacheManager} is used to provide a cache system
 */
public class LangRepositoryDBCache implements LangRepository {

	/** Database repository providing data access and modification capabilities */
	private LangRepositoryDB repoDB;

	/** Cache manager providing access/modification capabilities to the cache system */
	private CacheManager cache;
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param repoDB Database repository providing data access and modification capabilities
	 * @param cache Cache manager providing access/modification capabilities to the cache system
	 */
	public LangRepositoryDBCache(LangRepositoryDB repoDB, CacheManager cache) {
		if(repoDB == null || cache == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.repoDB = repoDB;
		this.cache = cache;
	}

	@Override
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

	@Override
	@SuppressWarnings("unchecked")
	public List<Byte> getAllLanguages() {		
		return (List<Byte>)cache.executeImplCached(repoDB, new Object[0]);
	}
	 			

}
