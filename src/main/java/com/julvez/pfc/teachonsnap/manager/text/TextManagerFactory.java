package com.julvez.pfc.teachonsnap.manager.text;

import com.julvez.pfc.teachonsnap.manager.text.i18n.TextManagerI18n;

/**
 * Factory to abstract the implementation selection for the {@link TextManager} and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getManager() method.
 */
public class TextManagerFactory {
	
	/** Singleton reference to the manager */
	private static TextManager manager;

	/**
	 * @return a singleton reference to the manager	 
	 */
	public static TextManager getManager() {
		if(manager==null){
			manager = new TextManagerI18n();
		}
		return manager;
	}	
}
