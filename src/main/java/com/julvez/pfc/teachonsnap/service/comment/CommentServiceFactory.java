package com.julvez.pfc.teachonsnap.service.comment;

import com.julvez.pfc.teachonsnap.service.comment.impl.CommentServiceImpl;


public class CommentServiceFactory {

	private static CommentService service;
	
	public static CommentService getService(){
		if(service==null){
			service = new CommentServiceImpl();
		}
		return service;
	}
}
