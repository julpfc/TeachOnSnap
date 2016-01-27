package com.julvez.pfc.teachonsnap.manager.text.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;

/**
 * Implementation of the TextManager interface, uses i18n localized text properties files  
 * to access the localized texts by key and language.
 */
public class TextManagerI18n implements TextManager {
	
	@Override
	public String getLocalizedText(Locale locale, String textKey, String bundle) {
		return getLocalizedText(locale, textKey, bundle, (String[])null);
	}

	@Override
	public String getLocalizedText(Locale locale, String textKey, String bundle, String... params) {
		String text = null;
		
		if(locale != null && textKey!=null && bundle != null){
			//get resource from bundle name and locale
			ResourceBundle rb = ResourceBundle.getBundle(bundle, locale);
			
			if(rb != null){
				//get localized text by key
				text = rb.getString(textKey);
				
				//format the localized string with params, if present
				if(params != null){
					MessageFormat formatter = new MessageFormat("");
					formatter.setLocale(locale);
					formatter.applyPattern(text);
					text = formatter.format(params);					
				}
			}
		}
		
		return text;
	}	

}
