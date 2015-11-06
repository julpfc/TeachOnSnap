package com.julvez.pfc.teachonsnap.manager.text;

import java.util.Locale;

public interface TextManager {

	public String getLocalizedText(Locale locale, String textKey, String bundle);
	
	public String getLocalizedText(Locale locale, String textKey, String bundle, String... params);

}
