package com.julvez.pfc.teachonsnap.lesson;

import com.julvez.pfc.teachonsnap.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.lesson.impl.LessonServiceImpl;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * Factory to abstract the implementation selection for the LessonService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class LessonServiceFactory {

	/** Singleton reference to the service */
	private static LessonService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static LessonService getService(){
		if(service==null){
			service = new LessonServiceImpl(LessonRepositoryFactory.getRepository(),
											LangServiceFactory.getService(),
											UserServiceFactory.getService(),
											TextServiceFactory.getService(),
											NotifyServiceFactory.getService(),
											URLServiceFactory.getService(),
											StringManagerFactory.getManager());
		}
		return service;
	}
}
