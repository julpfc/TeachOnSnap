package com.julvez.pfc.teachonsnap.notify;

import java.util.List;

import com.julvez.pfc.teachonsnap.user.model.User;

/**
 * Provides the functionality to work with notifications.
 */
public interface NotifyService {
	
	/**
	 * Send a notification to the user with the specified message.
	 * @param user Adressee
	 * @param message Content of the notification
	 * @return true if the notification is successfully sent
	 */
	public boolean info(User user, String message);
	
	/**
	 * Send a notification to the user with the specified message.
	 * @param user Adressee
	 * @param subject for the notification
	 * @param message Content of the notification
	 * @return true if the notification is successfully sent
	 */
	public boolean info(User user, String subject, String message);

	/**
	 * Send a broadcast notification to a list of users with the specified message.
	 * @param users Adressees
	 * @param subject for the broadcast notification
	 * @param message Content of the broadcast notification
	 * @return true if the broadcast is successfully sent
	 */
	public boolean broadcast(List<User> users, String subject,	String message);
	
}
