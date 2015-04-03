package com.julvez.pfc.teachonsnap.service.lang;

import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.model.visit.Visit;

public interface LangService {

	public Language getLanguage(String language);

	public Language getLanguage(short idLanguage);

	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang);
	
	public Language getDefaultLanguage();
}
