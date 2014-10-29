package com.julvez.pfc.teachonsnap.service.lesson;

import com.julvez.pfc.teachonsnap.service.lesson.impl.LessonServiceImpl;

public class LessonServiceFactory {

	private static LessonService service;
	
	public static LessonService getService(){
		if(service==null){
			service = new LessonServiceImpl();
		}
		return service;
	}
}
