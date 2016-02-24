package com.julvez.pfc.teachonsnap.ut.comment.repository;

import com.julvez.pfc.teachonsnap.comment.repository.CommentRepository;
import com.julvez.pfc.teachonsnap.comment.repository.CommentRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class CommentRepositoryFactoryTest extends RepositoryFactoryTest<CommentRepository> {

	@Override
	protected CommentRepository getTestRepository() {
		return CommentRepositoryFactory.getRepository();
	}
}
