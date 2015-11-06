package com.julvez.pfc.teachonsnap.service.text;

import com.julvez.pfc.teachonsnap.model.lang.Language;

public interface TextService {

	public String getLocalizedText(Language language, Enum<?> textKey);

	public String getLocalizedText(Language language, Enum<?> textKey, String... params);

}
