package com.julvez.pfc.teachonsnap.text;

import com.julvez.pfc.teachonsnap.lang.model.Language;

/**
 * Provides the functionality to work with localized texts.
 */
public interface TextService {

	/**
	 * Get the corresponding localized text for the key and languages specified.
	 * @param language Language the text will be localized
	 * @param textKey key identifying the text
	 * @return the localized text in the desired language (if there is no localization text for that language, it will return in the default language). Null if key is not a valid one.
	 */
	public String getLocalizedText(Language language, Enum<?> textKey);

	/**
	 * Get the corresponding localized text for the key and languages specified and replaces the string aprams on the text template.
	 * @param language Language the text will be localized
	 * @param textKey key identifying the text
	 * @param params Strings to be used on the localized text as a template.
	 * @return the localized text in the desired language (if there is no localization text for that language, it will return in the default language). Null if key is not a valid one.
	 */
	public String getLocalizedText(Language language, Enum<?> textKey, String... params);

}
