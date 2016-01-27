package com.julvez.pfc.teachonsnap.lessontest;

import com.julvez.pfc.teachonsnap.lessontest.impl.LessonTestServiceImpl;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepositoryFactory;
import com.julvez.pfc.teachonsnap.url.URLServiceFactory;

/**
 * Factory to abstract the implementation selection for the LessonTestService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */

public class LessonTestServiceFactory {

	/** Singleton reference to the service */
	private static LessonTestService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static LessonTestService getService(){
		if(service==null){
			service = new LessonTestServiceImpl(LessonTestRepositoryFactory.getRepository(),
												URLServiceFactory.getService());
		}
		return service;
	}
}
