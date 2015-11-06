package com.julvez.pfc.teachonsnap.service.notify;

import com.julvez.pfc.teachonsnap.model.user.User;

public interface NotifyService {

	boolean info(User user, String message);
	
	boolean info(User user, String subject, String message);
	
	boolean info(User user, String subject, String message, String optionalURL);

}
