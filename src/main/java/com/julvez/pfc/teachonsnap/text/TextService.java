package com.julvez.pfc.teachonsnap.text;

import com.julvez.pfc.teachonsnap.lang.model.Language;

public interface TextService {

	public String getLocalizedText(Language language, Enum<?> textKey);

	public String getLocalizedText(Language language, Enum<?> textKey, String... params);

}
