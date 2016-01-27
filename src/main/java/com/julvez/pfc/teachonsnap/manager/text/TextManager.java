package com.julvez.pfc.teachonsnap.manager.text;

import java.util.Locale;

/** Localized texts manager providing access to localized texts by key and language. */
public interface TextManager {

	/**
	 * Returns the localized text for the locale, key and bundle specified
	 * @param locale language for the localization
	 * @param textKey text's key at the bundle
	 * @param bundle Localized text bundle
	 * @return the localized text for the locale, key and bundle specified
	 */
	public String getLocalizedText(Locale locale, String textKey, String bundle);
	
	/**
	 * Returns the localized text for the locale, key and bundle specified,
	 * formatting the localized text with the params strings. 
	 * @param locale language for the localization
	 * @param textKey text's key at the bundle
	 * @param bundle Localized text bundle
	 * @param params to be formatted into the localized text
	 * @return the localized text for the locale, key and bundle specified
	 */
	public String getLocalizedText(Locale locale, String textKey, String bundle, String... params);

}
