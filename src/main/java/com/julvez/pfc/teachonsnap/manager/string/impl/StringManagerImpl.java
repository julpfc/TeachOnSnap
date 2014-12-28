package com.julvez.pfc.teachonsnap.manager.string.impl;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.commons.lang3.StringUtils;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;


public class StringManagerImpl implements StringManager {

	@Override
	public boolean isEmpty(String string) {
		return StringUtils.isBlank(string);		
	}

	@Override
	public boolean isTrue(String string) {
		boolean isTrue = false;
		
		if(!isEmpty(string)){
			try {
				int i = Integer.parseInt(string);
				isTrue = i>0;
			} catch (Exception e) {
				isTrue = Boolean.parseBoolean(string);
			}
		}
		return isTrue;
	}

	@Override
	public String generateURIname(String source) {
		String result = null;
		
		if(source!=null){
			result = Normalizer.normalize(source.toLowerCase(), Form.NFD)
			        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
			        .replaceAll("[^\\p{Alnum}]+", "-")
			        .replaceAll("[^a-z0-9]+$", "")
			        .replaceAll("^[^a-z0-9]+", "");
		}
		
		return result;
	}

}
