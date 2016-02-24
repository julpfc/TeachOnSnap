package com.julvez.pfc.teachonsnap.ut.lessontest.repository;

import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepository;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepositoryFactory;
import com.julvez.pfc.teachonsnap.ut.repository.RepositoryFactoryTest;

public class LessonTestRepositoryFactoryTest extends RepositoryFactoryTest<LessonTestRepository> {

	@Override
	protected LessonTestRepository getTestRepository() {
		return LessonTestRepositoryFactory.getRepository();
	}
}
