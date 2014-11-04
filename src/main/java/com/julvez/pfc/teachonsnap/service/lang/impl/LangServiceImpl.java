package com.julvez.pfc.teachonsnap.service.lang.impl;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lang.LangService;

public class LangServiceImpl implements LangService {

	LangRepository langRepo = LangRepositoryFactory.getRepository();
	@Override
	public Language getLanguageFromAccept(String acceptLanguage) {
		Language lang = null;
		
		if(acceptLanguage!=null){
			String language = acceptLanguage.substring(0,acceptLanguage.indexOf("-")==-1?0:acceptLanguage.indexOf("-"));
			short idLang = langRepo.getIdLanguage(language);
			lang = langRepo.getLanguage(idLang);
		}
		return lang;
	}

}
