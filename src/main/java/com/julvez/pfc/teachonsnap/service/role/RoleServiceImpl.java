package com.julvez.pfc.teachonsnap.service.role;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.lesson.LessonServiceFactory;

public class RoleServiceImpl implements RoleService {

	private LessonService lessonService = LessonServiceFactory.getService();
	
	@Override
	public boolean isAllowedForLesson(User user, int idLesson) {
		boolean isAllowed = false;
		
		if(user!=null && idLesson>0){
			
			Lesson lesson = lessonService.getLesson(idLesson);
			
			if(lesson != null){
				if(user.isAdmin() || (user.isAuthor() && user.getId() == lesson.getIdUser())){
					isAllowed = true;
				}
			}
		}
		return isAllowed;
	}

}
