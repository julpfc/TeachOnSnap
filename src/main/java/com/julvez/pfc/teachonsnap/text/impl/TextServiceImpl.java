package com.julvez.pfc.teachonsnap.text.impl;

import java.util.Locale;

import com.julvez.pfc.teachonsnap.lang.model.Language;
import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.text.TextService;

/**
 * Implementation of the TextService interface, uses an internal {@link TextManager} 
 * to access the localized texts by key.
 */
public class TextServiceImpl implements TextService {

	/**	Package prefix where i18n properties files are located */
	private static final String I18n_PACKAGE = "com.julvez.pfc.teachonsnap.i18n";
	
	/** Localized text manager providing access to localized texts by key and language. */
	private TextManager textManager;
	
		
	/**
	 * Constructor requires all parameters not to be null
	 * @param textManager Localized text manager providing access to localized texts by key and language
	 */
	public TextServiceImpl(TextManager textManager) {
		if(textManager == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.textManager = textManager;
	}


	@Override
	public String getLocalizedText(Language language, Enum<?> textKey) {
		return getLocalizedText(language, textKey, (String[])null);		
	}

	
	@Override
	public String getLocalizedText(Language language, Enum<?> textKey, String... params) {
		String text = null;
		
		//check input params
		if(language != null && textKey!=null){
			
			//get package name from enum
			String packageName = textKey.getClass().getPackage().getName();
			
			String[] packages = packageName.split("\\.");
			
			//get bundle package by concatening i18n package prefix and relevant package from enum
			//i.e. media, lesson, stats, ...
			String bundle = I18n_PACKAGE + "." + packages[packages.length - 2];	
			
			//get localized text from bundle by key and language
			text = textManager.getLocalizedText(new Locale(language.getLanguage()), textKey.toString(), bundle, params);
		
		}
		return text;
	}

}
