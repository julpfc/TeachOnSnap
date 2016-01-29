package com.julvez.pfc.teachonsnap.manager.mail;

import com.julvez.pfc.teachonsnap.manager.log.LogManagerFactory;
import com.julvez.pfc.teachonsnap.manager.mail.javamail.MailManagerJavaMail;
import com.julvez.pfc.teachonsnap.manager.property.PropertyManagerFactory;

/**
* Factory to abstract the implementation selection for the {@link MailManager} and provide
* a singleton reference.
* <p>
* In case a new implementation should be used, it can be selected here by modifying
* getManager() method.
*/
public class MailManagerFactory {

	/** Singleton reference to the manager */
	private static MailManager manager;
	
	/**
	 * @return a singleton reference to the manager	 
	 */
	public static MailManager getManager(){
		if(manager==null){
			manager = new MailManagerJavaMail(PropertyManagerFactory.getManager(),
												LogManagerFactory.getManager());
		}
		return manager;
	}
}
