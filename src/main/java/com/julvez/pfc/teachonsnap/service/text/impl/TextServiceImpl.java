package com.julvez.pfc.teachonsnap.service.text.impl;

import java.util.Locale;

import com.julvez.pfc.teachonsnap.manager.text.TextManager;
import com.julvez.pfc.teachonsnap.manager.text.TextManagerFactory;
import com.julvez.pfc.teachonsnap.model.lang.Language;
import com.julvez.pfc.teachonsnap.service.text.TextService;

public class TextServiceImpl implements TextService {

	private static final String I18n_PACKAGE = "com.julvez.pfc.teachonsnap.i18n";
	private TextManager textManager = TextManagerFactory.getManager();
		
	
	@Override
	public String getLocalizedText(Language language, Enum<?> textKey) {
		return getLocalizedText(language, textKey, (String[])null);		
	}

	
	@Override
	public String getLocalizedText(Language language, Enum<?> textKey, String... params) {
		String text = null;
		
		if(language != null && textKey!=null){
			
			String packageName = textKey.getClass().getPackage().getName();
			
			String[] packages = packageName.split("\\.");
			
			String bundle = I18n_PACKAGE + "." + packages[packages.length - 1];			
			
			text = textManager.getLocalizedText(new Locale(language.getLanguage()), textKey.toString(), bundle, params);
		
		}
		return text;
	}

}
