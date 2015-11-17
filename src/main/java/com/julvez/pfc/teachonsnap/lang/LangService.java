package com.julvez.pfc.teachonsnap.lang;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.stats.model.Visit;

public interface LangService {

	public Language getLanguage(String language);

	public Language getLanguage(short idLanguage);

	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang);
	
	public Language getDefaultLanguage();

	public List<Language> getAllLanguages();
}