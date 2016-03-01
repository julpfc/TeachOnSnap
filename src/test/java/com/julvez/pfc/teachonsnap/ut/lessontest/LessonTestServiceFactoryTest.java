package com.julvez.pfc.teachonsnap.ut.lessontest;

import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class LessonTestServiceFactoryTest extends ServiceFactoryTest<LessonTestService> {

	@Override
	protected LessonTestService getTestService() {
		return LessonTestServiceFactory.getService();
	}

}
