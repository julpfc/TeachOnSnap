package com.julvez.pfc.teachonsnap.service.lesson.test;

import com.julvez.pfc.teachonsnap.service.lesson.test.impl.LessonTestServiceImpl;

public class LessonTestServiceFactory {

	private static LessonTestService service;
	
	public static LessonTestService getService(){
		if(service==null){
			service = new LessonTestServiceImpl();
		}
		return service;
	}
}
