package com.julvez.pfc.teachonsnap.lang.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.LangService;
import com.julvez.pfc.teachonsnap.lang.model.Language;

/**
 * Repository to access/modify data related to languages.
 * <p>
 * To be used only by the {@link LangService}'s implementation
 */
public interface LangRepository {

	/**
	 * Get Language from the identifier at the application
	 * @param idLanguage Language ID
	 * @return Language if the it is an identifier for a recognized language by the application
	 */
	public Language getLanguage(short idLanguage);

	/**
	 * Get language ID from the 2 letter ISO code
	 * @param language 2 letter ISO code for language
	 * @return Language ID if it is a recognized language by the application 
	 */
	public short getIdLanguage(String language);

	/**
	 * Gets default language to be used on the application in case no other language is recognized for the user
	 * @return default language
	 */
	public short getDefaultIdLanguage();

	/**
	 * Get list of languages IDs recognized by the application
	 * @return all language IDs recognized by the application
	 */
	public List<Byte> getAllLanguages();
	
}
