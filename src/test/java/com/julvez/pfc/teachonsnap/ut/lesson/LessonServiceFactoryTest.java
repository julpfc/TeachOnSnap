package com.julvez.pfc.teachonsnap.ut.lesson;

import com.julvez.pfc.teachonsnap.lesson.LessonService;
import com.julvez.pfc.teachonsnap.lesson.LessonServiceFactory;
import com.julvez.pfc.teachonsnap.ut.service.ServiceFactoryTest;

public class LessonServiceFactoryTest extends ServiceFactoryTest<LessonService> {

	@Override
	protected LessonService getTestService() {
		return LessonServiceFactory.getService();
	}

}
