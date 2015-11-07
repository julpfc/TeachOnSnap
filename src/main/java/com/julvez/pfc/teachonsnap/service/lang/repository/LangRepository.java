package com.julvez.pfc.teachonsnap.service.lang.repository;

import com.julvez.pfc.teachonsnap.model.lang.Language;

public interface LangRepository {

	public Language getLanguage(short idLanguage);

	public short getIdLanguage(String language);

	public short getDefaultIdLanguage();
	
}
