package com.julvez.pfc.teachonsnap.repository.lang.db.cache;

import java.util.HashMap;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.repository.lang.db.LangRepositoryDB;

public class LangRepositoryDBCache extends LangRepositoryDB {

	private static HashMap<Short, Language> langCache = new HashMap<Short, Language>();
	
	@Override
	public Language getLanguage(short idLanguage) {
		Language lang = langCache.get(Short.valueOf(idLanguage)); 
		if(lang == null ){
			lang = super.getLanguage(idLanguage);
			langCache.put(Short.valueOf(idLanguage), lang);
		}				
		return lang;
	}

}
