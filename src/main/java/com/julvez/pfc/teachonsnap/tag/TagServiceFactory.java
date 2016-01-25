package com.julvez.pfc.teachonsnap.tag;

import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.stats.StatsServiceFactory;
import com.julvez.pfc.teachonsnap.tag.impl.TagServiceImpl;
import com.julvez.pfc.teachonsnap.tag.repository.TagRepositoryFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * Factory to abstract the implementation selection for the TagService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class TagServiceFactory {

	/** Singleton reference to the service */
	private static TagService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static TagService getService(){
		if(service==null){
			service = new TagServiceImpl(TagRepositoryFactory.getRepository(),
										LessonServiceFactory.getService(),
										UserServiceFactory.getService(),
										TextServiceFactory.getService(),
										NotifyServiceFactory.getService(),
										StatsServiceFactory.getService(),
										URLServiceFactory.getService(),
										StringManagerFactory.getManager());
		}
		return service;
	}
}
