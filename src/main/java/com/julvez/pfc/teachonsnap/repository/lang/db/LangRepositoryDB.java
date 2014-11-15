package com.julvez.pfc.teachonsnap.repository.lang.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManager;
import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;

public class LangRepositoryDB implements LangRepository {

	private DBManager dbm = DBManagerFactory.getDBManager();	
	
	@Override
	public Language getLanguage(short idLanguage) {
		return (Language) dbm
				.getQueryResultUnique("SQL_LANG_GET_LANGUAGE", Language.class, idLanguage);
	}

	@Override
	public short getIdLanguage(String language) {
		return Short.parseShort(dbm
				.getQueryResultUnique("SQL_LANG_GET_IDLANGUAGE_FROM_LANGUAGE", null, language).toString());
	}

	@Override
	public short getDefaultIdLanguage() {
		return Short.parseShort(dbm
				.getQueryResultUnique("SQL_LANG_GET_DEFAULT_IDLANGUAGE", null, new Object[0]).toString());
	}

}
