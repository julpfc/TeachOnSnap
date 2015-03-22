package com.julvez.pfc.teachonsnap.service.role;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface RoleService {

	public boolean isAllowedForLesson(User user, int idLesson);
	
}
