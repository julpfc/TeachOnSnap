package com.julvez.pfc.teachonsnap.lang.repository;

import com.julvez.pfc.teachonsnap.lang.model.Language;

public interface LangRepository {

	public Language getLanguage(short idLanguage);

	public short getIdLanguage(String language);

	public short getDefaultIdLanguage();
	
}
