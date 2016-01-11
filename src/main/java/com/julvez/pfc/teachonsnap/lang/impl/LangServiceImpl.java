package com.julvez.pfc.teachonsnap.lang.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepository;
import com.julvez.pfc.teachonsnap.lang.repository.LangRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.stats.model.Visit;
import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Implementation of the LangService interface, uses an internal {@link LangRepository} 
 * to access/modify the language related data.
 */

public class LangServiceImpl implements LangService {

	private LangRepository langRepo = LangRepositoryFactory.getRepository();
	
	private StringManager stringManager = StringManagerFactory.getManager();
	
	@Override
	public Language getLanguage(String language) {
		Language lang = null;
		
		//if valid input
		if(!stringManager.isEmpty(language)){
			//get lang ID from repository
			short idLang = langRepo.getIdLanguage(language);
			//get Language from ID
			lang = getLanguage(idLang);
		}
		return lang;
	}
	
	@Override
	public Language getLanguage(short idLanguage) {
		Language lang = null;
		//if valid input
		if(idLanguage>0){
			//get lang from repository
			lang = langRepo.getLanguage(idLanguage);
		}
		return lang;
	}

	@Override
	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang) {
		// Language priority
		// paramLang > visitUserLang > visitLang(Anon user) > acceptHeaderLang > defaultLang
		
		Language userSessionLanguage = null;
		User sessionUser = null;
		short sessionIdLang = -1;
		
		//if valid session try to get user and language from visit
		if(visit!=null){
			sessionUser = visit.getUser();
			sessionIdLang = visit.getIdLanguage();
		}
		
		//if logged user get language from user
		if(sessionUser!=null){
			userSessionLanguage = sessionUser.getLanguage();
		}
		else if(sessionIdLang>0){
			//if anon user, get language from visit
			userSessionLanguage = langRepo.getLanguage(sessionIdLang);
		}
		
		//if no valid language from visit then try to get language from accept header
		if(userSessionLanguage==null && acceptLang!=null){
			userSessionLanguage = getLanguage(acceptLang);
		}
		
		//if user just changed to a new language
		if(paramLang!=null){
			//if there is no language yet, try to get language from the new language
			if(userSessionLanguage == null){
				userSessionLanguage = getLanguage(paramLang);
			}
			else if(!userSessionLanguage.getLanguage().equalsIgnoreCase(paramLang)){
				//if there is already a lenaguage and the new language is different... 
				Language param = getLanguage(paramLang);
				//if the new language is valid, we use it
				if(param!=null){
					userSessionLanguage = param;
				}
			}
		}
		
		//if no valid language then get default as user language
		if(userSessionLanguage == null){
			userSessionLanguage = getDefaultLanguage();
		}
		
		return userSessionLanguage;
	}

	@Override
	public Language getDefaultLanguage() {
		Language lang = null;

		//get default language ID from repository
		short idLang = langRepo.getDefaultIdLanguage();
		
		//get language from ID
		lang = getLanguage(idLang);
		
		return lang;
	}

	@Override
	public List<Language> getAllLanguages() {
		List<Language> languages = Collections.emptyList();
		
		//get all language ids from repository
		List<Byte> ids = langRepo.getAllLanguages();
		
		//if no repo error fill in languages lists from ids
		if(ids != null){
			languages = new ArrayList<Language>();
			for(short id:ids){
				languages.add(getLanguage(id));
			}
		}
		
		return languages;
	}

}
