package com.julvez.pfc.teachonsnap.ut.lesson.repository;

import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepository;
import com.julvez.pfc.teachonsnap.lesson.repository.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class LessonRepositoryFactoryTest extends RepositoryFactoryTest<LessonRepository> {

	@Override
	protected LessonRepository getTestRepository() {
		return LessonRepositoryFactory.getRepository();
	}
}
