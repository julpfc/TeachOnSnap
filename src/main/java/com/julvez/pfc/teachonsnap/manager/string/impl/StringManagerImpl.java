package com.julvez.pfc.teachonsnap.manager.string.impl;

import org.apache.commons.lang3.StringUtils;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;


public class StringManagerImpl implements StringManager {

	@Override
	public boolean isEmpty(String string) {
		return StringUtils.isBlank(string);		
	}

}
