package com.julvez.pfc.teachonsnap.service.lang.impl;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.model.visit.Visit;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepository;
import com.julvez.pfc.teachonsnap.repository.lang.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lang.LangService;

public class LangServiceImpl implements LangService {

	LangRepository langRepo = LangRepositoryFactory.getRepository();
	
	@Override
	public Language getLanguage(String language) {
		Language lang = null;
		
		short idLang = langRepo.getIdLanguage(language);
		if(idLang>0)
			lang = langRepo.getLanguage(idLang);
		
		return lang;
	}
	
	@Override
	public Language getLanguage(short idLanguage) {
		return langRepo.getLanguage(idLanguage);
	}

	@Override
	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang) {
		
		// IdiomaCambio > IdiomaUsuarioSession > IdiomaSessionAnonimo > IdiomaAcceptRequest > IdiomaPorDefecto
		
		Language userSessionLanguage = null;
		User sessionUser = null;
		short sessionIdLang = -1;
		
		if(visit!=null){
			sessionUser = visit.getUser();
			sessionIdLang = visit.getIdLanguage();
		}
		
		if(sessionUser!=null){
			userSessionLanguage = sessionUser.getLanguage();
		}
		else if(sessionIdLang>0){
			userSessionLanguage = langRepo.getLanguage(sessionIdLang);
		}
		
		if(userSessionLanguage==null && acceptLang!=null){
			userSessionLanguage = getLanguage(acceptLang);
		}
		
		if(paramLang!=null){
			if(userSessionLanguage == null){
				userSessionLanguage = getLanguage(paramLang);
			}
			else if(!userSessionLanguage.getLanguage().equalsIgnoreCase(paramLang)){
				Language param = getLanguage(paramLang);
				if(param!=null){
					userSessionLanguage = param;
				}
			}
		}
		
		if(userSessionLanguage == null){
			userSessionLanguage = getDefaultLanguage();
		}
		
		return userSessionLanguage;
	}

	@Override
	public Language getDefaultLanguage() {
		Language lang = null;
		
		short idLang = langRepo.getDefaultIdLanguage();
		if(idLang>0)
			lang = langRepo.getLanguage(idLang);
		
		return lang;
	}



}
