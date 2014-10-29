package com.julvez.pfc.teachonsnap.repository.lang.db;

import com.julvez.pfc.teachonsnap.manager.db.DBManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;

public class LangRepositoryDB implements LangRepository {

	@Override
	public Language getLanguage(short idLanguage) {
		return (Language) DBManagerFactory.getDBManager()
				.getQueryResultUnique("test", Language.class, idLanguage);
	}

}
