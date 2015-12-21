package com.julvez.pfc.teachonsnap.notify;

import com.julvez.pfc.teachonsnap.user.model.User;

public interface NotifyService {

	boolean info(User user, String message);
	
	boolean info(User user, String subject, String message);
	
}
