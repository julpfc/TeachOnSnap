package com.julvez.pfc.teachonsnap.notify;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.model.User;

public interface NotifyService {

	boolean info(User user, String message);
	
	boolean info(User user, String subject, String message);

	boolean broadcast(List<User> users, String subject,	String message);
	
}
