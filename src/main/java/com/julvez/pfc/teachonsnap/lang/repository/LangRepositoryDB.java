package com.julvez.pfc.teachonsnap.lang.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;

/**
 * Repository implementation to access/modify data from a Database
 * <p>
 * {@link DBManager} is used to provide database access
 */
public class LangRepositoryDB implements LangRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();	
	
	@Override
	public Language getLanguage(short idLanguage) {
		return (Language) dbm
				.getQueryResultUnique("SQL_LANG_GET_LANGUAGE", Language.class, idLanguage);
	}

	@Override
	public short getIdLanguage(String language) {
		short id = -1;
		Object obj = dbm.getQueryResultUnique("SQL_LANG_GET_IDLANGUAGE_FROM_LANGUAGE", null, language);
		if(obj!=null)
			id = Short.parseShort(obj.toString());
		return id;		
	}

	@Override
	public short getDefaultIdLanguage() {
		short id = -1;
		Object obj = dbm
				.getQueryResultUnique("SQL_LANG_GET_DEFAULT_IDLANGUAGE", null, new Object[0]);		
		if(obj!=null)
			id = Short.parseShort(obj.toString());
		return id;		
	}

	@Override
	public List<Byte> getAllLanguages() {
		return dbm.getQueryResultList("SQL_LANG_GET_ALL_LANGUAGES", Byte.class, new Object[0]);
	}

}
