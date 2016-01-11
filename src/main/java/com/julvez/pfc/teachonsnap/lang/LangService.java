package com.julvez.pfc.teachonsnap.lang;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.stats.model.Visit;


/**
 * Provides the functionality to work with different languages to the application
 *
 */
public interface LangService {

	/**
	 * Get language from the 2 letter ISO code
	 * @param language 2 letter ISO code for language
	 * @return Language object if it is a recognized language by the application 
	 */
	public Language getLanguage(String language);

	/**
	 * Get Language from the identifier at the application
	 * @param idLanguage Language ID
	 * @return Language if the it is an identifier for a recognized language by the application
	 */
	public Language getLanguage(short idLanguage);

	/**
	 * Decides which is the user language depending on the different languages available, on headers, session, database,...
	 * @param acceptLang language at the accept HTTP header, if present
	 * @param visit Current user's visit(session)
	 * @param paramLang language at the URL as a param
	 * @return user's language from the available, default language if none
	 */
	public Language getUserSessionLanguage(String acceptLang, Visit visit, String paramLang);
	
	/**
	 * Gets default language to be used on the application in case no other language is recognized for the user
	 * @return default language
	 */
	public Language getDefaultLanguage();

	/**
	 * Get list of languages recognized by the application
	 * @return all languages recognized by the application
	 */
	public List<Language> getAllLanguages();
}
