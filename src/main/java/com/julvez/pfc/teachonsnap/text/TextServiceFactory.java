package com.julvez.pfc.teachonsnap.text;

import com.julvez.pfc.teachonsnap.manager.text.TextManagerFactory;
import com.julvez.pfc.teachonsnap.text.impl.TextServiceImpl;

/**
 * Factory to abstract the implementation selection for the TextService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class TextServiceFactory {
	/** Singleton reference to the service */
	private static TextService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static TextService getService(){
		if(service==null){
			service = new TextServiceImpl(TextManagerFactory.getManager());
		}
		return service;
	}
}
