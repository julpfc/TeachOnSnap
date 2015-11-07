package com.julvez.pfc.teachonsnap.lesson.test;

import com.julvez.pfc.teachonsnap.lesson.test.impl.LessonTestServiceImpl;

public class LessonTestServiceFactory {

	private static LessonTestService service;
	
	public static LessonTestService getService(){
		if(service==null){
			service = new LessonTestServiceImpl();
		}
		return service;
	}
}
