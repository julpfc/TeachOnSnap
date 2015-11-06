package com.julvez.pfc.teachonsnap.manager.text;

import com.julvez.pfc.teachonsnap.manager.text.i18n.TextManagerI18n;

public class TextManagerFactory {

	private static TextManager manager;

	public static TextManager getManager() {
		if(manager==null){
			manager = new TextManagerI18n();
		}
		return manager;
	}
	
	
	
}
