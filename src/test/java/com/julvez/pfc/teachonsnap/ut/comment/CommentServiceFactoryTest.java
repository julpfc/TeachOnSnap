package com.julvez.pfc.teachonsnap.ut.comment;

import com.julvez.pfc.teachonsnap.comment.CommentService;
import com.julvez.pfc.teachonsnap.comment.CommentServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class CommentServiceFactoryTest extends ServiceFactoryTest<CommentService> {

	@Override
	protected CommentService getTestService() {
		return CommentServiceFactory.getService();
	}

}
