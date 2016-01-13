package com.julvez.pfc.teachonsnap.comment;

import com.julvez.pfc.teachonsnap.comment.impl.CommentServiceImpl;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.notify.NotifyServiceFactory;
import com.julvez.pfc.teachonsnap.text.TextServiceFactory;
import com.julvez.pfc.teachonsnap.user.UserServiceFactory;

/**
 * Factory to abstract the implementation selection for the CommentService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class CommentServiceFactory {

	/** Singleton reference to the service */
	private static CommentService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static CommentService getService(){
		if(service==null){
			service = new CommentServiceImpl(CommentRepositoryFactory.getRepository(),
											UserServiceFactory.getService(),
											LessonServiceFactory.getService(),
											TextServiceFactory.getService(),
											NotifyServiceFactory.getService());
		}
		return service;
	}
}
