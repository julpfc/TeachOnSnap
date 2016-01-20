package com.julvez.pfc.teachonsnap.lessontest;

import com.julvez.pfc.teachonsnap.lessontest.impl.LessonTestServiceImpl;

public class LessonTestServiceFactory {

	private static LessonTestService service;
	
	public static LessonTestService getService(){
		if(service==null){
			service = new LessonTestServiceImpl();
		}
		return service;
	}
}
