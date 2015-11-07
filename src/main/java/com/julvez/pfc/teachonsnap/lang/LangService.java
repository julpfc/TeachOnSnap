package com.julvez.pfc.teachonsnap.lang;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.visit.model.Visit;

public interface LangService {

	public Language getLanguage(String language);

	public Language getLanguage(short idLanguage);

	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang);
	
	public Language getDefaultLanguage();
}
