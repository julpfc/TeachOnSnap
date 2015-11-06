package com.julvez.pfc.teachonsnap.manager.text.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;

public class TextManagerI18n implements TextManager {

	@Override
	public String getLocalizedText(Locale locale, String textKey, String bundle) {
		return getLocalizedText(locale, textKey, bundle, (String[])null);
	}

	@Override
	public String getLocalizedText(Locale locale, String textKey, String bundle, String... params) {
		String text = null;
		
		if(locale != null && textKey!=null && bundle != null){
			ResourceBundle rb = ResourceBundle.getBundle(bundle, locale);
			
			if(rb != null){
				
				text = rb.getString(textKey);
				
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
