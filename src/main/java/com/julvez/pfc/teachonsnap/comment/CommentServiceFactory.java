package com.julvez.pfc.teachonsnap.comment;

import com.julvez.pfc.teachonsnap.comment.impl.CommentServiceImpl;

/**
 * Factory to abstract the implementation selection for the CommentService and provide
 * a singleton reference.
 * <p>
 * In case a new implementation should be used, it can be selected here by modifying
 * getService() method.
 */
public class CommentServiceFactory {

	private static CommentService service;
	
	/**
	 * @return a singleton reference to the service	 
	 */
	public static CommentService getService(){
		if(service==null){
			service = new CommentServiceImpl();
		}
		return service;
	}
}
