package com.julvez.pfc.teachonsnap.lang.repository;

import java.util.List;

import com.julvez.pfc.teachonsnap.lang.model.Language;

public interface LangRepository {

	public Language getLanguage(short idLanguage);

	public short getIdLanguage(String language);

	public short getDefaultIdLanguage();

	public List<Byte> getAllLanguages();
	
}
